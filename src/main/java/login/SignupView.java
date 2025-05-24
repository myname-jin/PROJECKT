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
import java.awt.*;

public class SignupView extends JFrame {
    JTextField txtId;
    JPasswordField txtPw;
    private JTextField txtName;
    private JTextField txtDept;
    public JButton btnRegister;
    JComboBox<String> cmbRole;

    public SignupView() {
        super("회원가입");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);

        // Panel with GridLayout for clean label + input alignment
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("PW:"));
        txtPw = new JPasswordField();
        panel.add(txtPw);

        panel.add(new JLabel("이름:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("학과:"));
        txtDept = new JTextField();
        panel.add(txtDept);

        // 역할 콤보 박스
        panel.add(new JLabel("역할:"));
        cmbRole = new JComboBox<>(new String[]{"admin", "학생", "교수"});
        panel.add(cmbRole);

        btnRegister = new JButton("등록");

        // Main layout
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnRegister, BorderLayout.SOUTH);
    }
    
    public String getId() {
        return txtId.getText().trim();
    }

    public String getPw() {
        return new String(txtPw.getPassword()).trim();
    }
    // 이름 입력값을 반환
    public String getName() {
        return txtName.getText().trim();
    }
    // 학과 입력값을 반환
    public String getDept() {
        return txtDept.getText().trim();
    }

    public String getRole() {
        return (String) cmbRole.getSelectedItem();
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }
}
