/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class LoginController {
    private LoginView view;
    private LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = view.getUserId();
                String password = view.getPassword();
                String role = view.getRole();
                String serverIp = view.getServerIp();

                boolean valid = model.validateCredentials(userId, password, role);

                if (!valid) {
                    JOptionPane.showMessageDialog(view, "로그인 실패: 아이디 또는 비밀번호 오류");
                    return;
                }

                try {
                    Socket socket = new Socket(serverIp, 5000);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.write(userId + "\n");
                    out.flush();

                    System.out.println("서버 연결 성공 및 로그인 정보 전송 완료");

                    // 여기서 역할 기반으로 다음 뷰로 전환하는 로직을 추가할 수 있음
                    // 예: new UserReservationView(userId, socket);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "서버 연결 실패");
                }
            }
        });
    }
}