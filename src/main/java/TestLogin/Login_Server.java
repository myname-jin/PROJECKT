/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestLogin;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author adsd3
 */
public class Login_Server {
    private static final int PORT = 12345;
    private static final int MAX_USERS = 3;
    private static final Set<String> activeUsers = new HashSet<>();
    private static final Map<Socket, String> socketToUser = new HashMap<>();
    private static final Queue<ClientWaiting> waitingQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("🚀 LoginServer started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        BufferedReader in = null;
        BufferedWriter out = null;
        String userId = null;
        boolean fullyHandled = false;

        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String message = in.readLine();
                if (message == null) break;

                if (message.startsWith("LOGIN:")) {
                    userId = message.substring(6).trim();

                    ClientWaiting waitingClient = null;
                    boolean queued = false;

                    // 1) 로그인 시도 & 필요 시 대기열에 추가
                    synchronized (Login_Server.class) {
                        if (activeUsers.contains(userId)) {
                            out.write("FAIL:이미 로그인 중인 사용자입니다\n");
                            out.flush();
                            fullyHandled = true;
                            break;
                        } else if (activeUsers.size() >= MAX_USERS) {
                            out.write("WAIT:현재 접속자 수 초과\n");
                            out.flush();

                            waitingClient = new ClientWaiting(socket, userId, out);
                            waitingQueue.offer(waitingClient);
                            queued = true;
                        } else {
                            // 여유가 있으면 바로 로그인
                            activeUsers.add(userId);
                            socketToUser.put(socket, userId);
                            out.write("OK:로그인 성공\n");
                            out.flush();
                            System.out.println("✅ [" + userId + "] 접속 / 현재 접속자 수: " + activeUsers.size());
                        }
                    }

                    // 2) 대기중인 경우, 클래스 락 해제 후 wait()
                    if (queued && waitingClient != null) {
                        synchronized (waitingClient) {
                            try {
                                waitingClient.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // 3) 깨어난 후 실제 로그인 처리
                        synchronized (Login_Server.class) {
                            activeUsers.add(userId);
                            socketToUser.put(socket, userId);
                        }
                        out.write("OK:로그인 성공\n");
                        out.flush();
                        System.out.println("✅ [" + userId + "] 대기자 자동 접속 / 현재 접속자 수: " + activeUsers.size());
                    }

                } else if (message.startsWith("LOGOUT:")) {
                    userId = message.substring(7).trim();
                    synchronized (Login_Server.class) {
                        activeUsers.remove(userId);
                        socketToUser.remove(socket);
                        out.write("BYE:로그아웃 완료\n");
                        out.flush();
                        System.out.println("👋 [" + userId + "] 로그아웃 / 현재 접속자 수: " + activeUsers.size());

                        // 대기자 있으면 하나 깨워주기
                        if (!waitingQueue.isEmpty()) {
                            ClientWaiting next = waitingQueue.poll();
                            synchronized (next) {
                                next.notify();
                            }
                        }
                        fullyHandled = true;
                        break;
                    }
                }
            }

        } catch (IOException e) {
            // 클라이언트 비정상 종료 처리
            if (socketToUser.containsKey(socket)) {
                String user = socketToUser.get(socket);
                synchronized (Login_Server.class) {
                    activeUsers.remove(user);
                    socketToUser.remove(socket);
                    System.out.println("⚠️ [" + user + "] 연결 끊김 / 현재 접속자 수: " + activeUsers.size());

                    // 대기자 있으면 하나 깨워주기
                    if (!waitingQueue.isEmpty()) {
                        ClientWaiting next = waitingQueue.poll();
                        synchronized (next) {
                            next.notify();
                        }
                    }
                }
            }
        } finally {
            // 정상 처리된 경우에만 소켓 닫기
            if (fullyHandled && socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }

    private static class ClientWaiting {
        Socket socket;
        String userId;
        BufferedWriter out;

        ClientWaiting(Socket socket, String userId, BufferedWriter out) {
            this.socket = socket;
            this.userId = userId;
            this.out = out;
        }
    }
}