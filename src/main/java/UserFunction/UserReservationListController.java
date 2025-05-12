/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jms5310
 */
public class UserReservationListController {
    private final JTable table;
    private final String userId = "20221234"; // 임시 학번 (나중에 로그인 연동 예정)
    private final String filePath = "src/main/resources/mgmt_reservation.txt";

    public UserReservationListController(JTable table) {
        this.table = table;
    }

    public void loadReservationData() {
        String[] columns = {"학과", "이름", "학번", "강의실", "시간", "승인상태"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].equals(userId)) {
                    model.addRow(new Object[]{
                        parts[1], parts[2], parts[0], parts[3], parts[4], parts[5]
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "예약 정보를 불러오는 중 오류가 발생했습니다.");
        }

        table.setModel(model);
        table.setAutoCreateRowSorter(true);

        // 승인 상태 컬러 적용
      table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // 기본 색상
        c.setForeground(Color.BLACK);

        // 승인상태 컬럼만 색상 변경
        if (column == 5) {
            String status = table.getValueAt(row, column).toString();

            switch (status) {
                case "승인":
                    c.setForeground(new Color(0, 128, 0)); // 초록
                    break;
                case "거절":
                    c.setForeground(Color.RED); // 빨강
                    break;
                // 대기는 검정 (변경 없음)
            }
        }

        return c;
    }
});
    }
}
