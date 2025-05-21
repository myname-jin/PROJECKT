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
public class SignupControllerTest {
    // 정적 변수
    private static SignupController s1; // 기본 생성자에 의해 만들어진 인스턴스
    private SignupController s2;
    
    public SignupControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass");
        //s1 = new SignupController();
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

    @Test
    public void testDefaultConstructor() {
        //System.out.printf("%s: 기본 생성자 테스트%n",);
    }
    
}
