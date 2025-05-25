///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package ServerClient;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.BufferedWriter;
//import java.io.OutputStreamWriter;
//
//class LogoutUtilTest {
//
//    @Test
//    void testLogoutActuallyRemovesUser() throws Exception {
//        // 1명만 허용되는 세션 매니저 생성
//        SessionManager manager = new SessionManager(1);
//
//        // 사용자 "user1" 로그인
//        SessionManager.Pending p1 = new SessionManager.Pending(
//            null,
//            "user1",
//            new BufferedWriter(new OutputStreamWriter(System.out))
//        );
//        SessionManager.LoginDecision result = manager.tryLogin("user1", p1);
//        assertEquals(SessionManager.LoginDecision.OK, result);
//
//        // 로그아웃 호출
//        LogoutUtil.logout("user1", manager);
//
//        // 다시 로그인 가능해야 함
//        SessionManager.Pending p2 = new SessionManager.Pending(
//            null,
//            "user1",
//            new BufferedWriter(new OutputStreamWriter(System.out))
//        );
//        SessionManager.LoginDecision result2 = manager.tryLogin("user1", p2);
//        assertEquals(SessionManager.LoginDecision.OK, result2);
//    }
//}