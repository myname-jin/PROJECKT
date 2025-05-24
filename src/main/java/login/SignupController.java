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
import javax.swing.*;

public class SignupController {
    private final SignupView view;
    private final SignupModel model;

    public SignupController(SignupView view, SignupModel model) {
        this.view = view;
        this.model = model;

        //view.btnRegister.addActionListener(e -> handleRegister());
        view.btnRegister.addActionListener(this::sendRegister);
        view.btnBack.addActionListener(e -> {
            view.dispose();
            
            SwingUtilities.invokeLater(() -> {
                LoginView loginview = new LoginView();
                LoginModel loginmodel = new LoginModel();
                new LoginController(loginview, loginmodel);
                loginview.setLocationRelativeTo(null);
                loginview.setVisible(true);
            });
        });

    }
    private void sendRegister(ActionEvent e) {
        String id = view.txtId.getText().trim();
        String pw = new String(view.txtPw.getPassword()).trim();
        String role = (String) view.cmbRole.getSelectedItem();
        
        if (id.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(view, "아이디와 비밀번호를 모두 입력해주세요.");
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
                JOptionPane.showMessageDialog(view, "회원가입 되었습니다.");
                view.dispose();
                
                SwingUtilities.invokeLater(() -> {
                LoginView view = new LoginView();
                LoginModel model = new LoginModel();
                LoginController logincontroller = new LoginController(view, model);
                view.setLocationRelativeTo(null);
                view.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "회원가입에 실패하였습니다.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "서버 연결 오류: " + ex.getMessage());
        }
    }
}