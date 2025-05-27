//package UserNotification;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.net.Socket;
//
///**
// * NotificationButton 테스트 클래스
// */
//public class NotificationButtonTest {
//    
//    private NotificationButton button;
//    private Socket mockSocket;
//    private BufferedReader mockIn;
//    private BufferedWriter mockOut;
//    private final String testUserId = "20211111";
//    
//    @BeforeEach
//    public void setUp() {
//        // Mock 객체 생성
//        mockSocket = mock(Socket.class);
//        mockIn = mock(BufferedReader.class);
//        mockOut = mock(BufferedWriter.class);
//    }
//    
//    @AfterEach
//    public void tearDown() {
//        if (button != null) {
//            button.shutdown();
//        }
//        // NotificationController 인스턴스 정리
//        NotificationController.removeInstance(testUserId);
//    }
//    
//    @Test
//    public void testConstructorWithValidParameters() {
//        // 생성자가 정상적으로 동작하는지 테스트
//        assertDoesNotThrow(() -> {
//            button = new NotificationButton(testUserId, mockSocket, mockIn, mockOut);
//        }, "NotificationButton 생성 시 예외가 발생하지 않아야 함");
//        
//        assertNotNull(button, "NotificationButton이 정상적으로 생성되어야 함");
//    }
//    
//    @Test
//    public void testButtonText() {
//        // 버튼 텍스트가 설정되는지 테스트
//        button = new NotificationButton(testUserId, mockSocket, mockIn, mockOut);
//        
//        String text = button.getText();
//        assertNotNull(text, "버튼 텍스트가 null이 아니어야 함");
//        assertTrue(text.contains("알림"), "버튼 텍스트에 '알림'이 포함되어야 함");
//    }
//    
//    @Test
//    public void testGetController() {
//        // getController 메서드 테스트
//        button = new NotificationButton(testUserId, mockSocket, mockIn, mockOut);
//        
//        NotificationController controller = button.getController();
//        // controller가 null일 수도 있음 (초기화 실패 시)
//        // 따라서 예외가 발생하지 않는지만 확인
//        assertDoesNotThrow(() -> {
//            button.getController();
//        }, "getController 호출 시 예외가 발생하지 않아야 함");
//    }
//    
//    @Test
//    public void testUpdateButtonState() {
//        // updateButtonState 메서드가 예외 없이 실행되는지 테스트
//        button = new NotificationButton(testUserId, mockSocket, mockIn, mockOut);
//        
//        assertDoesNotThrow(() -> {
//            button.updateButtonState();
//        }, "updateButtonState 실행 시 예외가 발생하지 않아야 함");
//    }
//    
//    @Test
//    public void testShutdown() {
//        // shutdown 메서드가 예외 없이 실행되는지 테스트
//        button = new NotificationButton(testUserId, mockSocket, mockIn, mockOut);
//        
//        assertDoesNotThrow(() -> {
//            button.shutdown();
//        }, "shutdown 실행 시 예외가 발생하지 않아야 함");
//    }
//    
//    @Test
//    public void testConstructorWithNullParameters() {
//        // null 파라미터로도 생성 가능한지 테스트
//        assertDoesNotThrow(() -> {
//            button = new NotificationButton(testUserId, null, null, null);
//        }, "null 파라미터로도 NotificationButton 생성이 가능해야 함");
//    }
//}