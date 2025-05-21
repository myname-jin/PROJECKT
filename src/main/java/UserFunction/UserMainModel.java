/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;

/**
 *
 * @author jms5310
 */

import java.io.*;
import java.net.Socket;

public class UserMainModel {
  private String userId;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public UserMainModel(String userId, Socket socket, BufferedReader in, BufferedWriter out) {
        // 기본값으로 설정하지만, 유효한 값이 제공되면 그것을 사용
        this.userId = (userId != null && !userId.trim().isEmpty()) ? userId : "20211111";
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
public String getUserName() {
    String name = "사용자"; // 기본값
    
    try {
        File file = new File("src/main/resources/user_signup.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[0].equals(userId)) {
                        name = parts[2]; // 이름은 세 번째 필드
                        break;
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return name;
}
    // Getters
    public String getUserId() { return userId; }
    public Socket getSocket() { return socket; }
    public BufferedReader getIn() { return in; }
    public BufferedWriter getOut() { return out; }
}
