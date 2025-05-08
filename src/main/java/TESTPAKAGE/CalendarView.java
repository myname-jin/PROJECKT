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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CalendarView extends JFrame {
    private final ReservationModel model;
    private final JPanel calendarPanel = new JPanel(new GridLayout(6, 7));
    private LocalDate currentDate;

    public CalendarView(ReservationModel model) {
        this.model = model;
        this.currentDate = LocalDate.now();  // 📅 현재 날짜로 초기화

        setTitle("강의실 예약 차단 시스템");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<<");
        JButton nextButton = new JButton(">>");
        JLabel monthLabel = new JLabel(getMonthYearText(), SwingConstants.CENTER);

        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            monthLabel.setText(getMonthYearText());
            populateCalendar();
        });

        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            monthLabel.setText(getMonthYearText());
            populateCalendar();
        });

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        populateCalendar();
        setVisible(true);
    }

    private String getMonthYearText() {
        return currentDate.getMonth().toString() + " " + currentDate.getYear();
    }

    public void populateCalendar() {
        calendarPanel.removeAll();

        // 📅 현재 월의 총 일 수 계산
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonthValue());
        int daysInMonth = yearMonth.lengthOfMonth();

        // 📅 차단된 날짜 목록 가져오기
        List<String> blockedDates = model.getBlockedDates();

        for (int day = 1; day <= daysInMonth; day++) {
            String date = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), day).toString();
            JButton dayButton = new JButton(String.valueOf(day));

            // 📅 이미 차단된 날짜 표시
            if (blockedDates.contains(date)) {
                dayButton.setBackground(Color.RED);
                dayButton.setOpaque(true);
                dayButton.setForeground(Color.WHITE);
                dayButton.setToolTipText("차단된 날짜");
            } else {
                dayButton.setBackground(null);
                dayButton.setOpaque(true);
                dayButton.setForeground(Color.BLACK);
            }

            dayButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        new ReservationController(model, date, CalendarView.this);
                    }
                }
            });

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
}