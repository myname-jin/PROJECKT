package UserNotification;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 사용자 알림 및 입실 확인 관련 데이터를 처리하는 모델 클래스
 */
public class NotificationModel {

    private static final String NOTIFICATION_FILE = "src/main/resources/notification.txt";
    private static final String CHECKIN_FILE = "src/main/resources/checkin_status.txt";
    private static final String RESERVATION_FILE = "src/main/resources/reservation.txt";
    
    private String userId;
    private List<NotificationItem> notifications = new ArrayList<>();
    
    /**
     * 알림 항목을 나타내는 내부 클래스
     */
    public static class NotificationItem {
        private String id;
        private String userId;
        private String title;
        private String content;
        private LocalDateTime createdTime;
        private LocalDateTime reservationTime;
        private String roomNumber;
        private boolean isRead;
        private boolean isCheckedIn;
        
        public NotificationItem(String id, String userId, String title, String content, 
                              LocalDateTime createdTime, LocalDateTime reservationTime, 
                              String roomNumber, boolean isRead, boolean isCheckedIn) {
            this.id = id;
            this.userId = userId;
            this.title = title;
            this.content = content;
            this.createdTime = createdTime;
            this.reservationTime = reservationTime;
            this.roomNumber = roomNumber;
            this.isRead = isRead;
            this.isCheckedIn = isCheckedIn;
        }
        
        // Getters and setters
        public String getId() { return id; }
        public String getUserId() { return userId; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public LocalDateTime getCreatedTime() { return createdTime; }
        public LocalDateTime getReservationTime() { return reservationTime; }
        public String getRoomNumber() { return roomNumber; }
        public boolean isRead() { return isRead; }
        public boolean isCheckedIn() { return isCheckedIn; }
        
        public void setRead(boolean read) { isRead = read; }
        public void setCheckedIn(boolean checkedIn) { isCheckedIn = checkedIn; }
        
        // 직렬화 메서드 (파일 저장용)
        public String serialize() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return String.join(",", 
                id,
                userId,
                title,
                content,
                createdTime.format(formatter),
                reservationTime.format(formatter),
                roomNumber,
                String.valueOf(isRead),
                String.valueOf(isCheckedIn)
            );
        }
        
        // 역직렬화 메서드 (파일 로드용)
        public static NotificationItem deserialize(String line) {
            String[] parts = line.split(",");
            if (parts.length < 9) return null;
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return new NotificationItem(
                parts[0], // id
                parts[1], // userId
                parts[2], // title
                parts[3], // content
                LocalDateTime.parse(parts[4], formatter), // createdTime
                LocalDateTime.parse(parts[5], formatter), // reservationTime
                parts[6], // roomNumber
                Boolean.parseBoolean(parts[7]), // isRead
                Boolean.parseBoolean(parts[8])  // isCheckedIn
            );
        }
    }
    
    /**
     * 생성자
     * @param userId 사용자 ID
     */
    public NotificationModel(String userId) {
        this.userId = userId;
        loadNotifications();
        createFilesIfNotExist();
    }
    
    /**
     * 파일이 존재하지 않으면 생성
     */
    private void createFilesIfNotExist() {
        try {
            File notificationFile = new File(NOTIFICATION_FILE);
            if (!notificationFile.exists()) {
                notificationFile.getParentFile().mkdirs();
                notificationFile.createNewFile();
            }
            
            File checkinFile = new File(CHECKIN_FILE);
            if (!checkinFile.exists()) {
                checkinFile.getParentFile().mkdirs();
                checkinFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("파일 생성 중 오류 발생: " + e.getMessage());
        }
    }
    
    /**
     * 사용자의 모든 알림을 로드
     */
    public void loadNotifications() {
        notifications.clear();
        File file = new File(NOTIFICATION_FILE);
        
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                NotificationItem item = NotificationItem.deserialize(line);
                if (item != null && item.getUserId().equals(userId)) {
                    notifications.add(item);
                }
            }
        } catch (IOException e) {
            System.err.println("알림 데이터 로드 중 오류 발생: " + e.getMessage());
        }
    }
    
    /**
     * 모든 알림을 파일에 저장
     */
    public void saveNotifications() {
        try {
            List<String> allLines = new ArrayList<>();
            
            // 기존 파일의 다른 사용자 알림 데이터 유지
            File file = new File(NOTIFICATION_FILE);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        NotificationItem item = NotificationItem.deserialize(line);
                        if (item != null && !item.getUserId().equals(userId)) {
                            allLines.add(line);
                        }
                    }
                }
            }
            
            // 현재 사용자 알림 데이터 추가
            for (NotificationItem item : notifications) {
                allLines.add(item.serialize());
            }
            
            // 파일에 쓰기
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line : allLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("알림 데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }
    
    /**
     * 새 알림 추가
     */
    public void addNotification(String title, String content, LocalDateTime reservationTime, String roomNumber) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        NotificationItem item = new NotificationItem(
            id, userId, title, content, LocalDateTime.now(), 
            reservationTime, roomNumber, false, false
        );
        notifications.add(item);
        saveNotifications();
    }
    
    /**
     * 알림 읽음 상태 변경
     */
    public void markAsRead(String notificationId) {
        for (NotificationItem item : notifications) {
            if (item.getId().equals(notificationId)) {
                item.setRead(true);
                saveNotifications();
                break;
            }
        }
    }
    
    /**
     * 입실 확인 처리
     */
    public void checkIn(String notificationId) {
        for (NotificationItem item : notifications) {
            if (item.getId().equals(notificationId)) {
                item.setCheckedIn(true);
                saveNotifications();
                saveCheckinStatus(item);
                break;
            }
        }
    }
    
    /**
     * 입실 확인 상태 저장
     */
    private void saveCheckinStatus(NotificationItem item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHECKIN_FILE, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String line = String.join(",", 
                item.getId(),
                item.getUserId(),
                item.getReservationTime().format(formatter),
                item.getRoomNumber(),
                LocalDateTime.now().format(formatter)
            );
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("입실 확인 저장 중 오류 발생: " + e.getMessage());
        }
    }
    
    /**
     * 예약 시간 10분 전 알림이 필요한 예약 확인
     * @return 생성된 알림 수
     */
    public int checkUpcomingReservations() {

        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 12) continue;
                System.out.println("📋 예약 라인: " + line);

                // userId가 현재 사용자와 일치하는 예약만 처리
                if (!parts[2].equals(userId)) continue;
                
                 if (!parts[2].equals(userId)) {
                System.out.println("❌ 사용자 ID 불일치: " + parts[2] + " != " + userId);
                continue;
            }
                String name = parts[0];
                String date = parts[6];  // 예약 날짜
                String day = parts[7];   // 요일
                String startTime = parts[8]; // 시작 시간
                String roomNumber = parts[5]; // 강의실
                String status = parts[11]; // 예약 상태
                
                // 승인된 예약만 처리
                if (!"승인".equals(status) && !"예약대기".equals(status)) continue;
                
                // 예약 시간 파싱
                LocalDate reservationDate = LocalDate.parse(date);
                LocalTime reservationTime = LocalTime.parse(startTime);
                LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
                
                // 현재 시간
                LocalDateTime now = LocalDateTime.now();
                
                // 10분 후 시간
                LocalDateTime tenMinutesLater = now.plusMinutes(10);
                
                // 예약이 10분 내에 시작하고, 아직 시작하지 않았으면 알림 생성
                if (reservationDateTime.isAfter(now) && 
                    reservationDateTime.isBefore(tenMinutesLater)) {
                    
                    // 같은 예약에 대한 알림이 이미 있는지 확인
                    boolean alreadyNotified = false;
                    for (NotificationItem item : notifications) {
                        if (item.getReservationTime().equals(reservationDateTime) && 
                            item.getRoomNumber().equals(roomNumber)) {
                            alreadyNotified = true;
                            break;
                        }
                    }
                    
                    // 알림이 없으면 새로 추가
                    if (!alreadyNotified) {
                        String title = "강의실 예약 시작 10분 전";
                        String content = roomNumber + " 강의실 예약이 " + startTime + "에 시작됩니다.";
                        addNotification(title, content, reservationDateTime, roomNumber);
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("예약 데이터 읽기 중 오류 발생: " + e.getMessage());
        }
        
        return count;
    }
    
    /**
     * 입실 확인이 필요한 알림 목록 반환
     */
    public List<NotificationItem> getPendingCheckins() {
        List<NotificationItem> pendingList = new ArrayList<>();
        
        LocalDateTime now = LocalDateTime.now();
        List<NotificationItem> notificationsCopy = new ArrayList<>(notifications);

        for (NotificationItem item : notifications) {
            // 아직 체크인 안했고, 예약 시간이 현재보다 이전이고, 예약 시간 + 10분이 현재보다 이후인 항목
            if (!item.isCheckedIn() && 
                item.getReservationTime().isBefore(now) && 
                item.getReservationTime().plusMinutes(10).isAfter(now)) {
                pendingList.add(item);
            }
        }
        
        return pendingList;
    }
    
    private static final String CANCEL_FILE = "src/main/resources/cancel.txt";
    
    /**
     * 자동 취소가 필요한 예약 처리
     * @return 취소된 예약 수
     */
    // NotificationModel.java의 processMissedCheckins() 메서드 수정 부분

/**
 * ✅ 수정된 자동 취소가 필요한 예약 처리
 */
public int processMissedCheckins() {
    int count = 0;
    LocalDateTime now = LocalDateTime.now();
    
    // 체크인 하지 않은 예약 중 시작 시간으로부터 10분 이상 지난 예약 찾기
    List<NotificationItem> toCancel = new ArrayList<>();
    for (NotificationItem item : notifications) {
        if (!item.isCheckedIn() && 
            item.getReservationTime().plusMinutes(10).isBefore(now) &&
            item.getTitle().contains("10분 전")) { // ✅ 10분 전 알림만 대상으로 함
            toCancel.add(item);
        }
    }
    
    // 취소 처리
    if (!toCancel.isEmpty()) {
        List<String> reservations = new ArrayList<>();
        List<String> reservationsToRemove = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reservations.add(line);
            }
        } catch (IOException e) {
            System.err.println("예약 파일 읽기 중 오류 발생: " + e.getMessage());
            return 0;
        }
        
        // 취소할 예약 식별 및 처리
        for (NotificationItem item : toCancel) {
            // ✅ 이미 취소 알림이 있는지 확인
            boolean cancelNotificationExists = false;
            for (NotificationItem existingItem : notifications) {
                if (existingItem.getTitle().equals("예약 자동 취소") &&
                    existingItem.getRoomNumber().equals(item.getRoomNumber()) &&
                    existingItem.getReservationTime().equals(item.getReservationTime())) {
                    cancelNotificationExists = true;
                    break;
                }
            }
            
            // ✅ 취소 알림이 이미 있으면 스킵
            if (cancelNotificationExists) {
                continue;
            }
            
            for (String line : reservations) {
                String[] parts = line.split(",");
                if (parts.length < 12) continue;
                
                String reservationUserId = parts[2];
                String date = parts[6];
                String startTime = parts[8];
                String roomNumber = parts[5];
                
                try {
                    LocalDate reservationDate = LocalDate.parse(date);
                    LocalTime reservationTime = LocalTime.parse(startTime);
                    LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
                    
                    if (reservationUserId.equals(item.getUserId()) && 
                        reservationDateTime.equals(item.getReservationTime()) && 
                        roomNumber.equals(item.getRoomNumber())) {
                        
                        // 취소할 예약 목록에 추가
                        reservationsToRemove.add(line);
                        
                        // 취소 사유 파일에 자동 취소 기록 추가
                        try (BufferedWriter cancelWriter = new BufferedWriter(new FileWriter(CANCEL_FILE, true))) {
                            File cancelFileObj = new File(CANCEL_FILE);
                            boolean needNewLine = cancelFileObj.exists() && cancelFileObj.length() > 0;
                            
                            if (needNewLine) {
                                cancelWriter.newLine();
                            }
                            
                            cancelWriter.write(reservationUserId + ", 입실 확인 시간 초과로 인한 자동 취소");
                        } catch (IOException e) {
                            System.err.println("취소 사유 파일 쓰기 중 오류 발생: " + e.getMessage());
                        }
                        
                        // ✅ 취소 알림 추가 (한 번만)
                        String title = "예약 자동 취소";
                        String content = item.getRoomNumber() + " 강의실 예약이 입실 확인 시간 초과로 자동 취소되었습니다.";
                        addNotification(title, content, item.getReservationTime(), item.getRoomNumber());
                        
                        count++;
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("날짜/시간 파싱 중 오류 발생: " + e.getMessage());
                }
            }
        }
        
        // 예약 파일에서 취소된 예약 제거
        if (!reservationsToRemove.isEmpty()) {
            reservations.removeAll(reservationsToRemove);
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATION_FILE))) {
                boolean first = true;
                for (String line : reservations) {
                    if (!first) {
                        writer.newLine();
                    }
                    writer.write(line);
                    first = false;
                }
            } catch (IOException e) {
                System.err.println("예약 파일 쓰기 중 오류 발생: " + e.getMessage());
                return 0;
            }
        }
    }
    
    return count;
}
    
    /**
     * 읽지 않은 알림 개수 반환
     */
    public int getUnreadCount() {
        int count = 0;
        for (NotificationItem item : notifications) {
            if (!item.isRead()) count++;
        }
        return count;
    }
    
    /**
     * 모든 알림 목록 반환
     */
    public List<NotificationItem> getAllNotifications() {
        // 생성 시간 기준 내림차순 정렬 (최신순)
        List<NotificationItem> sorted = new ArrayList<>(notifications);
        sorted.sort((a, b) -> b.getCreatedTime().compareTo(a.getCreatedTime()));
        return sorted;
    }
}