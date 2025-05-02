/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;
import UserFunction.UserReservationListForm;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jms5310
 */
public class UserReservationListFormTest {
    @Test
    public void testLoadReservationData() {
        UserReservationListForm form = new UserReservationListForm();
DefaultTableModel model = (DefaultTableModel) form.getTable().getModel();

        // 행 개수 확인
        assertEquals(3, model.getRowCount());

        // 첫 번째 행의 데이터 확인
        assertEquals("912", model.getValueAt(0, 0));  // 강의실
        assertEquals("2025-04-20", model.getValueAt(0, 1));  // 날짜
        assertEquals("10:00~12:00", model.getValueAt(0, 2));  // 시간
        assertEquals("승인", model.getValueAt(0, 3));  // 예약상태
    }
}
