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

public class SignupController {
    private final SignupView view;
    private final SignupModel model;

    public SignupController(SignupView view, SignupModel model) {
        this.view = view;
        this.model = model;

        view.btnRegister.addActionListener(e -> handleRegister());
    }

    private void handleRegister() {
        String userId = view.getId();
        String password = view.getPw();
        String role = view.getRole();
        String userName = view.getName();   // userName 변수에 회원가입 화면의 이름 입력값을 저장
        String userDept = view.getDept();   // userDept 변수에 회원가입 화면의 학과 입력값을 저장
        

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "아이디와 비밀번호를 입력하세요.");
            return;
        }

        boolean success = model.registerUser(userId, password, role, userName, userDept);

        if (success) {
            // 회원가입 정보 전달 메서드 추가
            model.adminTransfer();
            model.userTransfer();
            JOptionPane.showMessageDialog(view, "회원가입 성공!");
            view.dispose();
            LoginView loginView = new LoginView();
            LoginModel loginModel = new LoginModel();
            new LoginController(loginView, loginModel);  // ⭐ Controller 연결해줘야 함

            loginView.setVisible(true);  // 로그인 화면 띄우기
            // new LoginView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "회원가입 실패. 다시 시도하세요.");
        }
    }
}
