/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

/**
 *
 * @author adsd3
 */
// 호수(911~918) 선택 3×3 그리드 UI
import javax.swing.*;
import java.awt.*;

/**
 * 호수 선택 다이얼로그 (911~918, 총 8개 버튼 + 취소 1개 = 9개)
 * 3×3 그리드로 빈 칸 없이 배치하고, 충분히 큰 크기로 중앙에 띄웁니다.
 */
public class RoomNumberDialogView {
    /** 선택 콜백 인터페이스 */
    public interface Handler {
        void onSelect(String roomNumber);  // 호수 선택 시 호출
        void onCancel();                   // 취소 시 호출
    }

    private JDialog dialog;
    private Handler handler;

    /** 콜백 핸들러 설정 */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /** 다이얼로그 표시 */
    public void show() {
        dialog = new JDialog((Frame) null, "호수 선택", true);
        dialog.setLayout(new GridLayout(3, 3, 10, 10));  // 3×3, 버튼 사이 10px 간격

        // 911 ~ 918 버튼
        for (int i = 911; i <= 918; i++) {
            String num = String.valueOf(i);
            JButton btn = new JButton(num);
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, 16f));  // 폰트 볼드·크기 16
            btn.addActionListener(e -> {
                if (handler != null) handler.onSelect(num);
                dialog.dispose();
            });
            dialog.add(btn);
        }

        // 마지막 셀에 취소 버튼
        JButton cancelBtn = new JButton("취소");
        cancelBtn.setFont(cancelBtn.getFont().deriveFont(Font.BOLD, 16f));
        cancelBtn.addActionListener(e -> {
            if (handler != null) handler.onCancel();
            dialog.dispose();
        });
        dialog.add(cancelBtn);

        // 다이얼로그 크기 및 위치
        dialog.setSize(500, 320);             // 충분히 넉넉한 크기
        dialog.setResizable(false);           // 크기 변경 불가
        dialog.setLocationRelativeTo(null);   // 화면 중앙
        dialog.setVisible(true);
    }

    /** 프로그래밍적으로 닫기 */
    public void close() {
        if (dialog != null) {
            dialog.dispose();
        }
    }
}