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
public class UserTest {
    
    private static User u1; // 기본 생성자에 의해 만들어진 인스턴스
    private User u2; // 다섯 개의 인자를 가지는 생성자에 의해 만들어진 인스턴스
    
    public UserTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
        u1 = new User(); // this("20143273", "12345678", "맹성진", "컴퓨터소프트웨어공학과", "학생");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
        u2 = new User("msj0228a", "24852954", "성진", "컴퓨터소프트웨어공학과", "학생");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }

    @Test
    public void testDefaultConstructor() {
        System.out.printf("%s: 기본 생성자 테스트%n", u1.getClass().getSimpleName());
        String expId = "20143273";
        String expPassword = "12345678";
        String expName = "맹성진";
        String expDepartment = "컴퓨터소프트웨어공학과";
        String expRole = "학생";
        
        // 결괏값 비교: 예상 결괏값 vs 실제 저장된/계산된 값
        assertEquals(expId, u1.id);
        assertEquals(expPassword, u1.password);
        assertEquals(expName, u1.name);
        assertEquals(expDepartment, u1.department);
        assertEquals(expRole, u1.role);
    }
    
    @Test
    public void testAnotherConstructor() {
        System.out.printf("%s: 다른 생성자 테스트%n", u2.getClass().getSimpleName());
        String expId = "msj0228a";
        String expPassword = "24852954";
        String expName = "성진";
        String expDepartment = "컴퓨터소프트웨어공학과";
        String expRole = "학생";
        
        // 결괏값 비교: 예상 결괏값 vs 실제 저장된/계산된 값
        assertEquals(expId, u2.id);
        assertEquals(expPassword, u2.password);
        assertEquals(expName, u2.name);
        assertEquals(expDepartment, u2.department);
        assertEquals(expRole, u2.role);
    }
    
    /*
    @Test
    public void testGetId() {
        System.out.println("getId");
        User instance = null;
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = null;
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testGetDepartment() {
        System.out.println("getDepartment");
        User instance = null;
        String expResult = "";
        String result = instance.getDepartment();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testGetRole() {
        System.out.println("getRole");
        User instance = null;
        String expResult = "";
        String result = instance.getRole();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testSaveToFile() {
        System.out.println("saveToFile");
        User instance = null;
        instance.saveToFile();
        // TODO review the generated test code and remove the default call to fail.
    }
    */



}
