/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author suk22
 */
public class UserStatsModelTest {

    @Test
    void testUserStatsModelFields() {
        String name = "홍길동";
        String userId = "20230001";
        int reservationCount = 3;
        int cancelCount = 1;
        String cancelReason = "사정 변경";

        UserStatsModel userStats = new UserStatsModel(name, userId, reservationCount, cancelCount, cancelReason);

        assertEquals(name, userStats.getName());
        assertEquals(userId, userStats.getUserId());
        assertEquals(reservationCount, userStats.getReservationCount());
        assertEquals(cancelCount, userStats.getCancelCount());
        assertEquals(cancelReason, userStats.getCancelReason());
    }
}
