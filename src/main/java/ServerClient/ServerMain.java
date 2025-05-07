/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerClient;

/**
 *
 * @author adsd3
 */

// 서버를 시작하고 클라이언트 연결을 수락하는 진입점 클래스

/**
 *
 * @author adsd3
 */
import java.net.ServerSocket;
import java.net.Socket;

// 서버를 시작하고 클라이언트 연결을 수락하는 진입점 클래스


public class ServerMain {
    public static void main(String[] args) throws Exception {
        final int PORT = 12345;
        final int MAX_USERS = 3;

        // 사용자 자격증명 파일 경로 지정
        CredentialStore creds     = new CredentialStore("src/main/resources/USER_LOGIN.txt");
        SessionManager sessions   = new SessionManager(MAX_USERS);

        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("서버 시작: 포트 " + PORT);
            while (true) {
                Socket client = ss.accept();
                new Thread(new ClientHandler(client, creds, sessions)).start();
            }
        }
    }
}