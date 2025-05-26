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
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton registerButton;
    private JButton findPasswordButton;

    public LoginView() {
        setTitle("로그인");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI 설정
        userIdField = new JTextField(20);
        passwordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(new String[]{"학생", "교수", "admin"});
        loginButton = new JButton("로그인");
        registerButton = new JButton("회원가입");
        findPasswordButton = new JButton("비밀번호 찾기");

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("아이디"));
        panel.add(userIdField);
        panel.add(new JLabel("비밀번호"));
        panel.add(passwordField);
        panel.add(new JLabel("역할"));
        panel.add(roleComboBox);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(findPasswordButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public String getUserId() {
        return userIdField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRole() {
        return (String) roleComboBox.getSelectedItem();
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