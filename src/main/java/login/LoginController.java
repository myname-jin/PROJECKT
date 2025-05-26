/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import ruleagreement.RuleAgreementController;
import management.ReservationMgmtView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class LoginController {

    private final LoginView view;
    private final LoginModel model;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;

    // ✅ 새로운 방식: 소켓 직접 전달
    public LoginController(LoginView view, LoginModel model, Socket socket) throws IOException {
        this.view = view;
        this.model = model;
        this.socket = socket;
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        setupListeners();
    }

    // ✅ 기존 방식: 내부에서 기본 IP로 소켓 생성
    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        Socket tempSocket = null;
        BufferedWriter tempOut = null;
        BufferedReader tempIn = null;

        try {
            tempSocket = new Socket("127.0.0.1", 5000);
            tempOut = new BufferedWriter(new OutputStreamWriter(tempSocket.getOutputStream()));
            tempIn = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "서버 접속 실패: " + e.getMessage());
        }

        this.socket = tempSocket;
        this.out = tempOut;
        this.in = tempIn;
        setupListeners();
    }

    private void setupListeners() {
        view.getLoginButton().addActionListener(e -> attemptLogin());
        view.getRegisterButton().addActionListener(e -> handleSignup());
        view.getFindPasswordButton().addActionListener(e -> handlePw());
    }

    private void attemptLogin() {
        String userId = view.getUserId();
        String password = view.getPassword();
        String role = view.getRole(); // "학생", "교수", "admin"

        try {
            out.write("LOGIN:" + userId + "," + password + "," + role);
            out.newLine();
            out.flush();

            String response = in.readLine();

            if ("LOGIN_SUCCESS".equals(response)) {
                JOptionPane.showMessageDialog(view, userId + "님 로그인 성공");
                String userType = role; // ✅ 역할 전달용 userType 변수 생성

                try {
                    if ("admin".equalsIgnoreCase(role)) {
                        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                        new ReservationMgmtView().setVisible(true);
                    } else {
                        new RuleAgreementController(userId, userType, socket, out);
                    }
                    view.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "화면 전환 오류: " + ex.getMessage());
                }

            } else if ("WAIT".equals(response)) {
                JOptionPane.showMessageDialog(view, "현재 접속 인원 초과로 대기 중입니다.");
                while ((response = in.readLine()) != null) {
                    if ("LOGIN_SUCCESS".equals(response)) {
                        JOptionPane.showMessageDialog(view, userId + "님 자동 로그인 성공");
                        String userType = role;
                        try {
                            new RuleAgreementController(userId, userType, socket, out);
                            view.dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(view, "이용 동의 화면 오류: " + ex.getMessage());
                        }
                        break;
                    }
                }

            } else {
                JOptionPane.showMessageDialog(view, "로그인 실패");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "서버 통신 오류: " + ex.getMessage());
        }
    }

    private void handleSignup() {
        view.dispose();
        SignupView signupView = new SignupView();
        SignupModel signupModel = new SignupModel();
        new SignupController(signupView, signupModel);
        signupView.setVisible(true);
    }

    private void handlePw() {
        view.dispose();
        PasswordFindView passwordFindView = new PasswordFindView();
        PasswordFindModel passwordFindModel = new PasswordFindModel();
        new PasswordFindController(passwordFindView, passwordFindModel);
        passwordFindView.setVisible(true);
    }
}