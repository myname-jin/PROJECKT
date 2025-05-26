/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author adsd3
 */
import login.LoginView;
import login.LoginModel;
import login.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

public class ConnectView extends JFrame {
    private JTextField ipField;
    private JButton connectButton;

    public ConnectView() {
        setTitle("서버 연결");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("서버 IP:");
        ipField = new JTextField("127.0.0.1", 15);
        connectButton = new JButton("서버 연결");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(label);
        panel.add(ipField);

        add(panel, BorderLayout.CENTER);
        add(connectButton, BorderLayout.SOUTH);

        connectButton.addActionListener((ActionEvent e) -> {
            String ip = ipField.getText().trim();
            try {
                Socket socket = new Socket(ip, 5000);
                JOptionPane.showMessageDialog(this, "서버 연결 성공");

                LoginView loginView = new LoginView(); // IP칸 없음
                LoginModel loginModel = new LoginModel();
                new LoginController(loginView, loginModel, socket);
                loginView.setVisible(true);

                dispose(); // 연결창 닫기
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "서버 연결 실패: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}