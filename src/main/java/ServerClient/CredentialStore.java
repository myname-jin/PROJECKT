/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerClient;
/**
 *
 * @author adsd3
 */
// 사용자 ID/비밀번호를 파일에서 읽고 자격을 검증하는 클래스


import java.io.*;
import java.util.*;

public class CredentialStore {
    private Map<String, String> credentials = new HashMap<>();
    private File credentialFile;
    private long lastLoadedTime = 0;

    public CredentialStore(String filePath) {
        this.credentialFile = new File(filePath);
        loadCredentials();
        lastLoadedTime = credentialFile.lastModified();
    }

    private void loadCredentials() {
        credentials.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(credentialFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    credentials.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidCredential(String username, String password) {
        if (credentialFile.lastModified() > lastLoadedTime) {
            loadCredentials();
            lastLoadedTime = credentialFile.lastModified();
        }
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }

    public boolean validate(String username, String password) {
        return isValidCredential(username, password);
    }
}