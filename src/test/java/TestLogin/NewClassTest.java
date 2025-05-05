/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestLogin;

import ServerClient.NewClass;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author adsd3
 */
public class NewClassTest {
    

    private static File testFile;
    private NewClass newClass;

    @BeforeAll
    static void setUpClass() throws IOException {
        // 테스트용 로그인 정보 파일 생성
        testFile = new File("C:/SWG/JAVAPROJECKT/TEST_LOGIN.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("validUser,validPass");
            writer.newLine();
            writer.write("anotherUser,anotherPass");
        }
    }

    @AfterAll
    static void tearDownClass() {
        // 테스트 파일 삭제
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @BeforeEach
    void setUp() {
        newClass = new NewClass();
    }

    @Test
    void testValidateCredentials_Valid() throws Exception {
        Method m = NewClass.class.getDeclaredMethod("validateCredentials", String.class, String.class);
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(newClass, "validUser", "validPass");
        assertTrue(result, "올바른 아이디/비번은 true를 반환해야 합니다");
    }

    @Test
    void testValidateCredentials_InvalidUser() throws Exception {
        Method m = NewClass.class.getDeclaredMethod("validateCredentials", String.class, String.class);
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(newClass, "invalidUser", "validPass");
        assertFalse(result, "존재하지 않는 사용자면 false여야 합니다");
    }

    @Test
    void testValidateCredentials_InvalidPass() throws Exception {
        Method m = NewClass.class.getDeclaredMethod("validateCredentials", String.class, String.class);
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(newClass, "validUser", "wrongPass");
        assertFalse(result, "잘못된 비밀번호면 false여야 합니다");
    }

    @Test
    void testMain_DoesNotThrow() {
        // main()이 예외 없이 실행되어야 합니다
        assertDoesNotThrow(() -> NewClass.main(new String[0]));
    }
}