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

public class LoginModel {

    public boolean validateCredentials(String userId, String password, String role) {
        String filePath = role.equals("admin")
                ? "C:/SWG/JAVAPROJECKT/ADMIN_LOGIN.txt"
                : "C:/SWG/JAVAPROJECKT/USER_LOGIN.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 &&
                        parts[0].trim().equals(userId) &&
                        parts[1].trim().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}