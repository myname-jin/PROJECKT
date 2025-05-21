/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package UserFunction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author jms5310
 */
public class UserMainControllerTest {
    
    private UserMainController controller;
    private UserMainModel mockModel;
    private UserMainView mockView;
    private Socket mockSocket;
    private BufferedReader mockIn;
    private BufferedWriter mockOut;
    
    public UserMainControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Mock objects setup
        mockSocket = mock(Socket.class);
        mockIn = mock(BufferedReader.class);
        mockOut = mock(BufferedWriter.class);
        
        // Create a real model with mock dependencies
        mockModel = new UserMainModel("20211111", mockSocket, mockIn, mockOut);
        
        // Create a mock view for testing actions
        mockView = mock(UserMainView.class);
        
        // Use reflection to set the private view field
        try {
            java.lang.reflect.Field viewField = UserMainController.class.getDeclaredField("view");
            viewField.setAccessible(true);
            
            // Create the controller with the mocked dependencies
            controller = new UserMainController("20211111", mockSocket, mockIn, mockOut);
            
            // Replace the view with our mock
            viewField.set(controller, mockView);
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructorInitializesComponents() {
        // Test that the controller initializes the model with correct values
        UserMainController realController = new UserMainController("20212222", mockSocket, mockIn, mockOut);
        
        // Since model is private, we need to use reflection to check it
        try {
            java.lang.reflect.Field modelField = UserMainController.class.getDeclaredField("model");
            modelField.setAccessible(true);
            UserMainModel model = (UserMainModel) modelField.get(realController);
            
            assertEquals("20212222", model.getUserId());
            assertSame(mockSocket, model.getSocket());
            assertSame(mockIn, model.getIn());
            assertSame(mockOut, model.getOut());
        } catch (Exception e) {
            fail("Could not access model field: " + e.getMessage());
        }
    }

    @Test
    public void testViewIsInitializedWithUserId() {
        // Create a new controller with real view for this test
        UserMainController realController = new UserMainController("20213333", mockSocket, mockIn, mockOut);
        
        // Check the view was set with user ID
        try {
            java.lang.reflect.Field viewField = UserMainController.class.getDeclaredField("view");
            viewField.setAccessible(true);
            UserMainView view = (UserMainView) viewField.get(realController);
            
            // We can't directly check if setWelcomeMessage was called, but the view should be visible
            assertTrue(view.isDisplayable());
        } catch (Exception e) {
            fail("Could not access view field: " + e.getMessage());
        }
    }

    @Test
    public void testOpenReservationList() throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method method = UserMainController.class.getDeclaredMethod("openReservationList");
        method.setAccessible(true);
        
        // Call the method
        method.invoke(controller);
        
        // Verify the view's dispose method was called
        verify(mockView).dispose();
    }

    @Test
    public void testOpenReservationSystem() throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method method = UserMainController.class.getDeclaredMethod("openReservationSystem");
        method.setAccessible(true);
        
        // Call the method
        method.invoke(controller);
        
        // Verify the view's showMessage method was called
        verify(mockView).showMessage(
            eq("강의실 예약 시스템으로 연결됩니다"), 
            eq("안내"), 
            eq(JOptionPane.INFORMATION_MESSAGE)
        );
    }

    @Test
    public void testOpenNoticeSystem() throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method method = UserMainController.class.getDeclaredMethod("openNoticeSystem");
        method.setAccessible(true);
        
        // Call the method
        method.invoke(controller);
        
        // Verify the view's showMessage method was called
        verify(mockView).showMessage(
            eq("공지사항 화면으로 연결됩니다."), 
            eq("안내"), 
            eq(JOptionPane.INFORMATION_MESSAGE)
        );
    }

    @Test
    public void testHandleLogout() throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method method = UserMainController.class.getDeclaredMethod("handleLogout");
        method.setAccessible(true);
        
        // Call the method
        method.invoke(controller);
        
        // Verify the view's dispose method was called
        verify(mockView).dispose();
        
        // Verify the writer's write and flush methods were called
        try {
            verify(mockOut).write("LOGOUT:" + mockModel.getUserId() + "\n");
            verify(mockOut).flush();
        } catch (IOException e) {
            fail("IOException during verification: " + e.getMessage());
        }
        
        // Verify socket was closed
        try {
            verify(mockSocket).close();
        } catch (IOException e) {
            fail("IOException during verification: " + e.getMessage());
        }
    }
}