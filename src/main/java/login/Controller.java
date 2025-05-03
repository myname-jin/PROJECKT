/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02    controller
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private NewJFrame view;

    public Controller(NewJFrame view) {
        this.view = view;

        // 뷰에서 버튼에 이벤트 리스너를 추가하는 대신, 컨트롤러에서 설정합니다.
        view.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
    }

    // 회원가입 처리
    public void handleSignUp() {
        // 뷰에서 입력값을 가져옵니다.
        String id = view.getId();
        String password = view.getPassword();
        String name = view.getName();
        String department = view.getDepartment();
        String role = view.getSelectedRole();

        // 사용자 정보를 모델에 전달하여 저장
        User newUser = new User(id, password, name, department, role);
        newUser.saveToFile();

        // 성공 메시지
        view.showMessage(role + " 계정이 등록되었습니다!");
    }
}