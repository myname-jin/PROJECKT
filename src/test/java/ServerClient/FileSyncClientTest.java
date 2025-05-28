/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ServerClient;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class FileSyncClientTest {

    @Test
    void testSyncFileSuccessfully() {
        String filename = "Notice.txt";
        File source = new File("src/test/resources/" + filename);

        // ✅ 파일이 없으면 자동 생성
        if (!source.exists()) {
            try (FileWriter writer = new FileWriter(source)) {
                writer.write("📄 테스트용 동기화 내용입니다.");
            } catch (IOException e) {
                fail("❌ 테스트 리소스 생성 실패: " + e.getMessage());
            }
        }

        // ✅ 리소스를 target/test-classes로 복사
        File target = new File("target/test-classes/" + filename);
        try {
            copyFile(source, target);
        } catch (IOException e) {
            fail("❌ 테스트 파일 복사 실패: " + e.getMessage());
        }

        assertTrue(target.exists(), "❌ 테스트 파일이 target/test-classes에 존재하지 않습니다.");

        // ✅ FileSyncClient를 통해 실제 서버에 파일 동기화 테스트
        try {
            Socket socket = new Socket("127.0.0.1", 5000); // 서버가 실행 중이어야 함
            SocketManager.setSocket(socket);

            FileSyncClient.syncFile(filename); // 동기화 시도

            assertTrue(true); // 예외 없으면 성공
        } catch (IOException e) {
            fail("❌ 서버 연결 또는 동기화 실패: " + e.getMessage());
        }
    }

    private void copyFile(File source, File destination) throws IOException {
        destination.getParentFile().mkdirs(); // 경로 없을 경우 생성
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}