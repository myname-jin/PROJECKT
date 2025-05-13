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
    public JTextField txtId = new JTextField();           // ID
    public JPasswordField txtPw = new JPasswordField();   // PW
    public JTextField txtName = new JTextField();         // 이름
    public JTextField txtDept = new JTextField();         // 학과
    public JButton btnRegister = new JButton("등록");
    public JRadioButton user = new JRadioButton("사용자");
    public JRadioButton admin = new JRadioButton("관리자");


    public SignupView() {
        super("회원가입 화면");
        setSize(350, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtId.setBounds(80, 30, 180, 30);
        txtPw.setBounds(80, 70, 180, 30);
        txtName.setBounds(80, 110, 180, 30);
        txtDept.setBounds(80, 150, 180, 30);
        user.setBounds(80, 190, 80, 20);
        admin.setBounds(160, 190, 80, 20);
        btnRegister.setBounds(130, 230, 80, 30);
        

        ButtonGroup group = new ButtonGroup();
        group.add(user);
        group.add(admin);
        user.setSelected(true);

        add(txtId);
        add(txtPw);
        add(btnRegister);
        add(user);
        add(admin);
        add(txtName);
        add(txtDept);
    }

    public String getId() {
        return txtId.getText().trim();
    }

    public String getPw() {
        return new String(txtPw.getPassword()).trim();
    }

    public String getRole() {
        return user.isSelected() ? "user" : "admin";
    }
    // 이름 입력값을 반환
    public String getName() {
        return txtName.getText().trim();
    }
    // 학과 입력값을 반환
    public String getDept() {
        return txtDept.getText().trim();
    }
}
