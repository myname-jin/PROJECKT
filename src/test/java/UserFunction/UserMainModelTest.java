/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;

/**
 *
 * @author jms5310
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserMainModelTest {
    
    private UserMainModel model;
    private String userId;
    private Socket mockSocket;
    private BufferedReader mockReader;
    private BufferedWriter mockWriter;
    
    @BeforeEach
    public void setUp() {
        userId = "20211111";
        mockSocket = mock(Socket.class);
        mockReader = mock(BufferedReader.class);
        mockWriter = mock(BufferedWriter.class);
        
        model = new UserMainModel(userId, mockSocket, mockReader, mockWriter);
    }
    
    @Test
    public void testGetUserId() {
        assertEquals(userId, model.getUserId(), "UserID가 생성자에서 설정한 값과 일치해야 함");
    }
    
    @Test
    public void testGetSocket() {
        assertEquals(mockSocket, model.getSocket(), "Socket이 생성자에서 설정한 값과 일치해야 함");
    }
    
    @Test
    public void testGetReader() {
        assertEquals(mockReader, model.getIn(), "BufferedReader가 생성자에서 설정한 값과 일치해야 함");
    }
    
    @Test
    public void testGetWriter() {
        assertEquals(mockWriter, model.getOut(), "BufferedWriter가 생성자에서 설정한 값과 일치해야 함");
    }
    
    @Test
    public void testDefaultConstructor() {
         
    UserMainModel defaultModel = new UserMainModel(null, null, null, null);
    // 실제 구현은 null을 그대로 유지하므로 그에 맞게 테스트
    assertNull(defaultModel.getUserId(), "UserID가 null이면 그대로 null을 반환해야 함");
    }
}