/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerClient;

/**
 *
 * @author adsd3
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

// 클라이언트의 로그인/로그아웃 요청을 처리하는 쓰레드 클래스


public class ClientHandler implements Runnable {
    private final Socket socket;
    private final CredentialStore creds;
    private final SessionManager sessions;

    public ClientHandler(Socket socket, CredentialStore creds, SessionManager sessions) {
        this.socket   = socket;
        this.creds    = creds;
        this.sessions = sessions;
    }

    @Override
    public void run() {
        try (BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("LOGIN:")) {
                    // "LOGIN:id:pw" 형태로 split
                    String[] parts   = line.split(":", 3);
                    String   userId  = parts.length > 1 ? parts[1].trim() : "";
                    String   password= parts.length > 2 ? parts[2].trim() : "";

                    // 자격 검증
                    if (!creds.validate(userId, password)) {
                        out.write("FAIL:자격증명 불일치\n\n");
                        out.flush();
                        break;
                    }

                    // 세션 관리
                    SessionManager.Pending pend = new SessionManager.Pending(socket, userId, out);
                    SessionManager.LoginDecision dec = sessions.tryLogin(userId, pend);
                    switch (dec) {
                        case OK:
                            out.write("OK:로그인 성공\n\n");
                            out.flush();
                            break;
                        case WAIT:
                            out.write("WAIT:대기열 추가\n\n");
                            out.flush();
                            break;
                        case FAIL_DUP:
                            out.write("FAIL:이미 접속 중\n\n");
                            out.flush();
                            break;
                    }

                } else if (line.startsWith("LOGOUT:")) {
                    String userId = line.substring(7).trim();
                    sessions.logout(userId);
                    out.write("BYE:로그아웃 완료\n\n");
                    out.flush();
                    break;
                }
            }
        } catch (IOException e) {
            // 비정상 종료 시 세션 정리가 필요하면 여기서 처리
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}