package Reservation;

import org.jdatepicker.impl.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationView extends JFrame {
    private JLabel nameLabel, idLabel, deptLabel;
    private JComboBox<String> roomComboBox;
    private JPanel timeSlotPanel;
    private JDatePickerImpl datePicker;
    private JTextField selectedTimeField;
    private JButton[] purposeButtons;
    private JButton reserveButton;
    private String selectedPurpose = "";

    public ReservationView() {
        setTitle("강의실 예약 시스템");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        nameLabel = new JLabel("이름: ");
        idLabel = new JLabel("학번: ");
        deptLabel = new JLabel("학과: ");
        infoPanel.add(nameLabel);
        infoPanel.add(idLabel);
        infoPanel.add(deptLabel);
        add(infoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // 강의실 선택
        JPanel roomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roomPanel.add(new JLabel("강의실 선택:"));
        roomComboBox = new JComboBox<>();
        roomPanel.add(roomComboBox);
        centerPanel.add(roomPanel);

        // 날짜 선택
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "오늘");
        p.put("text.month", "월");
        p.put("text.year", "년");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JPanel datePanelUI = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanelUI.add(new JLabel("예약 날짜 선택:"));
        datePanelUI.add(datePicker);
        centerPanel.add(datePanelUI);

        // 시간대 영역
        centerPanel.add(new JLabel("예약 가능한 시간대 목록:"));
        timeSlotPanel = new JPanel();
        timeSlotPanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(timeSlotPanel);
        scrollPane.setPreferredSize(new Dimension(600, 120));
        centerPanel.add(scrollPane);

        // 선택된 시간
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(new JLabel("선택한 시간:"));
        selectedTimeField = new JTextField(20);
        timePanel.add(selectedTimeField);
        centerPanel.add(timePanel);

        // 목적 선택
        centerPanel.add(new JLabel("예약 목적 선택:"));
        JPanel purposePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] purposes = {"수업", "시험", "스터디", "세미나", "기타"};
        purposeButtons = new JButton[purposes.length];
        for (int i = 0; i < purposes.length; i++) {
            final String purpose = purposes[i];
            purposeButtons[i] = new JButton(purpose);
            purposeButtons[i].addActionListener(e -> selectedPurpose = purpose);
            purposePanel.add(purposeButtons[i]);
        }
        centerPanel.add(purposePanel);

        add(centerPanel, BorderLayout.CENTER);

        reserveButton = new JButton("예약하기");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(reserveButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 사용자 정보 표시
    public void setUserInfo(String name, String id, String dept) {
        nameLabel.setText("이름: " + name);
        idLabel.setText("학번: " + id);
        deptLabel.setText("학과: " + dept);
    }

    // 강의실 콤보박스 채우기

    /**
     *
      @param roomNames 강의실 이름 리스트
     */
    public void setRoomList(java.util.List<String> roomNames) {
        roomComboBox.removeAllItems();
        for (String name : roomNames) {
            roomComboBox.addItem(name);
        }
    }

    public String getSelectedRoom() {
        return (String) roomComboBox.getSelectedItem();
    }

    public void addRoomSelectionListener(ActionListener listener) {
        roomComboBox.addActionListener(listener);
    }

    public String getSelectedDate() {
        return datePicker.getJFormattedTextField().getText().trim();
    }

    public void addDateSelectionListener(ActionListener listener) {
        datePicker.addActionListener(listener);
    }

    public void addTimeSlot(String time, ActionListener listener) {
        JButton timeButton = new JButton(time);
        timeButton.addActionListener(listener);
        timeSlotPanel.add(timeButton);
        timeSlotPanel.revalidate();
        timeSlotPanel.repaint();
    }

    public void clearTimeSlots() {
        timeSlotPanel.removeAll();
        timeSlotPanel.revalidate();
        timeSlotPanel.repaint();
    }

    public String getSelectedTime() {
        return selectedTimeField.getText().trim();
    }

    public void setSelectedTime(String time) {
        selectedTimeField.setText(time);
    }

    public String getSelectedPurpose() {
        return selectedPurpose;
    }

    public void addReserveButtonListener(ActionListener listener) {
        reserveButton.addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
