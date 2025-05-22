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

import ruleagreement.RuleAgreementController;

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
                String serverIp = view.getServerIp().trim();

                try {
                    Socket socket = new Socket(serverIp, 5000);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    out.write("LOGIN:" + userId + "," + password + "," + role);
                    out.newLine();
                    out.flush();

                    String response = in.readLine();

                    if ("LOGIN_SUCCESS".equals(response)) {
                        JOptionPane.showMessageDialog(view, userId + "님 로그인 성공");
                        try {
                            new RuleAgreementController(userId, socket, out);
                            view.dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(view, "이용 동의 창 표시 오류: " + ex.getMessage());
                        }

                    } else if ("WAIT".equals(response)) {
                        JOptionPane.showMessageDialog(view, "현재 접속 인원 초과로 대기 중입니다.");
                        while ((response = in.readLine()) != null) {
                            if ("LOGIN_SUCCESS".equals(response)) {
                                JOptionPane.showMessageDialog(view, userId + "님 자동 로그인 성공");
                                try {
                                    new RuleAgreementController(userId, socket, out);
                                    view.dispose();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(view, "이용 동의 창 오류: " + ex.getMessage());
                                }
                                break;
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(view, "로그인 실패");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "서버 연결 실패: " + ex.getMessage());
                }
            }
        });
    }
}