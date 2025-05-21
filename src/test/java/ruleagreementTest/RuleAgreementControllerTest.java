/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ruleagreementTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;
import ruleagreement.RuleAgreementController;

/**
 * RuleAgreementController 테스트 클래스
 * 
 * @author jms5310
 */
public class RuleAgreementControllerTest {

    public RuleAgreementControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    
     // 생성자 테스트
     
    @Test
    public void testConstructor() throws Exception {
        System.out.println("RuleAgreementController 생성자 테스트");
        
        // 테스트 파라미터
        String userId = "testUser";
        
        // 더미 Socket과 BufferedWriter 생성
        Socket dummySocket = new Socket() {
            @Override
            public void close() throws IOException {
                // 오버라이드하여 실제 소켓을 닫지 않음
            }
        };
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter dummyOut = new BufferedWriter(stringWriter);
        
        // 컨트롤러 생성 - 예외가 발생하지 않아야 함
        RuleAgreementController controller = null;
        try {
            controller = new RuleAgreementController(userId, dummySocket, dummyOut);
            assertNotNull(controller, "컨트롤러 객체가 생성되어야 함");
        } catch (Exception e) {
            fail("컨트롤러 생성 중 예외 발생 : " + e.getMessage());
        }
    }
}