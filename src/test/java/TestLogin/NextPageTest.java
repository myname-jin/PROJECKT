/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestLogin;


import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class NextPageTest {

    /** 
     * 소켓 닫힘 여부를 확인하기 위한 더미 소켓 
     */
    static class DummySocket extends Socket {
        boolean closed = false;
        @Override
        public void close() throws IOException {
            closed = true;
        }
    }

    @Test
    void testFrameProperties() {
        String userId = "testUser";
        DummySocket socket = new DummySocket();
        BufferedReader in = new BufferedReader(new StringReader(""));
        StringWriter sw = new StringWriter();
        BufferedWriter out = new BufferedWriter(sw);

        NextPage page = new NextPage(userId, socket, in, out);

        // 프레임 속성 검사
        assertEquals("다음 페이지", page.getTitle());
        assertEquals(300, page.getWidth());
        assertEquals(200, page.getHeight());
        assertEquals(JFrame.DISPOSE_ON_CLOSE, page.getDefaultCloseOperation());

        // 라벨 검증
        Component[] comps = page.getContentPane().getComponents();
        assertEquals(1, comps.length, "컨텐트에 컴포넌트가 하나 있어야 함");
        assertTrue(comps[0] instanceof JLabel, "컴포넌트는 JLabel 이어야 함");
        JLabel label = (JLabel) comps[0];
        assertEquals("로그인 성공! 다음 페이지입니다.", label.getText());
        assertEquals(SwingConstants.CENTER, label.getHorizontalAlignment());
    }

    @Test
    void testLogoutOnWindowClosing() throws Exception {
        String userId = "testUser";
        DummySocket socket = new DummySocket();
        BufferedReader in = new BufferedReader(new StringReader(""));
        StringWriter sw = new StringWriter();
        BufferedWriter out = new BufferedWriter(sw);

        NextPage page = new NextPage(userId, socket, in, out);

        // 등록된 WindowListener 중 WindowAdapter 찾기
        WindowListener target = null;
        for (WindowListener wl : page.getWindowListeners()) {
            if (wl instanceof WindowAdapter) {
                target = wl;
                break;
            }
        }
        assertNotNull(target, "WindowAdapter 리스너가 등록되어 있어야 합니다");

        // 윈도우 닫기 이벤트 시뮬레이션
        target.windowClosing(new WindowEvent(page, WindowEvent.WINDOW_CLOSING));

        // 로그아웃 메시지 전송 및 소켓 닫힘 확인
        out.flush();
        assertEquals("LOGOUT:" + userId + "\n", sw.toString());
        assertTrue(socket.closed, "socket.close()가 호출되어야 합니다");
    }
}