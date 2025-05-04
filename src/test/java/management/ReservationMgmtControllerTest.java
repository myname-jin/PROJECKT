/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author suk22
 */
public class ReservationMgmtControllerTest {
        private static final String TEST_FILE_PATH = "mgmt_reservation.txt";

    @BeforeEach
    void setUpTestFile() throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.println("20230001,컴퓨터공학과,홍길동,101호,10:00,승인됨");
            writer.println("20230002,기계공학과,이몽룡,201호,11:00,대기중");
        }
    }

    @Test
    void testGetAllReservations() {
        ReservationMgmtController controller = new ReservationMgmtController();
        List<ReservationMgmtModel> reservations = controller.getAllReservations();

        assertEquals(2, reservations.size());

        ReservationMgmtModel r1 = reservations.get(0);
        assertEquals("20230001", r1.getStudentId());
        assertEquals("컴퓨터공학과", r1.getDepartment());
        assertEquals("홍길동", r1.getName());
        assertEquals("101호", r1.getRoom());
        assertEquals("10:00", r1.getTime());
    }

    @AfterEach
    void cleanUpTestFile() throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.print(""); // 테스트 후 내용 비우기
        }
    }
}


