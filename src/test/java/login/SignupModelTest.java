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
public class SignupModelTest {
    
    public SignupModelTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUp");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("tearDown");
    }

    /**
     * Test of registerUser method, of class SignupModel.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser 테스트: 빈 값 입력하거나 role에 admin또는user 외 값 입력 시 false");
        String userId = "s87654321";
        String password = "87654321";
        String role = "else";
        String userName = "테스트";
        String userDept = "컴퓨터소프트웨어공학과";
        SignupModel instance = new SignupModel();
        boolean expResult = false;
        boolean result = instance.registerUser(userId, password, role, userName, userDept);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of adminTransfer method, of class SignupModel.
     
    @Test
    public void testAdminTransfer() {
        System.out.println("adminTransfer");
        SignupModel instance = new SignupModel();
        instance.adminTransfer();
    }

    **
     * Test of userTransfer method, of class SignupModel.
     
    @Test
    public void testUserTransfer() {
        System.out.println("userTransfer");
        SignupModel instance = new SignupModel();
        instance.userTransfer();
    }

    **
     * Test of bothinfoTransfer method, of class SignupModel.
     
    @Test
    public void testBothinfoTransfer() {
        System.out.println("bothinfoTransfer");
        SignupModel instance = new SignupModel();
        instance.bothinfoTransfer();
    }*/
    
}
