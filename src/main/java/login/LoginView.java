/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import javax.swing.*;

public class LoginView extends JFrame {
    public JTextField txtUser = new JTextField();
    public JPasswordField txtPass = new JPasswordField();
    public JButton btnLogin = new JButton("로그인");
    public JRadioButton userRadio = new JRadioButton("사용자");
    public JRadioButton adminRadio = new JRadioButton("관리자");
    public JButton btnSignup = new JButton("회원가입");
    public JButton btnPw = new JButton("비밀번호 찾기/변경 "); // 비밀번호 찾기/변경 버튼 생성
    public JTextField txtServerIp = new JTextField("127.0.0.1"); // 서버 IP 입력 필드 (기본값)

    public LoginView() {
        super("로그인 화면");
        setSize(350, 320);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel ipLabel = new JLabel("서버 IP:");
        ipLabel.setBounds(20, 225, 60, 30);
        txtServerIp.setBounds(80, 225, 180, 30);

        txtUser.setBounds(80, 30, 180, 30);
        txtPass.setBounds(80, 70, 180, 30);
        btnLogin.setBounds(80, 145, 80, 30);
        userRadio.setBounds(80, 110, 80, 20);
        adminRadio.setBounds(160, 110, 80, 20);
        btnSignup.setBounds(170, 145, 85, 30);
        btnPw.setBounds(80, 185, 175, 30);

        ButtonGroup group = new ButtonGroup();
        group.add(userRadio);
        group.add(adminRadio);
        userRadio.setSelected(true);

        add(txtUser);
        add(txtPass);
        add(btnLogin);
        add(userRadio);
        add(adminRadio);
        add(btnSignup);
        add(btnPw);
        add(ipLabel);
        add(txtServerIp);
    }

    public String getUserId() {
        return txtUser.getText().trim();
    }

    public String getPassword() {
        return new String(txtPass.getPassword()).trim();
    }

    public String getRole() {
        return userRadio.isSelected() ? "user" : "admin";
    }

    public String getServerIp() {
        return txtServerIp.getText().trim();
    }

    public JButton getLoginButton() {
        return btnLogin;
    }
}