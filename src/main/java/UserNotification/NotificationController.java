package UserNotification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * ✅ 싱글톤 패턴으로 수정된 NotificationController
 * 페이지 이동 시에도 알림 상태가 유지됩니다
 */
public class NotificationController {

    // ✅ 싱글톤 인스턴스 저장 (userId별로)
    private static Map<String, NotificationController> instances = new HashMap<>();
    
    private NotificationModel model;
    private NotificationView view;
    private Timer notificationTimer;
    private Timer checkinTimer;
    private String userId;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private boolean isTimerRunning = false;
    
    // ✅ 중복 방지 로직 (페이지 이동해도 유지됨)
    private Set<String> processedReservations = new HashSet<>();
    private Set<String> shownAlerts = new HashSet<>();
    private Set<String> shownDialogs = new HashSet<>();

    /**
     * ✅ private 생성자 (싱글톤 패턴)
     */
    private NotificationController(String userId, Socket socket, BufferedReader in, BufferedWriter out) {
        this.userId = userId;
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.model = new NotificationModel(userId);
        
        checkCurrentSituation();
        initializeTimers();
        loadNotifications();
    }
    
    /**
     * ✅ 싱글톤 인스턴스 반환 (userId별로 관리)
     */
    public static NotificationController getInstance(String userId, Socket socket, BufferedReader in, BufferedWriter out) {
        NotificationController instance = instances.get(userId);
        
        if (instance == null) {
            // 새로운 인스턴스 생성
            instance = new NotificationController(userId, socket, in, out);
            instances.put(userId, instance);
            System.out.println("✅ 새로운 NotificationController 생성: " + userId);
        } else {
            // 기존 인스턴스의 연결 정보 업데이트 (필요시)
            instance.updateConnection(socket, in, out);
        }
        
        return instance;
    }
    
    /**
     * ✅ 연결 정보 업데이트 (소켓 재연결 등)
     */
    private void updateConnection(Socket socket, BufferedReader in, BufferedWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
    
    /**
     * ✅ 사용자 로그아웃 시 인스턴스 정리
     */
    public static void removeInstance(String userId) {
        NotificationController instance = instances.get(userId);
        if (instance != null) {
            instance.shutdown();
            instances.remove(userId);
            System.out.println("✅ NotificationController 인스턴스 제거: " + userId);
        }
    }
    
    /**
     * ✅ 접속 시 현재 상황 즉시 체크 (이미 체크한 것은 스킵)
     */
    private void checkCurrentSituation() {
        LocalDateTime now = LocalDateTime.now();
        
        model.loadNotifications();
        List<NotificationModel.NotificationItem> allNotifications = model.getAllNotifications();
        
        for (NotificationModel.NotificationItem item : allNotifications) {
            if (!item.getTitle().contains("10분 전")) {
                continue;
            }
            
            LocalDateTime reservationTime = item.getReservationTime();
            String reservationKey = generateReservationKey(item);
            
            // ✅ 이미 처리된 예약은 완전히 스킵
            if (processedReservations.contains(reservationKey)) {
                continue;
            }
            
            if (Math.abs(java.time.Duration.between(now, reservationTime).toMinutes()) <= 10) {
                
                if (!shownAlerts.contains(reservationKey)) {
                    
                    SwingUtilities.invokeLater(() -> {
                        String message;
                        if (reservationTime.isAfter(now)) {
                            long minutesLeft = java.time.Duration.between(now, reservationTime).toMinutes();
                            message = item.getRoomNumber() + " 강의실 예약이 " + 
                                     minutesLeft + "분 후에 시작됩니다.\n알림 버튼을 눌러 확인하세요.";
                        } else {
                            message = item.getRoomNumber() + " 강의실 예약이 진행 중입니다.\n" +
                                     "입실 확인이 필요합니다.";
                        }
                        
                        JOptionPane.showMessageDialog(
                            null, 
                            message, 
                            "예약 알림", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    });
                    
                    shownAlerts.add(reservationKey);
                    System.out.println("✅ 접속 시 예약 알림 표시: " + reservationKey);
                }
            }
        }
        
        // 입실 확인 다이얼로그 체크
        List<NotificationModel.NotificationItem> pendingCheckins = model.getPendingCheckins();
        for (NotificationModel.NotificationItem item : pendingCheckins) {
            String reservationKey = generateReservationKey(item);
            
            if (!shownDialogs.contains(reservationKey) && 
                !processedReservations.contains(reservationKey)) {
                
                SwingUtilities.invokeLater(() -> {
                    if (view == null) {
                        view = new NotificationView(this);
                    }
                    
                    CheckinDialog dialog = new CheckinDialog(view, item);
                    dialog.setVisible(true);
                });
                
                shownDialogs.add(reservationKey);
                System.out.println("✅ 접속 시 입실 확인 다이얼로그 표시: " + reservationKey);
            }
        }
        
        checkAndProcessExpiredReservations();
    }
    
    private void checkAndProcessExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<NotificationModel.NotificationItem> allNotifications = model.getAllNotifications();
        
        for (NotificationModel.NotificationItem item : allNotifications) {
            if (item.getTitle().contains("10분 전") && 
                !item.isCheckedIn() && 
                item.getReservationTime().plusMinutes(10).isBefore(now)) {
                
                String reservationKey = generateReservationKey(item);
                
                if (!processedReservations.contains(reservationKey)) {
                    System.out.println("✅ 접속 시 만료된 예약 발견 - 취소 처리: " + reservationKey);
                    
                    int cancelCount = model.processMissedCheckins();
                    
                    if (cancelCount > 0) {
                        SwingUtilities.invokeLater(() -> {
                            loadNotifications();
                            
                            JOptionPane.showMessageDialog(
                                null,
                                item.getRoomNumber() + " 강의실 예약이 입실 확인 시간 초과로 자동 취소되었습니다.",
                                "예약 자동 취소",
                                JOptionPane.WARNING_MESSAGE
                            );
                        });
                    }
                    
                    processedReservations.add(reservationKey);
                    break;
                }
            }
        }
    }
    
    private void initializeTimers() {
        if (isTimerRunning) return; // 이미 실행 중이면 스킵
        
        stopTimers();
        
        notificationTimer = new Timer();
        notificationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkReservations();
            }
        }, 0, 60 * 1000);
        
        checkinTimer = new Timer();
        checkinTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                processMissedCheckins();
            }
        }, 0, 5 * 60 * 1000);
        
        isTimerRunning = true;
    }
    
    public void stopTimers() {
        if (notificationTimer != null) {
            notificationTimer.cancel();
            notificationTimer = null;
        }
        
        if (checkinTimer != null) {
            checkinTimer.cancel();
            checkinTimer = null;
        }
        
        isTimerRunning = false;
    }
    
    public void showNotificationView() {
        if (view != null) {
            loadNotifications();
            view.setVisible(true);
        } else {
            view = new NotificationView(this);
            loadNotifications();
            view.setVisible(true);
        }
    }
    
    public void loadNotifications() {
        model.loadNotifications();
        if (view != null) {
            view.updateNotificationList(model.getAllNotifications());
            view.updateUnreadCount(model.getUnreadCount());
        }
    }
    
    private void checkReservations() {
        int newNotificationCount = model.checkUpcomingReservations();
        checkAndShowReservationAlerts();
        checkAndShowCheckinDialogs();
        
        if (newNotificationCount > 0) {
            SwingUtilities.invokeLater(() -> loadNotifications());
        }
    }
    
    private void checkAndShowReservationAlerts() {
        List<NotificationModel.NotificationItem> allNotifications = model.getAllNotifications();
        LocalDateTime now = LocalDateTime.now();
        
        for (NotificationModel.NotificationItem item : allNotifications) {
            if (!item.getTitle().contains("10분 전")) {
                continue;
            }
            
            LocalDateTime reservationTime = item.getReservationTime();
            String reservationKey = generateReservationKey(item);
            
            if (processedReservations.contains(reservationKey)) {
                continue;
            }
            
            if (reservationTime.isAfter(now) && 
                reservationTime.isBefore(now.plusMinutes(10))) {
                
                if (shownAlerts.contains(reservationKey)) {
                    continue;
                }
                
                SwingUtilities.invokeLater(() -> {
                    String message = item.getRoomNumber() + " 강의실 예약이 " + 
                                   reservationTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
                                   "에 시작됩니다.\n알림 버튼을 눌러 확인하세요.";
                    
                    JOptionPane.showMessageDialog(
                        null, 
                        message, 
                        "예약 알림", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                });
                
                shownAlerts.add(reservationKey);
                System.out.println("✅ 10분 전 알림 표시: " + reservationKey);
            }
        }
    }
    
    private void checkAndShowCheckinDialogs() {
        List<NotificationModel.NotificationItem> pendingCheckins = model.getPendingCheckins();
        
        for (NotificationModel.NotificationItem item : pendingCheckins) {
            String reservationKey = generateReservationKey(item);
            
            if (processedReservations.contains(reservationKey)) {
                continue;
            }
            
            if (shownDialogs.contains(reservationKey)) {
                continue;
            }
            
            SwingUtilities.invokeLater(() -> {
                if (view == null) {
                    view = new NotificationView(this);
                }
                
                CheckinDialog dialog = new CheckinDialog(view, item);
                dialog.setVisible(true);
            });
            
            shownDialogs.add(reservationKey);
            System.out.println("✅ 입실 확인 다이얼로그 표시: " + reservationKey);
        }
    }
    
    private String generateReservationKey(NotificationModel.NotificationItem item) {
        return userId + "_" + item.getRoomNumber() + "_" + 
               item.getReservationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
    }
    
    private void processMissedCheckins() {
        int count = model.processMissedCheckins();
        
        if (count > 0) {
            SwingUtilities.invokeLater(() -> {
                loadNotifications();
            });
        }
    }
    
    public void markAsRead(String notificationId) {
        model.markAsRead(notificationId);
        loadNotifications();
    }
    
    public void checkIn(String notificationId) {
        model.checkIn(notificationId);
        loadNotifications();
        
        NotificationModel.NotificationItem item = findNotificationById(notificationId);
        if (item != null) {
            String reservationKey = generateReservationKey(item);
            
            shownAlerts.remove(reservationKey);
            shownDialogs.remove(reservationKey);
            processedReservations.add(reservationKey);
            
            System.out.println("✅ 입실 확인 완료 - 예약 처리 완료: " + reservationKey);
        }
        
        JOptionPane.showMessageDialog(
            view, 
            "입실 확인이 완료되었습니다.", 
            "입실 확인", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private NotificationModel.NotificationItem findNotificationById(String notificationId) {
        for (NotificationModel.NotificationItem item : model.getAllNotifications()) {
            if (item.getId().equals(notificationId)) {
                return item;
            }
        }
        return null;
    }
    
    public String getNotificationButtonText() {
        int unreadCount = model.getUnreadCount();
        if (unreadCount > 0) {
            return "알림(" + unreadCount + ")";
        } else {
            return "알림";
        }
    }
    
    public String formatNotificationContent(NotificationModel.NotificationItem item) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        StringBuilder sb = new StringBuilder();
        sb.append("<html><b>").append(item.getTitle()).append("</b><br>");
        sb.append(item.getContent()).append("<br>");
        sb.append("강의실: ").append(item.getRoomNumber()).append("<br>");
        sb.append("일시: ").append(item.getReservationTime().format(dateFormatter))
          .append(" ").append(item.getReservationTime().format(timeFormatter));
        
        if (item.isCheckedIn()) {
            sb.append("<br><font color='green'>✓ 입실 확인 완료</font>");
        } else if (LocalDateTime.now().isAfter(item.getReservationTime()) && 
                  LocalDateTime.now().isBefore(item.getReservationTime().plusMinutes(10))) {
            sb.append("<br><font color='red'>! 입실 확인 필요</font>");
        }
        
        sb.append("</html>");
        return sb.toString();
    }
    
    public void closeView() {
        if (view != null) {
            view.dispose();
            view = null;
        }
    }
    
    public void shutdown() {
        stopTimers();
        closeView();
        
        shownAlerts.clear();
        shownDialogs.clear();
        processedReservations.clear();
        
        System.out.println("✅ NotificationController 정리 완료");
    }
    
    // Getter 메서드들
    public NotificationModel getModel() {
        return model;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public BufferedReader getIn() {
        return in;
    }
    
    public BufferedWriter getOut() {
        return out;
    }
}