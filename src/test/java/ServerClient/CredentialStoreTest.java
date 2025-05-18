/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ServerClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CredentialStoreTest {

    static final String TEST_DIR = "src/test/resources";
    static final String TEST_FILE = "test_users.txt";
    static final Path TEST_PATH = Paths.get(TEST_DIR, TEST_FILE);

    @BeforeAll
    static void createTestUserFile() throws IOException {
        // ✅ 디렉토리 없으면 자동 생성
        Files.createDirectories(Paths.get(TEST_DIR));

        // ✅ 테스트용 사용자 데이터 파일 생성
        try (FileWriter writer = new FileWriter(TEST_PATH.toFile())) {
            writer.write("user1,1234\n");
            writer.write("admin,adminpass\n");
            writer.write("guest,guestpw\n");
        }
    }

    @Test
    void testValidLogin() throws IOException {
        CredentialStore store = new CredentialStore(TEST_PATH.toString());
        assertTrue(store.validate("user1", "1234"));
        assertTrue(store.validate("admin", "adminpass"));
    }

    @Test
    void testInvalidPassword() throws IOException {
        CredentialStore store = new CredentialStore(TEST_PATH.toString());
        assertFalse(store.validate("user1", "wrongpw"));
    }

    @Test
    void testNonexistentUser() throws IOException {
        CredentialStore store = new CredentialStore(TEST_PATH.toString());
        assertFalse(store.validate("notexist", "1234"));
    }
}