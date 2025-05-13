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
        String[] sourceFiles = {
            "target/classes/ADMIN_LOGIN.txt",
            "target/classes/USER_LOGIN.txt"
        };
        String outputFile = "target/classes/NEW_LOGIN.txt";

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
