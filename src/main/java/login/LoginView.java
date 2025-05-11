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

public class LoginView extends JFrame {
    public JTextField txtUser = new JTextField();
    public JPasswordField txtPass = new JPasswordField();
    public JButton btnLogin = new JButton("로그인");
    public JRadioButton userRadio = new JRadioButton("사용자");
    public JRadioButton adminRadio = new JRadioButton("관리자");

    public LoginView() {
        super("로그인 화면");
        setSize(350, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtUser.setBounds(80, 30, 180, 30);
        txtPass.setBounds(80, 70, 180, 30);
        btnLogin.setBounds(130, 160, 80, 30);
        userRadio.setBounds(80, 110, 80, 20);
        adminRadio.setBounds(160, 110, 80, 20);

        ButtonGroup group = new ButtonGroup();
        group.add(userRadio);
        group.add(adminRadio);
        userRadio.setSelected(true);

        add(txtUser);
        add(txtPass);
        add(btnLogin);
        add(userRadio);
        add(adminRadio);
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
}