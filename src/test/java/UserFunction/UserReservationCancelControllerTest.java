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
import org.junit.jupiter.api.AfterEach;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserReservationCancelControllerTest {

    // 테스트 대상
    private UserReservationCancelController controller;
    
    // 모의 객체
    private UserReservationCancelView mockView;
    private UserReservationCancelModel mockModel;
    private UserReservationListView mockParentView;
    private DefaultTableModel tableModel;
    
    // JOptionPane 모킹을 위한 필드
    private MockedStatic<JOptionPane> mockedJOptionPane;
    
    @BeforeEach
    public void setUp() {
        // JOptionPane 정적 메서드 모킹 시작
        mockedJOptionPane = mockStatic(JOptionPane.class);
        
        // 모의 객체 설정
        mockView = mock(UserReservationCancelView.class);
        mockModel = mock(UserReservationCancelModel.class);
        
        // 테이블 모델 및 부모 뷰 설정
        String[] columns = {"이름", "학번", "강의실", "날짜", "요일", "시작시간", "종료시간", "승인상태"};
        Object[][] data = {
            {"김학생", "20211234", "912", "2025-05-20", "화", "09:00", "10:00", "예약대기"}
        };
        tableModel = new DefaultTableModel(data, columns);
        
        JTable table = new JTable(tableModel);
        mockParentView = mock(UserReservationListView.class);
        when(mockParentView.getTable()).thenReturn(table);
        
        // 컨트롤러 생성 및 모의 객체 주입
        controller = new UserReservationCancelController(mockParentView, 0);
        
        try {
            injectMockView(mockView);
            injectMockModel(mockModel);
        } catch (Exception e) {
            fail("테스트 설정 중 오류 발생: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
        // JOptionPane 정적 메서드 모킹 종료
        mockedJOptionPane.close();
    }
    
    @Test
    public void testHandleCancelConfirm_성공케이스() throws Exception {
        // 준비
        when(mockView.getCancelReason()).thenReturn("개인 사정으로 인한 취소");
        when(mockModel.cancelReservation(anyString(), anyString(), anyString())).thenReturn(true);
        when(mockModel.saveCancelReason(anyString(), anyString())).thenReturn(true);
        
        // 실행
        invokeHandleCancelConfirm();
        
        // 검증
        verify(mockView).getCancelReason();
        verify(mockView, never()).showError(anyString());
        assertEquals(0, tableModel.getRowCount(), "행이 테이블에서 제거되어야 함");
    }
    
    @Test
    public void testHandleCancelConfirm_취소사유없음() throws Exception {
        // 준비
        when(mockView.getCancelReason()).thenReturn("");
        
        // 실행
        invokeHandleCancelConfirm();
        
        // 검증
        verify(mockView).showError("취소 사유를 입력해주세요.");
        assertEquals(1, tableModel.getRowCount(), "테이블이 변경되지 않아야 함");
    }
    
    @Test
    public void testHandleCancelConfirm_예약취소실패() throws Exception {
        // 준비
        when(mockView.getCancelReason()).thenReturn("개인 사정");
        when(mockModel.cancelReservation(anyString(), anyString(), anyString())).thenReturn(false);
        
        // 실행
        invokeHandleCancelConfirm();
        
        // 검증
        verify(mockView).showError("예약 취소 처리 중 오류가 발생했습니다.");
        assertEquals(1, tableModel.getRowCount(), "테이블이 변경되지 않아야 함");
    }
    
    @Test
public void testHandleCancelConfirm_취소사유저장실패() throws Exception {
    // 준비
    when(mockView.getCancelReason()).thenReturn("개인 사정");
    when(mockModel.cancelReservation(anyString(), anyString(), anyString())).thenReturn(true);
    when(mockModel.saveCancelReason(anyString(), anyString())).thenReturn(false);
    
    // 실행
    invokeHandleCancelConfirm();
    
    // 검증
    verify(mockView).showError("취소 사유 저장 중 오류가 발생했습니다.");
    assertEquals(1, tableModel.getRowCount(), "취소 사유 저장에 실패하면 테이블 행은 그대로 유지됨");
}
    
    @Test
    public void testShowView() {
        // 실행
        controller.showView();
        
        // 검증
        verify(mockView).setVisible(true);
    }
    
    // UserReservationCancelController에 mockView 주입
    private void injectMockView(UserReservationCancelView view) throws Exception {
        Field viewField = UserReservationCancelController.class.getDeclaredField("view");
        viewField.setAccessible(true);
        viewField.set(controller, view);
    }
    
    // UserReservationCancelController에 mockModel 주입
    private void injectMockModel(UserReservationCancelModel model) throws Exception {
        Field modelField = UserReservationCancelController.class.getDeclaredField("model");
        modelField.setAccessible(true);
        modelField.set(controller, model);
    }
    
    // handleCancelConfirm 메서드 호출
    private void invokeHandleCancelConfirm() throws Exception {
        Method method = UserReservationCancelController.class.getDeclaredMethod("handleCancelConfirm");
        method.setAccessible(true);
        method.invoke(controller);
    }
}