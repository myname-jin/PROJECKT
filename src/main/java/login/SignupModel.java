/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SignupModel {
    // 5개의 매개변수를 받는 회원가입 메서드
    public boolean registerUser(String userId, String password, String role, String userName, String userDept) {
        // role에 따라 저장할 파일 이름 선택
        String fileName = role.equals("admin") ? "admin_signup.txt" : "user_signup.txt";

        try {
            File file = new File("src/main/resources/" + fileName);

            // txt파일에 append 모드로 사용자 정보 추가
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(userId + "," + password + "," + userName + "," + userDept);
                writer.newLine();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 관리자 회원가입 정보 txt에서 Id와 Pw를 관리자 로그인 txt로 전달하는 메서드
    public void adminTransfer() {
        String[] sourceFiles = {
            "src/main/resources/admin_signup.txt",
        };
        String outputFile = "src/main/resources/ADMIN_LOGIN.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            for (String filePath : sourceFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if (tokens.length >= 2) {
                            String id = tokens[0];
                            String pw = tokens[1];
                            writer.write("\n"+id + "," + pw);
                            writer.newLine();
                        }
                    }
                }
            }
            System.out.println("ID, PW 추출 완료 → " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 사용자 회원가입 정보 txt에서 Id와 Pw를 사용자 로그인 txt로 전달하는 메서드
    public void userTransfer() {
        String[] sourceFiles = {
            "src/main/resources/user_signup.txt"
        };
        String outFile = "src/main/resources/USER_LOGIN.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true))) {
            for (String filePath : sourceFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if (tokens.length >= 2) {
                            String id = tokens[0];
                            String pw = tokens[1];
                            writer.write("\n"+id + "," + pw);
                            writer.newLine();
                        }
                    }
                }
            }
            System.out.println("ID, PW 추출 완료 → " + outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

