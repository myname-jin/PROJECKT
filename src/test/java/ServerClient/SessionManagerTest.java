/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ServerClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager(1);
    }

    SessionManager.Pending mockPending(String userId) throws IOException {
        Socket socket = mock(Socket.class);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new ByteArrayOutputStream()));
        return new SessionManager.Pending(socket, userId, out);
    }

    @Test
    void testTryLoginOk() throws IOException {
        SessionManager.Pending pending = mockPending("user1");
        SessionManager.LoginDecision decision = sessionManager.tryLogin("user1", pending);
        assertEquals(SessionManager.LoginDecision.OK, decision);
    }

    @Test
    void testDuplicateLoginFails() throws IOException {
        SessionManager.Pending p1 = mockPending("user1");
        SessionManager.Pending p2 = mockPending("user1");

        sessionManager.tryLogin("user1", p1);
        SessionManager.LoginDecision decision = sessionManager.tryLogin("user1", p2);
        assertEquals(SessionManager.LoginDecision.FAIL_DUP, decision);
    }

    @Test
    void testQueueWaitAndWakeUp() throws IOException {
        SessionManager.Pending p1 = mockPending("user1");
        SessionManager.Pending p2 = mockPending("user2");

        assertEquals(SessionManager.LoginDecision.OK, sessionManager.tryLogin("user1", p1));
        assertEquals(SessionManager.LoginDecision.WAIT, sessionManager.tryLogin("user2", p2));

        sessionManager.logout("user1"); // p2 should be logged in
    }
}