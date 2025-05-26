/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02
 */
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SignupController {
    private final SignupView view;
    private final SignupModel model;

    public SignupController(SignupView view, SignupModel model) {
        this.view = view;
        this.model = model;

        // 등록 버튼 → 서버로 전송
        view.btnRegister.addActionListener(this::sendRegister);
        // 뒤로가기 버튼 → 로그인 화면으로
        view.btnBack.addActionListener(e -> {
            view.dispose();
            SwingUtilities.invokeLater(() -> {
                LoginView loginView = new LoginView();
                LoginModel loginModel = new LoginModel();
                new LoginController(loginView, loginModel);
                loginView.setLocationRelativeTo(null);
                loginView.setVisible(true);
            });
        });
    }

    private void sendRegister(ActionEvent e) {
        String id   = view.getId();
        String pw   = view.getPw();
        String name = view.getName();
        String dept = view.getDept();
        String role = view.getRole();

        // 빈칸 체크
        if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(view, "모든 항목을 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 서버 연결 & 메시지 전송
        try (Socket socket = new Socket("127.0.0.1", 5000);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 프로토콜: REGISTER:role:id:pw:name:dept
            String msg = String.join(":", "REGISTER", role, id, pw, name, dept);
            out.write(msg);
            out.newLine();
            out.flush();

            // 서버 응답 처리
            String response = in.readLine();
            if ("REGISTER_SUCCESS".equals(response)) {
                JOptionPane.showMessageDialog(view, "회원가입 되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();

                // 가입 후 로그인 화면으로 이동
                SwingUtilities.invokeLater(() -> {
                    LoginView loginView = new LoginView();
                    LoginModel loginModel = new LoginModel();
                    new LoginController(loginView, loginModel);
                    loginView.setLocationRelativeTo(null);
                    loginView.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "회원가입에 실패하였습니다.", "에러", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "서버 연결 오류: " + ex.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
        }
    }
}