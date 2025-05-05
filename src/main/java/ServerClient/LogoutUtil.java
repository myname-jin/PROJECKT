/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerClient;
/**
 *
 * @author adsd3
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

// 창 종료 시 로그아웃 처리를 공통으로 수행하는 유틸 클래스

public class LogoutUtil {
    public static void attach(JFrame frame, String userId, Socket socket, BufferedWriter out) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("[공통 로그아웃] 창 종료 이벤트 발생: " + userId);
                try {
                    out.write("LOGOUT:" + userId + "\n");
                    out.flush();
                    socket.close();
                    System.out.println("[공통 로그아웃] 로그아웃 메시지 전송 완료");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
