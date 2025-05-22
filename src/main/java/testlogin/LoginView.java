/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testlogin;

/**
 *
 * @author adsd3
 */
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JButton loginButton;

    public LoginView() {
        setTitle("로그인");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userIdField = new JTextField(15);
        passwordField = new JPasswordField(15);
        roleCombo = new JComboBox<>(new String[]{"user", "admin"});
        loginButton = new JButton("로그인");

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("아이디:"));
        panel.add(userIdField);
        panel.add(new JLabel("비밀번호:"));
        panel.add(passwordField);
        panel.add(new JLabel("역할:"));
        panel.add(roleCombo);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }

    public String getUserId() {
        return userIdField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword()).trim();
    }

    public String getRole() {
        return roleCombo.getSelectedItem().toString();
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}