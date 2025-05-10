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


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 회원가입으로 .txt파일에 추가된 사용자들도 로그인할 수 있도록 수정
public class CredentialStore {
    private final Map<String, String> store = new HashMap<>();
    private final String pathToFile;

    public CredentialStore(String pathToFile) throws IOException {
        this.pathToFile = pathToFile;
        load();
    }

    /** 초기 로딩 및 수동 리로드용 */
    public synchronized void reload() {
        store.clear();
        try (BufferedReader r = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    store.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 내부에서 초기 로딩 */
    private void load() throws IOException {
        reload();
    }

    /** id와 pw가 일치하면 true */
    public synchronized boolean validate(String userId, String password) {
        return password.equals(store.get(userId));
    }
}
