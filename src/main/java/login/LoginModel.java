/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author adsd3
 */
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LoginModel {
    
    public boolean validateCredentials(String userId, String password, String role) {
        // 클래스패스에서 파일 읽기
        String fileName = role.equals("admin") ? 
                "/ADMIN_LOGIN.txt" : 
                "/USER_LOGIN.txt";

        try (InputStream input = getClass().getResourceAsStream(fileName)) {
            if (input == null) {
                System.err.println("❌ 파일을 찾을 수 없습니다: " + fileName);
                return false;
            }

            Scanner scanner = new Scanner(input, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2 &&
                        parts[0].trim().equals(userId) &&
                        parts[1].trim().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // 3개의 매개변수를 받는 회원가입 메서드 -> 5개의 매개변수로 변경
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