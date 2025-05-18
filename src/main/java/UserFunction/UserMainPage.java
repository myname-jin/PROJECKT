/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import ServerClient.LogoutUtil;

/**
 *
 * @author jms5310
 */
public class UserMainPage extends JFrame {
    private final String userId;
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    /**
     * 사용자 메인 페이지 생성자
     * @param userId 사용자 ID
     * @param socket 소켓 연결
     * @param in 입력 스트림
     * @param out 출력 스트림
     */
    public UserMainPage(String userId, Socket socket, BufferedReader in, BufferedWriter out) {
        this.userId = (userId != null && !userId.trim().isEmpty()) ? userId : "20211111";
        this.socket = socket;
        this.in = in;
        this.out = out;

        initUI();
        
        // 로그아웃 처리 연결
        if (socket != null && out != null) {
            LogoutUtil.attach(this, userId, socket, out);
        }
    }
    
    /**
     * UI 초기화
     */
    private void initUI() {
        // 기본 프레임 설정
        setTitle("강의실 예약 시스템 - 사용자 메뉴");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 상단 패널 - 환영 메시지
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // 중앙 패널 - 기능 버튼들
        JPanel centerPanel = createButtonPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // 하단 패널 - 로그아웃 버튼
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
        
        // 화면 표시
        setVisible(true);
    }
    
    /**
     * 상단 패널 생성 - 환영 메시지 및 정보 표시
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 메인 환영 메시지
        JLabel welcomeLabel = new JLabel("환영합니다, " + userId + "님", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        panel.add(welcomeLabel, BorderLayout.CENTER);
        
        // 현재 시간 정보 
        JLabel dateLabel = new JLabel(new java.util.Date().toString(), SwingConstants.RIGHT);
        dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        panel.add(dateLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * 중앙 버튼 패널 생성 - 각 기능 버튼 배치
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 예약 조회 버튼
        JButton viewButton = createFunctionButton("내 예약 조회/취소", "예약 목록을 확인하고 취소할 수 있습니다.");
        viewButton.addActionListener(e -> openReservationList());
        panel.add(viewButton);
        
        // 강의실 예약 버튼
        JButton reserveButton = createFunctionButton("강의실 예약하기", "새로운 강의실을 예약합니다.");
        reserveButton.addActionListener(e -> openReservationSystem());
        panel.add(reserveButton);
        
        // 공지사항 버튼
        JButton noticeButton = createFunctionButton("공지사항 확인하기", "관리자가 등록한 공지사항을 확인합니다.");
        noticeButton.addActionListener(e -> openNoticeSystem());
        panel.add(noticeButton);
        
        return panel;
    }
    
    /**
     * 스타일이 적용된 기능 버튼 생성
     */
    private JButton createFunctionButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240));
        button.setPreferredSize(new Dimension(200, 60));
        return button;
    }
    
    /**
     * 하단 패널 생성 - 로그아웃 버튼
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> handleLogout());
        panel.add(logoutButton);
        
        return panel;
    }
    
    /**
     * 로그아웃 처리
     */
    private void handleLogout() {
        try {
            if (socket != null && out != null) {
                out.write("LOGOUT:" + userId + "\n");
                out.flush();
                socket.close();
            }
            
            this.dispose();
            
            // 로그인 화면으로 이동
            login.LoginView view = new login.LoginView();
            login.LoginModel model = new login.LoginModel();
            new login.LoginController(view, model);
            view.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                    "로그아웃 중 오류가 발생했습니다: " + ex.getMessage(),
                    "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 예약 조회 화면 열기
     */
    private void openReservationList() {
        new UserReservationListController(userId, socket, in, out);
    this.dispose(); // 메인 페이지 닫기
    }
    
    /**
     * 예약 시스템 열기
     */
    private void openReservationSystem() {
        // 예약/취소 시스템으로 연결 
        JOptionPane.showMessageDialog(this, 
                "강의실 예약 시스템으로 연결됩니다",
                "안내", JOptionPane.INFORMATION_MESSAGE);
        
        // 실제 구현 시
        // new ReservationGUIController(userId, socket, in, out);
    }
    
    /**
     * 공지사항 시스템 열기
     */
    private void openNoticeSystem() {
        // 공지사항 화면으로 연결 
        JOptionPane.showMessageDialog(this, 
                "공지사항 화면으로 연결됩니다.",
                "안내", JOptionPane.INFORMATION_MESSAGE);
        
        // 실제 구현 시
        // new UserNoticeController(userId, socket, in, out);
    }
    
    /**
     * 테스트용 메인 메서드
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 테스트용 - 실제 소켓 연결 없이 UI만 표시
            new UserMainPage(null, null, null, null);
        });
    }
}
