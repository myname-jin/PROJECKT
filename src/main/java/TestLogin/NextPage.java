/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestLogin;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author adsd3
 */
public class NextPage extends JFrame {
    private final String userId;
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public NextPage(String userId, Socket socket, BufferedReader in, BufferedWriter out) {
        this.userId = userId;
        this.socket = socket;
        this.in = in;
        this.out = out;

        setTitle("다음 페이지");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JLabel("로그인 성공! 다음 페이지입니다.", SwingConstants.CENTER));

        // 창 닫히면 로그아웃 메시지 전송
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("[클라이언트] 창 종료 이벤트 발생: " + userId);
                try {
                    out.write("LOGOUT:" + userId + "\n");
                    out.flush();
                    socket.close();
                    System.out.println("[클라이언트] 로그아웃 메시지 전송 완료: " + userId);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}