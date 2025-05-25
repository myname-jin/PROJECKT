///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package ServerClient;
///**
// *
// * @author adsd3
// */
//// 사용자 ID/비밀번호를 파일에서 읽고 자격을 검증하는 클래스
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CredentialStore {
//    private final Map<String, String> credentials = new HashMap<>();
//
//    public CredentialStore() {
//        loadCredentialsFromResource("USER_LOGIN.txt"); // resources 안의 파일 이름
//    }
//
//    private void loadCredentialsFromResource(String resourceName) {
//        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
//            if (is == null) {
//                System.err.println("❌ 리소스 파일을 찾을 수 없습니다: " + resourceName);
//                return;
//            }
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (!line.trim().isEmpty() && line.contains(",")) {
//                    String[] parts = line.split(",");
//                    if (parts.length == 2) {
//                        String id = parts[0].trim();
//                        String pw = parts[1].trim();
//                        credentials.put(id, pw);
//                        System.out.println("✅ 로딩됨: " + id + " / " + pw);
//                    }
//                }
//            }
//            System.out.println("✅ 로그인 정보 로딩 완료. 사용자 수: " + credentials.size());
//
//        } catch (IOException e) {
//            System.err.println("❌ 로그인 정보 파일 읽기 오류:");
//            e.printStackTrace();
//        }
//    }
//
//    public boolean validate(String username, String password) {
//        String storedPw = credentials.get(username);
//        System.out.println("🔍 인증 시도: ID=" + username + ", PW=" + password + " → 저장된 PW=" + storedPw);
//        return storedPw != null && storedPw.equals(password);
//    }
//}