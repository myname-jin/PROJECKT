/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestLogin;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Timeout;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(value = 30, unit = TimeUnit.SECONDS)
public class Login_ServerTest {

    private static Thread serverThread;

    @BeforeAll
    static void startServer() throws Exception {
        serverThread = new Thread(() -> {
            try {
                Login_Server.main(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
                fail("서버 기동 중 예외 발생: " + e.getMessage());
            }
        }, "LoginServer");
        serverThread.setDaemon(true);
        serverThread.start();
        // 서버가 완전히 시작될 때까지 잠시 대기
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("1. Simple Login & Logout")
    void testSimpleLoginAndLogout() throws Exception {
        try (
            Socket client = new Socket("localhost", 12345);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            out.write("LOGIN:userA\n"); out.flush();
            assertEquals("OK:로그인 성공", in.readLine());

            out.write("LOGOUT:userA\n"); out.flush();
            assertEquals("BYE:로그아웃 완료", in.readLine());
        }
    }

    @Test
    @DisplayName("2. Duplicate Login Fails")
    void testDuplicateLoginFails() throws Exception {
        // 첫 연결로 정상 로그인
        try (
            Socket c1 = new Socket("localhost", 12345);
            BufferedWriter o1 = new BufferedWriter(new OutputStreamWriter(c1.getOutputStream()));
            BufferedReader i1 = new BufferedReader(new InputStreamReader(c1.getInputStream()));
        ) {
            o1.write("LOGIN:dupUser\n"); o1.flush();
            assertEquals("OK:로그인 성공", i1.readLine());

            // 두 번째 연결로 같은 ID 로그인 시도
            try (
                Socket c2 = new Socket("localhost", 12345);
                BufferedWriter o2 = new BufferedWriter(new OutputStreamWriter(c2.getOutputStream()));
                BufferedReader i2 = new BufferedReader(new InputStreamReader(c2.getInputStream()));
            ) {
                o2.write("LOGIN:dupUser\n"); o2.flush();
                assertEquals("FAIL:이미 로그인 중인 사용자입니다", i2.readLine());
            }

            // c1에서 로그아웃
            o1.write("LOGOUT:dupUser\n"); o1.flush();
            assertEquals("BYE:로그아웃 완료", i1.readLine());
        }
    }

    @Test
    @DisplayName("3. Max Users & Waiting Queue")
    void testMaxUsersAndWaitingQueue() throws Exception {
        // 3명 로그인
        Socket s1 = login("u1");
        Socket s2 = login("u2");
        Socket s3 = login("u3");

        // 4번째는 대기
        try (
            Socket s4 = new Socket("localhost", 12345);
            BufferedWriter o4 = new BufferedWriter(new OutputStreamWriter(s4.getOutputStream()));
            BufferedReader i4 = new BufferedReader(new InputStreamReader(s4.getInputStream()));
        ) {
            o4.write("LOGIN:u4\n"); o4.flush();
            assertEquals("WAIT:현재 접속자 수 초과", i4.readLine());

            // u2 로그아웃 → u4 자동 로그인
            try (
                BufferedWriter o2 = new BufferedWriter(new OutputStreamWriter(s2.getOutputStream()));
                BufferedReader i2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
            ) {
                o2.write("LOGOUT:u2\n"); o2.flush();
                assertEquals("BYE:로그아웃 완료", i2.readLine());
            }
            s2.close();  // u2 소켓 닫기

            // 대기열 u4에게 OK 메시지
            assertEquals("OK:로그인 성공", i4.readLine());

            // 나머지 정리
            cleanupLogout(s1, "u1");
            cleanupLogout(s3, "u3");
            cleanupLogout(s4, "u4");
        }
    }

    // ======== helper ========
    private Socket login(String userId) throws IOException {
        Socket s = new Socket("localhost", 12345);
        BufferedWriter o = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        BufferedReader i = new BufferedReader(new InputStreamReader(s.getInputStream()));
        o.write("LOGIN:" + userId + "\n");
        o.flush();
        String resp = i.readLine();
        assertEquals("OK:로그인 성공", resp, "유저 " + userId + " 로그인 실패");
        return s;
    }

    private void cleanupLogout(Socket s, String userId) throws IOException {
        try (
            BufferedWriter o = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader i = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ) {
            o.write("LOGOUT:" + userId + "\n");
            o.flush();
            assertEquals("BYE:로그아웃 완료", i.readLine(), userId + " 로그아웃 실패");
        }
        s.close();
    }
}