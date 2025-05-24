package UserNotification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;


public class NotificationControllerTest {
    
    private NotificationController controller;
    private Socket mockSocket;
    private BufferedReader mockIn;
    private BufferedWriter mockOut;
    private final String testUserId = "TEST_CONTROLLER_999"; // 실제 사용자와 다른 ID
    
    @BeforeEach
    public void setUp() {
        // Mock 객체 생성
        mockSocket = mock(Socket.class);
        mockIn = mock(BufferedReader.class);
        mockOut = mock(BufferedWriter.class);
    }
    
    @AfterEach
    public void tearDown() {
        // 테스트 후 인스턴스 정리
        if (controller != null) {
            controller.shutdown();
            NotificationController.removeInstance(testUserId);
        }
    }
    
    @Test
    public void testGetInstance() {
        // 싱글톤 인스턴스 생성 테스트
        assertDoesNotThrow(() -> {
            controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        }, "NotificationController 인스턴스 생성 시 예외가 발생하지 않아야 함");
        
        assertNotNull(controller, "NotificationController 인스턴스가 생성되어야 함");
    }
    
    @Test
    public void testSingletonPattern() {
        // 싱글톤 패턴이 제대로 동작하는지 테스트
        NotificationController instance1 = NotificationController.getInstance(
            testUserId, mockSocket, mockIn, mockOut);
        NotificationController instance2 = NotificationController.getInstance(
            testUserId, mockSocket, mockIn, mockOut);
        
        assertSame(instance1, instance2, "같은 userId로 호출 시 동일한 인스턴스를 반환해야 함");
        
        // 정리
        instance1.shutdown();
        NotificationController.removeInstance(testUserId);
    }
    
    @Test
    public void testGetModel() {
        // getModel 메서드 테스트
        controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        
        NotificationModel model = controller.getModel();
        assertNotNull(model, "NotificationModel이 null이 아니어야 함");
    }
    
    @Test
    public void testGetNotificationButtonText() {
        // getNotificationButtonText 메서드 테스트
        controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        
        String buttonText = controller.getNotificationButtonText();
        assertNotNull(buttonText, "알림 버튼 텍스트가 null이 아니어야 함");
        assertTrue(buttonText.contains("알림"), "버튼 텍스트에 '알림'이 포함되어야 함");
    }
    
    @Test
    public void testLoadNotifications() {
        // loadNotifications 메서드가 예외 없이 실행되는지 테스트
        controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        
        assertDoesNotThrow(() -> {
            controller.loadNotifications();
        }, "알림 로드 시 예외가 발생하지 않아야 함");
    }
    
    @Test
    public void testGetUserInfo() {
        // 사용자 정보 getter 메서드들 테스트
        controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        
        assertEquals(testUserId, controller.getUserId(), "사용자 ID가 일치해야 함");
        assertSame(mockSocket, controller.getSocket(), "Socket이 일치해야 함");
        assertSame(mockIn, controller.getIn(), "BufferedReader가 일치해야 함");
        assertSame(mockOut, controller.getOut(), "BufferedWriter가 일치해야 함");
    }
    
    @Test
    public void testShutdown() {
        // shutdown 메서드가 예외 없이 실행되는지 테스트
        controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        
        assertDoesNotThrow(() -> {
            controller.shutdown();
        }, "shutdown 실행 시 예외가 발생하지 않아야 함");
    }
    
    @Test
    public void testRemoveInstance() {
        // removeInstance 메서드 테스트
        controller = NotificationController.getInstance(testUserId, mockSocket, mockIn, mockOut);
        
        assertDoesNotThrow(() -> {
            NotificationController.removeInstance(testUserId);
        }, "인스턴스 제거 시 예외가 발생하지 않아야 함");
        
        // 제거 후 새로운 인스턴스가 생성되는지 확인
        NotificationController newController = NotificationController.getInstance(
            testUserId, mockSocket, mockIn, mockOut);
        assertNotNull(newController, "제거 후 새로운 인스턴스가 생성되어야 함");
        
        // 정리
        newController.shutdown();
        NotificationController.removeInstance(testUserId);
    }
}