/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

/**
 *
 * @author adsd3
 */
// 월간 달력 UI 생성 및 차단된 날짜 표시
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CalendarView extends JFrame {
    public interface DayDoubleClickHandler {
        void onDoubleClick(LocalDate date);
    }

    private DayDoubleClickHandler handler;
    private final JPanel calendarPanel = new JPanel(new GridLayout(6, 7));
    private YearMonth current = YearMonth.now();

    public CalendarView() {
        setTitle("강의실 예약 차단 시스템");
        setSize(800, 600);
        setLocationRelativeTo(null);      // 화면 중앙
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 헤더
        JPanel header = new JPanel(new BorderLayout());
        JButton prev = new JButton("<<");
        JButton next = new JButton(">>");
        JLabel monthLabel = new JLabel(getMonthLabel(), SwingConstants.CENTER);
        prev.addActionListener(e -> {
            current = current.minusMonths(1);
            monthLabel.setText(getMonthLabel());
            refresh();
        });
        next.addActionListener(e -> {
            current = current.plusMonths(1);
            monthLabel.setText(getMonthLabel());
            refresh();
        });
        header.add(prev, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(next, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        refresh();
        setVisible(true);
    }

    public void setDayDoubleClickHandler(DayDoubleClickHandler h) {
        this.handler = h;
    }

    private String getMonthLabel() {
        return current.getMonth() + " " + current.getYear();
    }

    public void refresh() {
        calendarPanel.removeAll();
        int daysInMonth = current.lengthOfMonth();
        List<String> blocked = ReservationServiceModel.staticGetBlockedDates();
        for (int d = 1; d <= daysInMonth; d++) {
            LocalDate date = current.atDay(d);
            JButton btn = new JButton(String.valueOf(d));
            if (blocked.contains(date.toString())) {
                btn.setBackground(Color.RED);
                btn.setOpaque(true);
                btn.setForeground(Color.WHITE);
                btn.setToolTipText("차단된 날짜");
            }
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && handler != null) {
                        handler.onDoubleClick(date);
                    }
                }
            });
            calendarPanel.add(btn);
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
}