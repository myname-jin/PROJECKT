/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunction;

/**
 *
 * @author jms5310
 */
import UserNotification.*;
import ServerClient.LogoutUtil;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import Reservation.ReservationGUIController;
import Reservation.ReservationView;
import UserNotification.NotificationController;
import UserNotification.NotificationButton;


public class UserMainController {
    private UserMainModel model;
    private UserMainView view;

    private NotificationController notificationController;
    private NotificationButton notificationButton;
    
    public UserMainController(String userId, Socket socket, BufferedReader in, BufferedWriter out) {
        // 모델 초기화
        this.model = new UserMainModel(userId, socket, in, out);
        
        // 뷰 초기화
        this.view = new UserMainView();
        view.setWelcomeMessage(model.getUserName());
        
        // ✅ 알림 시스템 초기화
        initializeNotificationSystem();
        
        // 리스너 등록
        initListeners();
        
        // 로그아웃 처리 연결 (소켓이 있을 때만)
        if (model.getSocket() != null && model.getOut() != null) {
            LogoutUtil.attach(view, model.getUserId(), model.getSocket(), model.getOut());
        }
        
        // 화면 표시
       view.setVisible(true);
    }
    
    
    
 // ✅ 알림 시스템 초기화 메서드
    private void initializeNotificationSystem() {
        try {
            // NotificationController 생성 (소켓 정보 포함)
            notificationController = NotificationController.getInstance(
        model.getUserId(),
        model.getSocket(),
        model.getIn(),
        model.getOut()
        );
            
            // NotificationButton 생성 (소켓 정보 포함)
            notificationButton = new NotificationButton(
                model.getUserId(),
                model.getSocket(),
                model.getIn(),
                model.getOut()
            );
            
            // 뷰에 알림 버튼 추가
            view.setNotificationButton(notificationButton);
            
            
        } catch (Exception e) {
            System.err.println("❌ 알림 시스템 초기화 실패: " + e.getMessage());
            e.printStackTrace();
            
            // 알림 시스템 실패해도 메인 기능은 계속 동작
            JOptionPane.showMessageDialog(
                view,
                "알림 시스템 초기화에 실패했습니다. 기본 기능은 정상 작동합니다.",
                "경고",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    
    private void initListeners() {
        // 예약 조회 버튼
        view.addViewReservationsListener(e -> openReservationList());
        
        // 강의실 예약 버튼
        view.addCreateReservationListener(e -> openReservationSystem());
        
        // 공지사항 버튼
        view.addNoticeListener(e -> openNoticeSystem());
        
        // 로그아웃 버튼
        view.addLogoutListener(e -> handleLogout());
    }

    private void openReservationList() {
        view.dispose(); // 메인 페이지 닫기
        
            shutdownNotificationSystem();

        
        new UserReservationListController(
            model.getUserId(), 
            model.getSocket(), 
            model.getIn(), 
            model.getOut()
        );
    }

    private void openReservationSystem() {
       try {
        // 예약 시스템으로 연결
        view.showMessage(
            "강의실 예약 시스템으로 연결됩니다",
            "안내", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // ReservationGUIController 생성자 호출
      // new ReservationGUIController(model.getUserId(), model.getSocket(), model.getOut());
      new ReservationGUIController(
            model.getUserId(),
            model.getUserName(),
            model.getUserDept(),
            model.getUserType(),
            model.getSocket(),
            model.getOut()
        );
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            null,
            "예약 시스템 연결 중 오류가 발생했습니다: " + e.getMessage(),
            "오류",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}

    private void openNoticeSystem() {
        try {
        // 사용자 공지사항 조회 화면으로 연결
        view.dispose(); // 현재 메인 화면 닫기
                shutdownNotificationSystem();

        
        new UserNoticeController(
            model.getUserId(), 
            model.getSocket(), 
            model.getIn(), 
            model.getOut()
        );
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            view,
            "공지사항 시스템 연결 중 오류가 발생했습니다: " + e.getMessage(),
            "오류",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}

    private void handleLogout() {
        try {
            
                        shutdownNotificationSystem();

            if (model.getSocket() != null && model.getOut() != null) {
                model.getOut().write("LOGOUT:" + model.getUserId() + "\n");
                model.getOut().flush();
                model.getSocket().close();
            }
            
            view.dispose();
            
            // 로그인 화면으로 이동
            login.LoginView loginView = new login.LoginView();
            login.LoginModel loginModel = new login.LoginModel();
            new login.LoginController(loginView, loginModel);
            loginView.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            view.showMessage(
                "로그아웃 중 오류가 발생했습니다: " + ex.getMessage(),
                "오류", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
     private void shutdownNotificationSystem() {
        try {
            if (notificationController != null) {
                notificationController.shutdown();
            }
            
            if (notificationButton != null) {
                notificationButton.shutdown();
            }
        } catch (Exception e) {
            System.err.println("❌ 알림 시스템 정리 중 오류: " + e.getMessage());
        }
    }
    
    // ✅ Getter 메서드들 (테스트용)
    public NotificationController getNotificationController() {
        return notificationController;
    }
    
    public NotificationButton getNotificationButton() {
        return notificationButton;
    }
}
