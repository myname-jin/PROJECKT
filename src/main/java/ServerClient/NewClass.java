/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerClient;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

// 로그인 화면을 제공하고 서버와 통신하는 초기 뷰/컨트롤러



public class NewClass extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public NewClass() {
        super("로그인 화면");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);
        setLayout(null);

        txtUser = new JTextField("아이디");
        txtUser.setBounds(80, 30, 180, 30);
        add(txtUser);

        txtPass = new JPasswordField("비밀번호");
        txtPass.setBounds(80, 80, 180, 30);
        add(txtPass);

        btnLogin = new JButton("로그인");
        btnLogin.setBounds(130, 130, 80, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String userId = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        // 메모장에서 아이디/비번 확인
        if (!validateCredentials(userId, password)) {
            JOptionPane.showMessageDialog(this, "ID 또는 비밀번호가 올바르지 않습니다.", 
                                          "로그인 실패", JOptionPane.ERROR_MESSAGE);
            return;
        }

        btnLogin.setEnabled(false);
        final JFrame currentFrame = this;

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 12345);
                in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                out.write("LOGIN:" + userId + ":" + password + "\n");
                out.flush();

                String response;
                boolean waitingMessageShown = false;

                while ((response = in.readLine()) != null) {
                    System.out.println("서버 응답: " + response);

                    if (response.startsWith("OK")) {
                        SwingUtilities.invokeLater(() -> {
                            new NextPage(userId, socket, in, out).setVisible(true);
                            currentFrame.dispose();
                        });
                        break;
                    } else if (response.startsWith("WAIT")) {
                        if (!waitingMessageShown) {
                            waitingMessageShown = true;
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(currentFrame,
                                    "접속 대기 중입니다. 자리가 나면 자동 접속됩니다.",
                                    "대기 중", JOptionPane.INFORMATION_MESSAGE);
                            });
                        }
                        // 계속 대기
                    } else if (response.startsWith("FAIL")) {
                        final String msg = response;
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(currentFrame, msg, 
                                                          "접속 거부", JOptionPane.WARNING_MESSAGE);
                            btnLogin.setEnabled(true);
                        });
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(currentFrame, 
                        "서버 연결 실패: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    btnLogin.setEnabled(true);
                });
            }
        }).start();
    }

    private boolean validateCredentials(String userId, String password) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader("C:/SWG/JAVAPROJECKT/TEST_LOGIN.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 &&
                    parts[0].trim().equals(userId) &&
                    parts[1].trim().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewClass().setVisible(true));
    }
}