/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author msj02
 */
public class LoginModelTest {
    
    public LoginModelTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }

    /**
     * Test of validateCredentials method, of class LoginModel.
     */
    @Test
    public void testValidateCredentials() {
        System.out.println("로그인 검증 테스트: role에 해당하는 txt 파일 정보와 일치하면 passed");
        String userId = "test0524";
        String password = "0524";
        String role = "admin";
        LoginModel instance = new LoginModel();
        boolean expResult = true;
        boolean result = instance.validateCredentials(userId, password, role);
        assertEquals(expResult, result);
    }
    
}
