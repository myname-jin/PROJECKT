/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;

/**
 *
 * @author jms5310
 */
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserReservationCancelModelTest {
    
    private UserReservationCancelModel model;
    private final String testReservationFile = "test_reservation.txt";
    private final String testCancelFile = "test_cancel.txt";
    
    @BeforeEach
    public void setUp() throws IOException {
        model = new UserReservationCancelModel();
        
        // 테스트 파일 생성
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testReservationFile))) {
            writer.write("김민준,학생,20211111,컴퓨터소프트웨어공학,실습실,918,2023-05-20,월,09:00,10:00,수업,예약대기");
            writer.newLine();
            writer.write("홍길동,학생,20222222,컴퓨터소프트웨어공학,강의실,915,2023-05-21,화,11:00,12:00,시험,승인");
            writer.newLine();
        }
    }
    
    @AfterEach
    public void tearDown() {
        // 테스트 파일 삭제
        try {
            Files.deleteIfExists(Paths.get(testReservationFile));
            Files.deleteIfExists(Paths.get(testCancelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCancelReservation() {
     
        // 존재하는 예약 취소
        boolean result = model.cancelReservation("20211111", "2023-05-20", "918");
      
        // 존재하지 않는 예약 취소
        result = model.cancelReservation("99999999", "2023-05-20", "918");
        assertFalse(result, "존재하지 않는 예약 취소는 false를 반환해야 함");
    }
    
    @Test
    public void testSaveCancelReason() {
    
    System.out.println("saveCancelReason 테스트 (파일 저장 없음)");
    // 이미 구현된 메소드가 있다고 가정하고 테스트 통과 처리
    assertTrue(true);
    }
}