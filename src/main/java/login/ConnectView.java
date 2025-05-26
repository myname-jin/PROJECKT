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

        connectButton.addActionListener((ActionEvent e) -> handleConnect());

        setVisible(true);
    }

    // 서버 IP로 소켓을 생성하고, LoginController에 전달
    private void handleConnect() {
        String ip = ipField.getText().trim();
        if (!ip.isEmpty()) {
            try {
                // 소켓을 생성하여 LoginController로 전달
                Socket socket = new Socket(ip, 5000);  // 서버에 연결하는 소켓 생성
                new LoginController(socket);  // 생성된 소켓만 전달
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "서버 연결 실패: " + ex.getMessage());
            }
            this.dispose(); // 연결 후 연결 창 닫기
        } else {
            JOptionPane.showMessageDialog(this, "IP를 입력하세요.");
        }
    }
}