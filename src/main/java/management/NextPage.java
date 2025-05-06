/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;


import ServerClient.LogoutUtil;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

// 로그인 성공 후 사용자에게 보여지는 다음 페이지 뷰
public class NextPage extends JFrame {
    private final String userId;
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    // 사용자용 생성자
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

        // ✅ 소켓이 있을 때만 로그아웃 처리 부착
        if (socket != null && out != null) {
            LogoutUtil.attach(this, userId, socket, out);
        }
    }

    // 관리자용 생성자
    public NextPage(String userId) {
        this(userId, null, null, null);
    }
}
