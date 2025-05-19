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

    // Getters
    public String getUserId() { return userId; }
    public Socket getSocket() { return socket; }
    public BufferedReader getIn() { return in; }
    public BufferedWriter getOut() { return out; }
}
