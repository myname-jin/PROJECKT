/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import management.ReservationMgmtView;
import ruleagreement.RuleAgreementController;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class LoginController {

    private final LoginView view;
    private final LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        view.btnLogin.addActionListener(e -> handleLogin());
        view.btnSignup.addActionListener(e -> handleSignup()); // 회원가입 버튼
    }

    private void handleLogin() {
        String userId = view.getUserId();
        String password = view.getPassword();
        String role = view.getRole();

        // 관리자 로그인 처리
        if (role.equals("admin")) {
            if (model.validateCredentials(userId, password, role)) {
                showNextPage(userId, role, null, null, null);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "관리자 인증 실패", "오류", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        // 사용자 로그인 처리 (서버 연결)
        view.btnLogin.setEnabled(false);
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 9999); // 포트 9999로 변경
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // 🔍 서버로 로그인 메시지 전송
                String loginMsg = "LOGIN:" + userId + ":" + password + "\n";
                out.write(loginMsg);
                out.flush();
                System.out.println("👉 보낸 메시지: " + loginMsg);

                String response;
                boolean waitingShown = false;
                while ((response = in.readLine()) != null) {
                    System.out.println("👈 서버 응답: " + response); // 🔍 응답 출력

                    if (response.startsWith("OK")) {
                        showNextPage(userId, role, socket, in, out);
                        break;

                    } else if (response.startsWith("WAIT") && !waitingShown) {
                        waitingShown = true;
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view,
                                "접속 대기 중입니다. 자리가 나면 자동 접속됩니다.",
                                "대기 중", JOptionPane.INFORMATION_MESSAGE));

                    } else if (response.startsWith("FAIL")) {
                        final String failMsg = response;

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    view,
                                    failMsg,
                                    "접속 거부",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            view.btnLogin.setEnabled(true);
                        });

                        socket.close();
                        break;
                    }
                }

            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view,
                        "서버 연결 실패: " + e.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE));
                view.btnLogin.setEnabled(true);
            }
        }).start();
    }

    private void handleSignup() {
        view.dispose();

        SignupView signupView = new SignupView();
        SignupModel signupModel = new SignupModel();
        new SignupController(signupView, signupModel);

        signupView.setVisible(true); // 회원가입 화면 띄우기
    }

    private void showNextPage(String userId, String role,
                              Socket socket, BufferedReader in, BufferedWriter out) {
        SwingUtilities.invokeLater(() -> {
            view.dispose();

            if ("admin".equals(role)) {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception ignored) {
                }

                SwingUtilities.invokeLater(() -> {
                    ReservationMgmtView mgmtView = new ReservationMgmtView();
                    mgmtView.setLocationRelativeTo(null);
                    mgmtView.setVisible(true);
                });
            } else {
                try {
                    new RuleAgreementController(userId, socket, out);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "규칙 동의 화면을 여는 중 오류가 발생했습니다:\n" + e.getMessage(),
                            "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}