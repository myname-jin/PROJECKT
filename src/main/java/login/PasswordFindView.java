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
//import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PasswordFindView extends JFrame {
    public JTextField txtId = new JTextField();
    public JTextField txtName = new JTextField();
    public JTextField txtOriginalPw = new JTextField();
    public JButton btnOk = new JButton("확인");
    public JRadioButton find = new JRadioButton("찾기");
    public JRadioButton change = new JRadioButton("변경");
    public JLabel labelId = new JLabel("ID:"); // ID: 라벨 생성
    public JLabel labelName = new JLabel("이름:"); // 이름: 라벨 생성
    public JLabel labelPw = new JLabel("PW:"); // PW: 라벨 생성

    public PasswordFindView() {
        super("비밀번호 찾기/변경 화면");
        setSize(350, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtId.setBounds(80, 30, 180, 30);
        txtName.setBounds(80, 70, 180, 30);
        txtOriginalPw.setBounds(80, 110, 180, 30);
        btnOk.setBounds(80, 145, 80, 30);
        find.setBounds(80, 110, 80, 20);
        change.setBounds(160, 110, 80, 20);
        labelId.setBounds(50, 30, 80, 30); // ID: 라벨 위치 지정
        labelPw.setBounds(50, 110, 80, 30); // PW: 라벨 위치 지정
        labelName.setBounds(50, 70, 80, 30); // 이름: 라벨 위치 지정


        ButtonGroup radiogroup = new ButtonGroup();
        radiogroup.add(find);
        radiogroup.add(change);
        find.setSelected(true);

        add(txtId);
        add(txtName);
        add(txtOriginalPw);
        add(btnOk);
        add(find);
        add(change);
        add(labelId); // ID: 라벨 추가
        add(labelPw); // PW: 라벨 추가
        add(labelName); // 이름: 라벨 추가
    }

    public String getId() {
        return txtId.getText().trim();
    }
    public String getOriginalPw() {
        return txtOriginalPw.getText().trim();
    }

    public String getRole() {
        return find.isSelected() ? "찾기" : "변경";
    }
}
