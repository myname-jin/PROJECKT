/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02
 */
import javax.swing.*;

public class SignupView extends JFrame {
    public JTextField txtUser = new JTextField();
    public JPasswordField txtPass = new JPasswordField();
    public JButton btnSignup = new JButton("회원가입");
    public JRadioButton userRadio = new JRadioButton("사용자");
    public JRadioButton adminRadio = new JRadioButton("관리자");
    // 회원가입 화면에 이름과 학과를 입력받을 객체 생성
    public JTextField txtUsername = new JTextField();
    public JTextField txtDepartment = new JTextField();

    public SignupView() {
        super("회원가입 화면");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtUser.setBounds(80, 30, 180, 30);
        txtPass.setBounds(80, 70, 180, 30);
        // 이름과 학과 텍스트필드 위치 지정
        txtUsername.setBounds(80, 110, 180, 30);
        txtDepartment.setBounds(80, 150, 180, 30);
        userRadio.setBounds(80, 190, 80, 20);
        adminRadio.setBounds(160, 190, 80, 20);
        btnSignup.setBounds(130, 230, 80, 30);
        

        ButtonGroup group = new ButtonGroup();
        group.add(userRadio);
        group.add(adminRadio);
        userRadio.setSelected(true);

        add(txtUser);
        add(txtPass);
        add(btnSignup);
        add(userRadio);
        add(adminRadio);
        // 이름과 학과 텍스트 필드 추가
        add(txtUsername);
        add(txtDepartment);
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