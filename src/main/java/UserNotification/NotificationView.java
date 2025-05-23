/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserNotification;

/**
 *
 * @author jms5310
 */
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * 알림 목록 및 상세 내용을 표시하는 뷰 클래스
 */
public class NotificationView extends JFrame {

    private NotificationController controller;
    private JList<NotificationModel.NotificationItem> notificationList;
    private DefaultListModel<NotificationModel.NotificationItem> listModel;
    private JTextPane detailPane;
    private JButton checkinButton;
    private JLabel titleLabel;

    /**
     * 생성자
     * @param controller 컨트롤러 객체
     */
    public NotificationView(NotificationController controller) {
        this.controller = controller;
        initializeUI();
    }

    /**
     * UI 초기화
     */
    private void initializeUI() {
        setTitle("알림 센터");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ✅ X 버튼 클릭 시 로그아웃 처리
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                handleLogout();
            }
        });

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // ✅ 상단 패널 (제목 + 뒤로가기 버튼)
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 제목
        titleLabel = new JLabel("알림 센터");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        // ✅ 뒤로가기 버튼
        JButton backButton = new JButton("뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        backButton.addActionListener(e -> handleBackButton());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 목록과 상세 화면을 위한 분할 패널
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // 알림 목록
        listModel = new DefaultListModel<>();
        notificationList = new JList<>(listModel);
        notificationList.setCellRenderer(new NotificationCellRenderer());
        notificationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 알림 선택 시 상세 내용 표시
        notificationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                NotificationModel.NotificationItem selectedItem = notificationList.getSelectedValue();
                if (selectedItem != null) {
                    updateDetailView(selectedItem);
                    
                    // 읽음 표시
                    if (!selectedItem.isRead()) {
                        controller.markAsRead(selectedItem.getId());
                    }
                }
            }
        });
        
        JScrollPane listScrollPane = new JScrollPane(notificationList);
        splitPane.setLeftComponent(listScrollPane);

        // 상세 내용 패널
        JPanel detailPanel = new JPanel(new BorderLayout(5, 5));
        
        detailPane = new JTextPane();
        detailPane.setContentType("text/html");
        detailPane.setEditable(false);
        
        JScrollPane detailScrollPane = new JScrollPane(detailPane);
        detailPanel.add(detailScrollPane, BorderLayout.CENTER);
        
        // 입실 확인 버튼
        checkinButton = new JButton("입실 확인");
        checkinButton.setEnabled(false);
        checkinButton.addActionListener(e -> {
            NotificationModel.NotificationItem selectedItem = notificationList.getSelectedValue();
            if (selectedItem != null) {
                controller.checkIn(selectedItem.getId());
            }
        });
        
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomButtonPanel.add(checkinButton);
        detailPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        
        splitPane.setRightComponent(detailPanel);

        setContentPane(mainPanel);
    }
    
    /**
     * ✅ 뒤로가기 버튼 처리
     */
    private void handleBackButton() {
        dispose();
        // UserMainController로 돌아가기
        new UserFunction.UserMainController(
            controller.getUserId(), 
            controller.getSocket(), 
            controller.getIn(), 
            controller.getOut()
        );
    }
    
    /**
     * ✅ 로그아웃 처리 (X 버튼 클릭 시) - 서버에만 알리고 창 닫기
     */
    private void handleLogout() {
        try {
            if (controller.getSocket() != null && controller.getOut() != null) {
                controller.getOut().write("LOGOUT:" + controller.getUserId() + "\n");
                controller.getOut().flush();
                controller.getSocket().close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public NotificationController getController() {
        return this.controller;
    }
    
    /**
     * 알림 목록 업데이트
     */
    public void updateNotificationList(List<NotificationModel.NotificationItem> notifications) {
        SwingUtilities.invokeLater(() -> {
            listModel.clear();
            for (NotificationModel.NotificationItem item : notifications) {
                listModel.addElement(item);
            }
            
            // 이전에 선택된 알림이 있으면 다시 선택
            if (notificationList.getSelectedIndex() == -1 && listModel.size() > 0) {
                notificationList.setSelectedIndex(0);
            }
        });
    }

    /**
     * 읽지 않은 알림 수 업데이트
     */
    public void updateUnreadCount(int count) {
        SwingUtilities.invokeLater(() -> {
            if (count > 0) {
                titleLabel.setText("알림 센터 (읽지 않은 알림: " + count + ")");
            } else {
                titleLabel.setText("알림 센터");
            }
        });
    }

    /**
     * 상세 내용 표시 업데이트
     */
    private void updateDetailView(NotificationModel.NotificationItem item) {
        // HTML 형식의 상세 내용 설정
        detailPane.setText(controller.formatNotificationContent(item));
        
        // 입실 확인 버튼 활성화 여부 설정
        boolean canCheckin = isCheckinAvailable(item);
        checkinButton.setEnabled(canCheckin);
        
        // 이미 입실 확인했으면 버튼 텍스트 변경
        if (item.isCheckedIn()) {
            checkinButton.setText("입실 확인 완료");
            checkinButton.setEnabled(false);
        } else {
            checkinButton.setText("입실 확인");
        }
    }
    
    /**
     * 입실 확인 가능 여부 검사
     */
    private boolean isCheckinAvailable(NotificationModel.NotificationItem item) {
        if (item.isCheckedIn()) return false;
        
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.LocalDateTime reservationTime = item.getReservationTime();
        
        // 예약 시간부터 10분 이내만 체크인 가능
        return now.isAfter(reservationTime) && 
               now.isBefore(reservationTime.plusMinutes(10));
    }

    /**
     * 알림 항목 셀 렌더러
     */
    private class NotificationCellRenderer extends DefaultListCellRenderer {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof NotificationModel.NotificationItem) {
                NotificationModel.NotificationItem item = (NotificationModel.NotificationItem) value;
                
                // 제목과 시간 표시
                String displayText = item.getTitle() + " (" + item.getCreatedTime().format(formatter) + ")";
                label.setText(displayText);
                
                // 읽지 않은 알림은 굵게 표시
                if (!item.isRead()) {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                } else {
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                }
                
                // 입실 확인이 필요한 알림은 아이콘 추가
                if (isCheckinAvailable(item)) {
                    label.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
                } else if (item.isCheckedIn()) {
                    label.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
                } else {
                    label.setIcon(null);
                }
            }
            
            return label;
        }
    }
}