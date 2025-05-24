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

public class PasswordFindController {
    private final PasswordFindView view;
    private final PasswordFindModel model;

    public PasswordFindController(PasswordFindView view, PasswordFindModel model) {
        this.view = view;
        this.model = model;

        view.btnOk.addActionListener(e -> handleOk());
    }

    private void handleOk() {
        String userId = view.getId();
        String orgPw = view.getOriginalPw();
        String role = view.getRole();
        String userName = view.getName();   // userName 변수에 회원가입 화면의 이름 입력값을 저장
        
        if (userId.isEmpty() || orgPw.isEmpty()) {
            JOptionPane.showMessageDialog(view, "아이디와 비밀번호를 입력하세요.");
            return;
        }        
        
        /*boolean success = model.registerUser(userId, orgPw, role, userName);

        if (success) {
            
            
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
        }*/
    }
}
