/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SignupModel {
    // 5개의 매개변수를 받는 회원가입 메서드
    public boolean registerUser(String userId, String password, String role,  String userName, String userDept) {
        String resourcePath = role.equals("admin") ? "/ADMIN_LOGIN.txt" : "/USER_LOGIN.txt";

        try {
            // 파일 시스템 경로로 변환
            File file = new File(getClass().getResource(resourcePath).toURI());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                // userName과 userDept 추가
                writer.write(userId + "," + password + "," + userName + "," + userDept);
                writer.newLine();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
