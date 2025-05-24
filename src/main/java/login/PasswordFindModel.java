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
import javax.swing.JOptionPane;
// import java.io.*;

public class PasswordFindModel {
    public boolean registerUser(String userId, String password, String role, String userName, String userDept) {
        
        // role에 따라 저장할 파일 이름 선택
        String fileName = role.equals("admin") ? "admin_signup.txt" : "user_signup.txt";
        
        try {
            File file = new File("src/main/resources/" + fileName);

            // role에 따라 해당되는 파일에 사용자 정보 추가
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(userId + "," + password + "," + userName + "," + userDept + "," + role);
                writer.newLine();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
