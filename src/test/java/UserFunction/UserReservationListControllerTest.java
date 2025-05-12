/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;
import org.junit.jupiter.api.Test;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author jms5310
 */
public class UserReservationListControllerTest {
   @Test
public void testLoadReservationDataOnlyFor20221234() {
    JTable table = new JTable();
    UserReservationListController controller = new UserReservationListController(table);
    controller.loadReservationData();

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int rowCount = model.getRowCount();

    System.out.println("✅ 로드된 예약 수: " + rowCount);

    for (int i = 0; i < rowCount; i++) {
        String studentId = model.getValueAt(i, 2).toString(); // index 2 = 학번
        assertEquals("20221234", studentId, "다른 학번이 포함되어 있음!");

        // 콘솔 출력: 학번 + 예약 정보 요약
        String info = String.join(" | ",
                model.getValueAt(i, 2).toString(), // 학번
                model.getValueAt(i, 1).toString(), // 이름
                model.getValueAt(i, 0).toString(), // 학과
                model.getValueAt(i, 3).toString(), // 강의실
                model.getValueAt(i, 4).toString(), // 시간
                model.getValueAt(i, 5).toString()  // 승인상태
        );
        System.out.println("예약 정보: " + info);
    }
}
}
