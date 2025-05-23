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
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class RegisterTestClient extends JFrame {
    private JTextField idField;
    private JPasswordField pwField;
    private JComboBox<String> roleCombo;
    private JButton registerButton;

    public RegisterTestClient() {
        setTitle("회원가입 테스트");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        idField = new JTextField(15);
        pwField = new JPasswordField(15);
        roleCombo = new JComboBox<>(new String[]{"user", "admin"});
        registerButton = new JButton("회원가입");

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("아이디:"));
        panel.add(idField);
        panel.add(new JLabel("비밀번호:"));
        panel.add(pwField);
        panel.add(new JLabel("역할:"));
        panel.add(roleCombo);
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(this::sendRegister);
    }

    private void sendRegister(ActionEvent e) {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if (id.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 모두 입력해주세요.");
            return;
        }

        try (Socket socket = new Socket("127.0.0.1", 5000);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.write("REGISTER:" + id + "," + pw + "," + role);
            out.newLine();
            out.flush();
            String response = in.readLine();

            if ("REGISTER_SUCCESS".equals(response)) {
                JOptionPane.showMessageDialog(this, "✅ 회원가입 성공!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ 회원가입 실패!");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "서버 연결 오류: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterTestClient().setVisible(true));
    }
}