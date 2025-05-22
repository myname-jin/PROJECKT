/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JRadioButton userRadio;
    private JRadioButton adminRadio;
    private JButton loginButton;
    private JButton registerButton;
    private JButton findPasswordButton;

    public LoginView() {
        setTitle("로그인 화면");
        setSize(330, 330);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ✅ 컴포넌트 생성
        userIdField = new JTextField(15);
        passwordField = new JPasswordField(15);
        userRadio = new JRadioButton("사용자", true);
        adminRadio = new JRadioButton("관리자");

        ButtonGroup group = new ButtonGroup();
        group.add(userRadio);
        group.add(adminRadio);

        loginButton = new JButton("로그인");
        registerButton = new JButton("회원가입");
        findPasswordButton = new JButton("비밀번호 찾기/변경");

        // ✅ 레이아웃 구성
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // 간격
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("아이디:"), gbc);
        gbc.gridx = 1;
        panel.add(userIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("비밀번호:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(userRadio, gbc);
        gbc.gridx = 1;
        panel.add(adminRadio, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(loginButton, gbc);
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(findPasswordButton, gbc);

        // ✅ 프레임에 적용
        add(panel);
    }

    public String getUserId() {
        return userIdField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword()).trim();
    }

    public String getRole() {
        return userRadio.isSelected() ? "user" : "admin";
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getFindPasswordButton() {
        return findPasswordButton;
    }
}