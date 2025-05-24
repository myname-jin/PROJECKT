package UserFunction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;


public class UserMainControllerTest {
    
    private UserMainController controller;
    private Socket mockSocket;
    private BufferedReader mockIn;
    private BufferedWriter mockOut;
    
    @BeforeEach
    public void setUp() {
        // Mock objects setup
        mockSocket = mock(Socket.class);
        mockIn = mock(BufferedReader.class);
        mockOut = mock(BufferedWriter.class);
    }
    
    @AfterEach
    public void tearDown() {
        if (controller != null) {
            // 컨트롤러 정리
            controller = null;
        }
    }

    @Test
    public void testConstructorWithValidParameters() {
        // 생성자가 예외 없이 실행되는지 테스트
        assertDoesNotThrow(() -> {
            controller = new UserMainController("20211111", mockSocket, mockIn, mockOut);
        }, "컨트롤러 생성 시 예외가 발생하지 않아야 함");
        
        assertNotNull(controller, "컨트롤러가 정상적으로 생성되어야 함");
    }

    @Test
    public void testConstructorWithNullSocket() {
        // null 소켓으로도 생성 가능한지 테스트
        assertDoesNotThrow(() -> {
            controller = new UserMainController("20211111", null, null, null);
        }, "null 파라미터로도 컨트롤러 생성이 가능해야 함");
    }

    @Test
    public void testGetNotificationController() {
        // NotificationController getter 테스트
        controller = new UserMainController("20211111", mockSocket, mockIn, mockOut);
        
        // NotificationController가 초기화되었는지 확인
        assertNotNull(controller.getNotificationController(), 
                     "NotificationController가 초기화되어야 함");
    }

    @Test
    public void testGetNotificationButton() {
        // NotificationButton getter 테스트
        controller = new UserMainController("20211111", mockSocket, mockIn, mockOut);
        
        // NotificationButton이 초기화되었는지 확인
        assertNotNull(controller.getNotificationButton(), 
                     "NotificationButton이 초기화되어야 함");
    }
}