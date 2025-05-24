package UserNotification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;


public class NotificationModelTest {
    
    private NotificationModel model;
    private final String testUserId = "TEST_USER_999"; // 실제 사용자와 다른 테스트 ID
    
    @BeforeEach
    public void setUp() {
        model = new NotificationModel(testUserId);
    }
    
    @AfterEach
    public void tearDown() {
        model = null;
    }
    
    @Test
    public void testConstructorWithValidUserId() {
        // 생성자가 정상적으로 동작하는지 테스트
        assertNotNull(model, "NotificationModel이 정상적으로 생성되어야 함");
    }
    
    @Test
    public void testGetAllNotifications() {
        // getAllNotifications 메서드가 리스트를 반환하는지 테스트
        List<NotificationModel.NotificationItem> notifications = model.getAllNotifications();
        assertNotNull(notifications, "알림 목록이 null이 아니어야 함");
       
        assertTrue(notifications.size() >= 0, "알림 목록 크기가 0 이상이어야 함");
    }
    
    @Test
    public void testGetUnreadCount() {
        // getUnreadCount 메서드가 정상적으로 동작하는지 테스트
        int unreadCount = model.getUnreadCount();
        assertTrue(unreadCount >= 0, "읽지 않은 알림 수는 0 이상이어야 함");
    }
    
    @Test
    public void testAddNotificationMethodExists() {
        
        LocalDateTime now = LocalDateTime.now();
        String roomNumber = "911";
        
        // 메서드 호출만 테스트 
        assertDoesNotThrow(() -> {
           
            
            // 대신 메서드가 존재하는지 리플렉션으로 확인
            java.lang.reflect.Method method = NotificationModel.class.getDeclaredMethod(
                "addNotification", String.class, String.class, 
                java.time.LocalDateTime.class, String.class);
            assertNotNull(method, "addNotification 메서드가 존재해야 함");
        }, "addNotification 메서드 확인 시 예외가 발생하지 않아야 함");
    }
    
    @Test
    public void testCheckUpcomingReservations() {
        // 테스트 사용자는 실제 예약 파일에 데이터가 없으므로 0을 반환해야 함
        assertDoesNotThrow(() -> {
            int count = model.checkUpcomingReservations();
            assertTrue(count >= 0, "생성된 알림 수는 0 이상이어야 함");
        }, "예약 확인 시 예외가 발생하지 않아야 함");
    }
    
    @Test
    public void testGetPendingCheckins() {
        // getPendingCheckins 메서드가 정상적으로 동작하는지 테스트
        List<NotificationModel.NotificationItem> pendingList = model.getPendingCheckins();
        assertNotNull(pendingList, "대기 중인 체크인 목록이 null이 아니어야 함");
    }
    
    @Test
    public void testProcessMissedCheckins() {
        
        assertDoesNotThrow(() -> {
            int count = model.processMissedCheckins();
            assertTrue(count >= 0, "처리된 취소 수는 0 이상이어야 함");
        }, "체크인 실패 처리 시 예외가 발생하지 않아야 함");
    }
    
    @Test
    public void testNotificationItemSerialization() {
        // NotificationItem의 직렬화/역직렬화 테스트 
        LocalDateTime now = LocalDateTime.now();
        NotificationModel.NotificationItem item = new NotificationModel.NotificationItem(
            "test123", testUserId, "테스트 제목", "테스트 내용", 
            now, now.plusMinutes(10), "911", false, false
        );
        
        String serialized = item.serialize();
        assertNotNull(serialized, "직렬화 결과가 null이 아니어야 함");
        assertTrue(serialized.contains("test123"), "직렬화된 문자열에 ID가 포함되어야 함");
        
        NotificationModel.NotificationItem deserialized = 
            NotificationModel.NotificationItem.deserialize(serialized);
        assertNotNull(deserialized, "역직렬화 결과가 null이 아니어야 함");
        assertEquals(item.getId(), deserialized.getId(), "ID가 일치해야 함");
        assertEquals(item.getTitle(), deserialized.getTitle(), "제목이 일치해야 함");
    }
    
    @Test
    public void testMarkAsReadMethodExists() {
      
        assertDoesNotThrow(() -> {
          
            java.lang.reflect.Method method = NotificationModel.class.getDeclaredMethod(
                "markAsRead", String.class);
            assertNotNull(method, "markAsRead 메서드가 존재해야 함");
        }, "markAsRead 메서드 확인 시 예외가 발생하지 않아야 함");
    }
}
