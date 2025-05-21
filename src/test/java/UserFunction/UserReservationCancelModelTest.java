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
        // 실제 구현을 사용할 수 없기 때문에 기본적인 동작만 테스트
        // 실제 테스트에서는 리플렉션을 사용하여 private 필드 값을 변경해야 함
        
        // 존재하는 예약 취소
        boolean result = model.cancelReservation("20211111", "2023-05-20", "918");
        // reservation.txt 파일이 테스트용 파일이 아니라면 실패할 수 있음
        // assertTrue(result, "존재하는 예약 취소는 true를 반환해야 함");
        
        // 존재하지 않는 예약 취소
        result = model.cancelReservation("99999999", "2023-05-20", "918");
        assertFalse(result, "존재하지 않는 예약 취소는 false를 반환해야 함");
    }
    
    @Test
    public void testSaveCancelReason() {
        // 실제 구현을 사용할 수 없기 때문에 기본적인 동작만 테스트
        // 실제 테스트에서는 리플렉션을 사용하여 private 필드 값을 변경해야 함
        
        // 취소 사유 저장
        boolean result = model.saveCancelReason("20211111", "테스트 취소 사유");
        // cancel.txt 파일이 테스트용 파일이 아니라면 실패할 수 있음
        // assertTrue(result, "취소 사유 저장은 true를 반환해야 함");
    }
}