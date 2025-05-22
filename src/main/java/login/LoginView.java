/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author adsd3
 */

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField idField, ipField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JButton loginButton;

    public LoginView() {
        setTitle("로그인");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("아이디:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("비밀번호:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("역할:"));
        roleCombo = new JComboBox<>(new String[] { "user", "admin" });
        panel.add(roleCombo);

        panel.add(new JLabel("서버 IP:"));
        ipField = new JTextField("127.0.0.1"); // 기본값 설정
        panel.add(ipField);

        loginButton = new JButton("로그인");
        panel.add(new JLabel()); // 빈 칸
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public String getUserId() {
        return idField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRole() {
        return roleCombo.getSelectedItem().toString();
    }

    public String getServerIp() {
        return ipField.getText();
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}