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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserReservationListControllerTest {
    
    private UserReservationListController controller;
    private UserReservationListView mockView;
    private JTable mockTable;
    private DefaultTableModel mockTableModel;
    
    @BeforeEach
    public void setUp() {
        // 모의 객체 생성
        mockView = mock(UserReservationListView.class);
        
        // 테이블 및 테이블 모델 설정
        String[] columns = {"이름", "학번", "강의실", "날짜", "요일", "시작시간", "종료시간", "승인상태"};
        mockTableModel = new DefaultTableModel(columns, 0);
        mockTable = new JTable(mockTableModel);
        
        when(mockView.getTable()).thenReturn(mockTable);
        
        // 컨트롤러 생성 - 실제 테스트에서는 실제 구현 사용
        controller = new UserReservationListController("20211111", null, null, null);
        
        // 뷰 설정
        mockView.setController(controller);
    }
    
    @Test
    public void testLoadReservationData() {
        // loadReservationData 메서드 호출
        controller.loadReservationData();
        
        
    }
    
    @Test
    public void testBackToMainPage() {
        // backToMainPage 메서드 호출 시 UserMainController가 생성되는지 확인
        
    }
}