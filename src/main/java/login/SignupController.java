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
            // 새로 회원가입한 정보롤 role에 해당하는 로그인 파일에만 전달하도록 수정
            if (role.equals("admin")){
                model.adminTransfer();
            } else {
                model.userTransfer();
            }
            // 모든 로그인 정보를 예약에 필요한 텍스트 파일로 전달하는 메서드 호출
            model.bothinfoTransfer();
            
            JOptionPane.showMessageDialog(view, "회원가입 성공!");
            view.dispose();
            
            SwingUtilities.invokeLater(() -> {
                LoginView view = new LoginView();
                LoginModel model = new LoginModel();
                LoginController logincontroller = new LoginController(view, model);
                view.setLocationRelativeTo(null);
                view.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(view, "회원가입 실패. 다시 시도하세요.");
        }
    }
}
