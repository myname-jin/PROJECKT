/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestLogin;

/**
 *
 * @author adsd3
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TEST_LOGIN.txt 에서 id,password 쌍을 읽어 와서 검증하는 클래스
 */
public class CredentialStore {
    private final Map<String,String> store = new HashMap<>();

    public CredentialStore(String pathToFile) throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    store.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    /** id와 pw가 일치하면 true */
    public boolean validate(String userId, String password) {
        return password.equals(store.get(userId));
    }
}