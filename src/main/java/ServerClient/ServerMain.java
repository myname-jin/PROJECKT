/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerClient;



// 서버를 시작하고 클라이언트 연결을 수락하는 진입점 클래스

/**
 *
 * @author adsd3
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            // 🔧 수정된 부분: 생성자 인자 추가
            CredentialStore credentialStore = new CredentialStore();
            SessionManager sessionManager = new SessionManager(3); // 로그인 허용 인원 수

            InetAddress bindAddress = InetAddress.getByName("0.0.0.0");
            ServerSocket serverSocket = new ServerSocket(PORT, 50, bindAddress);
            System.out.println("서버가 포트 " + PORT + "에서 시작되었습니다. (외부 접속 허용)");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("새로운 클라이언트 접속: " + clientSocket.getInetAddress());
                executorService.execute(new ClientHandler(clientSocket, credentialStore, sessionManager));
            }

        } catch (IOException e) {
            System.err.println("서버 에러: " + e.getMessage());
        }
    }
}