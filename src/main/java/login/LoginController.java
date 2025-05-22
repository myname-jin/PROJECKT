/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import login.UserFileWatcher;
import ruleagreement.RuleAgreementController;
import management.ReservationMgmtView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class LoginController {
    private final LoginView view;
    private final LoginService loginService;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.loginService = new LoginService(model, view);

        // 로그인 버튼 이벤트
        view.getLoginButton().addActionListener(e -> loginService.attemptLogin());

        // ✅ 감시 쓰레드 자동 실행
        try {
            Socket watcherSocket = new Socket(view.getServerIp().trim(), 5000);
            new UserFileWatcher("USER_LOCAL.txt", watcherSocket).start();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "회원가입 감시 시작 실패: " + ex.getMessage());
        }
    }
}