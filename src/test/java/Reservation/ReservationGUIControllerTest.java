/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Reservation;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author scq37
 */
public class ReservationGUIControllerTest {

    @Test
    void testCalculateTotalDuration() {
        ReservationGUIController controller = new ReservationGUIController();
        List<String> times = List.of("09:00~10:00", "10:00~10:30");
        
        int total = controller.calculateTotalDuration(times);
        
        System.out.println("🧪 [총 예약 시간 계산 테스트]");
        System.out.println("🧪 입력값: " + times);
        System.out.println("✅ 예상 결과: 90");
        System.out.println("🔢 실제 결과: " + total);
        
        assertEquals(90, total);
    }

    @Test
    void testGetDayColumnIndex() {
        ReservationGUIController controller = new ReservationGUIController();
        
        System.out.println("🧪 [요일 인덱스 계산 테스트]");
        String date1 = "2025-05-19"; // Monday
        String date2 = "2025-05-20"; // Tuesday
        String invalidDate = "invalid-date";

        System.out.println("입력 날짜 1: " + date1 + " → 예상 인덱스: 1");
        System.out.println("입력 날짜 2: " + date2 + " → 예상 인덱스: 2");
        System.out.println("입력 날짜 3: " + invalidDate + " → 예상 인덱스: -1\n");
        
        assertEquals(1, controller.getDayColumnIndex("2025-05-19")); // Monday
        assertEquals(2, controller.getDayColumnIndex("2025-05-20")); // Tuesday
        assertEquals(-1, controller.getDayColumnIndex("invalid-date")); // Invalid
    }

    @Test
    void testGetDayOfWeek() {
        ReservationGUIController controller = new ReservationGUIController();
        
        String date1 = "2025-05-19"; // Monday
        String date2 = "2025-05-20"; // Tuesday

        String result1 = controller.getDayOfWeek(date1);
        String result2 = controller.getDayOfWeek(date2);

        System.out.println("🧪 [한글 요일 반환 테스트]");
        System.out.println("입력 날짜: " + date1 + " → 기대 요일: 월 → 실제 요일: " + result1);
        System.out.println("입력 날짜: " + date2 + " → 기대 요일: 화 → 실제 요일: " + result2 + "\n");

        
        assertEquals("월", controller.getDayOfWeek("2025-05-19"));
        assertEquals("화", controller.getDayOfWeek("2025-05-20"));
    }
    
    @Test
    void testGetAvailableTimesByDay() {
        
        //  가상의 Excel Sheet 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("913");

        // 첫 행: 시간대 제목 (예: "09:00~10:00", "10:00~11:00")
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("09:00~10:00");
        row1.createCell(1).setCellValue("비어있음");

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("10:00~11:00");
        row2.createCell(1).setCellValue("사용중");

        Row row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("11:00~12:00");
        row3.createCell(1).setCellValue("비어있음");

        // 테스트 대상 메서드 호출
        ReservationGUIController controller = new ReservationGUIController();
        List<String> availableTimes = controller.getAvailableTimesByDay(sheet, 1); // 열 인덱스 1 (월요일)

        //  출력 및 검증
        System.out.println("🧪 [엑셀 시간대 추출 테스트]");
        System.out.println("추출된 예약 가능 시간대: " + availableTimes);

        assertEquals(2, availableTimes.size());
        assertEquals("09:00~10:00", availableTimes.get(0));
        assertEquals("11:00~12:00", availableTimes.get(1));
    }
}


