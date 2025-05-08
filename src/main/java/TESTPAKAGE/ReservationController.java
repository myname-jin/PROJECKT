/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TESTPAKAGE;

/**
 *
 * @author adsd3
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationController {
    private final ReservationModel model;
    private final String date;
    private final CalendarView parentView;

    public ReservationController(ReservationModel model, String date, CalendarView parentView) {
        this.model = model;
        this.date = date;
        this.parentView = parentView;
        showBlockTypeDialog();
    }

    private void showBlockTypeDialog() {
        JFrame frame = new JFrame("예약 차단 선택");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 1));

        JButton fullBlockButton = new JButton("모두 차단");
        JButton partialBlockButton = new JButton("차단");
        JButton unblockButton = new JButton("차단 취소");
        JButton cancelButton = new JButton("취소");

        if (model.isFullyBlocked(date)) {
            partialBlockButton.setEnabled(false);
            partialBlockButton.setToolTipText("이미 모두 차단된 날짜입니다.");
        }

        fullBlockButton.addActionListener(e -> {
            model.saveReservation("모두", date, "", "", "");
            JOptionPane.showMessageDialog(frame, "전체 예약 차단 완료");
            parentView.populateCalendar();
            frame.dispose();
        });

        partialBlockButton.addActionListener(e -> showRoomTypeDialog(frame, "일부"));

        unblockButton.addActionListener(e -> {
            model.removeReservation(date);
            JOptionPane.showMessageDialog(frame, "차단 취소 완료");
            parentView.populateCalendar();
            frame.dispose();
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.add(fullBlockButton);
        frame.add(partialBlockButton);
        frame.add(unblockButton);
        frame.add(cancelButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showRoomTypeDialog(JFrame parentFrame, String blockType) {
        JFrame frame = new JFrame("강의실/실습실 선택");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 1));

        JButton lectureRoomButton = new JButton("강의실");
        JButton labRoomButton = new JButton("실습실");
        JButton cancelButton = new JButton("취소");

        lectureRoomButton.addActionListener(e -> showRoomNumberDialog(frame, blockType, "강의실"));
        labRoomButton.addActionListener(e -> showRoomNumberDialog(frame, blockType, "실습실"));
        cancelButton.addActionListener(e -> frame.dispose());

        frame.add(lectureRoomButton);
        frame.add(labRoomButton);
        frame.add(cancelButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showRoomNumberDialog(JFrame parentFrame, String blockType, String roomType) {
        JFrame frame = new JFrame(roomType + " 호수 선택");
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(2, 4));

        for (int i = 911; i <= 918; i++) {
            String roomNumber = String.valueOf(i);
            JButton roomButton = new JButton(roomNumber);
            roomButton.addActionListener(e -> showTimeSlotDialog(frame, blockType, roomType, roomNumber));
            frame.add(roomButton);
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showTimeSlotDialog(JFrame parentFrame, String blockType, String roomType, String roomNumber) {
        JFrame frame = new JFrame("시간대 선택");
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(4, 2));

        // 📅 새로운 1~8 시간대 (1시간 단위)
        String[] timeSlots = {
            "1 (9-10시)", "2 (10-11시)", "3 (11-12시)", "4 (12-13시)",
            "5 (13-14시)", "6 (14-15시)", "7 (15-16시)", "8 (16-17시)"
        };

        List<String> blockedSlots = model.getBlockedTimeSlots(date, roomType, roomNumber);

        for (String timeSlot : timeSlots) {
            String slotNumber = timeSlot.split(" ")[0];
            JButton timeButton = new JButton(timeSlot);
            if (blockedSlots.contains(slotNumber)) {
                timeButton.setEnabled(false);
                timeButton.setToolTipText("이미 차단된 시간대입니다.");
            }
            timeButton.addActionListener(e -> {
                model.saveReservation(blockType, date, roomType, roomNumber, slotNumber);
                JOptionPane.showMessageDialog(frame, "예약 차단 설정 완료");
                parentView.populateCalendar();
                frame.dispose();
                parentFrame.dispose();
            });
            frame.add(timeButton);
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
