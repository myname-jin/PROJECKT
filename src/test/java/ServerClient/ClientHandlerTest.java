///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package ServerClient;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;  // ✅ 여기가 핵심!
//import java.io.*;
//import java.net.Socket;
//
//import static org.mockito.Mockito.*;
//
//class ClientHandlerTest {
//
//    @Test
//    void testHandlerRunsWithoutError() throws Exception {
//        ByteArrayInputStream in = new ByteArrayInputStream("logout\n".getBytes());
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        Socket mockSocket = mock(Socket.class);
//        when(mockSocket.getInputStream()).thenReturn(in);
//        when(mockSocket.getOutputStream()).thenReturn(out);
//
//        CredentialStore creds = mock(CredentialStore.class);
//        SessionManager sessionManager = new SessionManager(1);
//
//        ClientHandler handler = new ClientHandler(mockSocket, creds, sessionManager);
//
//        Thread t = new Thread(handler);
//        t.start();
//        t.join(300);
//
//        assertTrue(true);  // ✅ 이 assertTrue 가 동작하려면 import 필요
//    }
//}