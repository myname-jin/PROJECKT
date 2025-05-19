/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author msj02
 */
import java.io.*;

public class LoginExtractor {
    public static void main(String[] args) {
        // 파일 경로 일괄 수정 classes -> resources
        String[] sourceFiles = {
            "src/main/resources/ADMIN_LOGIN.txt",
            "src/main/resources/USER_LOGIN.txt"
        };
        String outputFile = "src/main/resources/NEW_LOGIN.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String filePath : sourceFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if (tokens.length >= 2) {
                            String id = tokens[0];
                            String pw = tokens[1];
                            writer.write(id + "," + pw);
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
}
