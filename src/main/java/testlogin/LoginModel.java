/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testlogin;

/**
 *
 * @author adsd3
 */
public class LoginModel {
    public String getFilePath(String role) {
        return role.equals("admin") ? "src/main/resources/ADMIN_LOGIN.txt" : "src/main/resources/USER_LOGIN.txt";
    }
}
