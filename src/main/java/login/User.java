/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02    model
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class User {
    String id;
    String password;
    String name;
    String department;
    String role; // 관리자, 교수, 학생 등

    // 기본 생성자: 매개변수가 없는 생성자
    public User() {
        this("20143273", "12345678", "맹성진", "컴퓨터소프트웨어공학과", "학생");
    }
    
    // 매개변수가 5개 있는 생성자
    public User(String id, String password, String name, String department, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.department = department;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    // 사용자 정보를 파일에 저장하는 메서드
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(role.equals("관리자") ? "admin_accounts.txt" : "user_accounts.txt", true))) {
            writer.write(this.id + "," + this.password + "," + this.name + "," + this.department + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
