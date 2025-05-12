/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author adsd3
 */
import management.NextPage;
import ruleagreement.RuleAgreementController;
import management.ReservationMgmt;        // ← 추가
import management.ReservationMgmtView; 


import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private final LoginView view;
    private final LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        view.btnLogin.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String userId = view.getUserId();
        String password = view.getPassword();
        String role = view.getRole();

        // 1. 관리자 로그인 처리
        if (role.equals("admin")) {
            if (model.validateCredentials(userId, password, role)) {
                showNextPage(userId, role, null, null, null);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "관리자 인증 실패", "오류", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        // 2. 사용자 로그인 처리 (서버 연결 필요)
        view.btnLogin.setEnabled(false);

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 12345);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // ✅ 메시지에서 role 제거
                out.write("LOGIN:" + userId + ":" + password + "\n");
                out.flush();

                String response;
                boolean waitingShown = false;

                while ((response = in.readLine()) != null) {
                    if (response.startsWith("OK")) {
                        showNextPage(userId, role, socket, in, out);
                        break;
                    } else if (response.startsWith("WAIT") && !waitingShown) {
                        waitingShown = true;
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view,
                                        "접속 대기 중입니다. 자리가 나면 자동 접속됩니다.",
                                        "대기 중", JOptionPane.INFORMATION_MESSAGE));
                    } else if (response.startsWith("FAIL")) {
                        final String responseFinal = response;  // 람다에서 사용 가능하게
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view,
                                        responseFinal, "접속 거부", JOptionPane.WARNING_MESSAGE));
                        socket.close();
                        view.btnLogin.setEnabled(true);
                        break;
                    }
                }

            } catch (IOException e) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(view,
                                "서버 연결 실패: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE));
                view.btnLogin.setEnabled(true);
            }
        }).start();
    }

   private void showNextPage(String userId, String role,
                          Socket socket, BufferedReader in, BufferedWriter out) {
    SwingUtilities.invokeLater(() -> {
        // 1) 로그인 창 닫기
        view.dispose();

        // 2) 바로 관리 화면(ReservationMgmtView) 띄우기
        ReservationMgmtView mgmtView = new ReservationMgmtView();
        mgmtView.setLocationRelativeTo(null);
        mgmtView.setVisible(true);
    });
}
}