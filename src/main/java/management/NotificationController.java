/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author suk22
 */
public class NotificationController {

    private NotificationModel model;
    private NotificationView view;
    private Set<String> shownMessages = new HashSet<>();
    private Timer timer;

    public NotificationController() {
        this.model = new NotificationModel(); // 기본 생성자 혹은 필요한 생성자 사용
    }

    // 기존 10초마다 예약 상태 확인
    public void startMonitoring() {
        timer = new Timer(true); // 데몬 스레드로 실행
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkAndShowNewNotifications();
            }
        }, 0, 10_000); // 10초마다 검사
    }

    // 예약 상태 즉시 갱신용 공개 메서드
    public void refreshNotifications() {
        checkAndShowNewNotifications();
    }

    // 중복 코드를 함수로 분리
    private void checkAndShowNewNotifications() {
        List<String> pendingList = model.getPendingReservations();
        List<String> newMessages = new ArrayList<>();

        for (String msg : pendingList) {
            if (!shownMessages.contains(msg)) {
                shownMessages.add(msg);
                newMessages.add(msg);
            }
        }

        if (!newMessages.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                String message = "새로운 알림이 " + newMessages.size() + "건 있습니다.";
                JOptionPane.showMessageDialog(null, message, "알림", JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    public void showNotificationView() {
        List<String> pendingList = model.getPendingReservations();
        if (pendingList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "현재 대기 중인 예약이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(() -> {
            view = new NotificationView(pendingList);
            view.setVisible(true);
        });
    }

    public void stopMonitoring() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
