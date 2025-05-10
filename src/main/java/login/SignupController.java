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

// SignupView와 LoginModel을 중개하는 SIgnupController 클래스 추가
public class SignupController {
    private final SignupView view;
    private final LoginModel model;

    public SignupController(SignupView view, LoginModel model) {
        this.view = view;
        this.model = model;

        view.btnRegister.addActionListener(e -> handleSignup());
    }

    private void handleSignup() {
        String userId = view.getUserId();
        String password = view.getPassword();
        String role = view.getRole();
        // userName 변수에 회원가입 화면의 이름 입력값을 저장
        String userName = view.getUserName();
        // userDept 변수에 회원가입 화면의 학과 입력값을 저장
        String userDept = view.getUserDept();
        

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "아이디와 비밀번호를 입력하세요.");
            return;
        }

        boolean success = model.registerUser(userId, password, role, userName, userDept);

        if (success) {
            JOptionPane.showMessageDialog(view, "회원가입 성공!");
            view.dispose();
            new LoginView().setVisible(true);  // 다시 로그인 화면으로
        } else {
            JOptionPane.showMessageDialog(view, "회원가입 실패. 다시 시도하세요.");
        }
    }
}
