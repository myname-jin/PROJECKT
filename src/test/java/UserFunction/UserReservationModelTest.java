/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package UserFunction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jms5310
 */
public class UserReservationModelTest {
    
    private UserReservationModel model;
    private final String studentId = "20211234";
    private final String department = "컴퓨터소프트웨어공학";
    private final String name = "홍길동";
    private final String room = "911";
    private final String time = "10:00~12:00";
    private final String status = "승인";
    
    public UserReservationModelTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("UserReservationModelTest - 테스트 클래스 초기화");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("UserReservationModelTest - 테스트 클래스 정리");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("각 테스트 전 모델 객체 생성");
        // 테스트 데이터로 모델 생성
        model = new UserReservationModel(studentId, department, name, room, time, status);
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("각 테스트 후 모델 객체 정리");
        model = null;
    }

    /**
     * getStudentId 메소드 테스트
     */
    @Test
    public void testGetStudentId() {
        System.out.println("getStudentId 테스트");
        assertEquals(studentId, model.getStudentId(), "학번이 올바르게 반환되어야 합니다");
    }

    /**
     * getDepartment 메소드 테스트
     */
    @Test
    public void testGetDepartment() {
        System.out.println("getDepartment 테스트");
        assertEquals(department, model.getDepartment(), "학과가 올바르게 반환되어야 합니다");
    }

    /**
     * getName 메소드 테스트
     */
    @Test
    public void testGetName() {
        System.out.println("getName 테스트");
        assertEquals(name, model.getName(), "이름이 올바르게 반환되어야 합니다");
    }

    /**
     * getRoom 메소드 테스트
     */
    @Test
    public void testGetRoom() {
        System.out.println("getRoom 테스트");
        assertEquals(room, model.getRoom(), "강의실이 올바르게 반환되어야 합니다");
    }

    /**
     * getTime 메소드 테스트
     */
    @Test
    public void testGetTime() {
        System.out.println("getTime 테스트");
        assertEquals(time, model.getTime(), "시간이 올바르게 반환되어야 합니다");
    }

    /**
     * getStatus 메소드 테스트
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus 테스트");
        assertEquals(status, model.getStatus(), "상태가 올바르게 반환되어야 합니다");
    }

    /**
     * toString 메소드 테스트
     */
    @Test
    public void testToString() {
        System.out.println("toString 테스트");
        String expected = studentId + "," + department + "," + name + "," + room + "," + time + "," + status;
        assertEquals(expected, model.toString(), "toString 메소드가 올바른 형식으로 반환해야 합니다");
    }
    
    /**
     * 테스트를 수정하여 null 값이 들어갈 때 null로 유지되는지 확인합니다.
     */
    @Test
    public void testConstructorWithNullValues() {
        System.out.println("null 값으로 생성자 테스트");
        UserReservationModel nullModel = new UserReservationModel(null, null, null, null, null, null);
        
        // UserReservationModel 클래스의 실제 동작이 null 값을 그대로 유지하는 경우
        assertNull(nullModel.getStudentId(), "생성자에 null 학번을 전달하면 null을 반환해야 합니다");
        assertNull(nullModel.getDepartment(), "생성자에 null 학과를 전달하면 null을 반환해야 합니다");
        assertNull(nullModel.getName(), "생성자에 null 이름을 전달하면 null을 반환해야 합니다");
        assertNull(nullModel.getRoom(), "생성자에 null 강의실을 전달하면 null을 반환해야 합니다");
        assertNull(nullModel.getTime(), "생성자에 null 시간을 전달하면 null을 반환해야 합니다");
        assertNull(nullModel.getStatus(), "생성자에 null 상태를 전달하면 null을 반환해야 합니다");
        
     
    }
    
    /**
     * 빈 문자열로 생성자 테스트
     */
    @Test
    public void testConstructorWithEmptyValues() {
        System.out.println("빈 문자열로 생성자 테스트");
        UserReservationModel emptyModel = new UserReservationModel("", "", "", "", "", "");
        
        // 빈 문자열은 그대로 유지
        assertEquals("", emptyModel.getStudentId(), "빈 학번은 그대로 유지되어야 합니다");
        assertEquals("", emptyModel.getDepartment(), "빈 학과는 그대로 유지되어야 합니다");
        assertEquals("", emptyModel.getName(), "빈 이름은 그대로 유지되어야 합니다");
        assertEquals("", emptyModel.getRoom(), "빈 강의실은 그대로 유지되어야 합니다");
        assertEquals("", emptyModel.getTime(), "빈 시간은 그대로 유지되어야 합니다");
        assertEquals("", emptyModel.getStatus(), "빈 상태는 그대로 유지되어야 합니다");
        
        // toString은 CSV 형식을 유지
        assertEquals(",,,,,", emptyModel.toString(), "toString은 빈 필드를 포함한 CSV 형식이어야 합니다");
    }
}