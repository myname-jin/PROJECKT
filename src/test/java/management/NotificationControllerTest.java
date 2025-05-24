/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author suk22
 */
public class NotificationControllerTest {

    NotificationController controller;

    @BeforeEach
    void setup() {
        // 테스트용 controller 생성
        controller = new NotificationController() {
            @Override
            public void refreshNotifications() {
                // 초기 상태 강제 세팅
                List<String> initialPending = Arrays.asList(
                        "홍길동님이 강의실 101호를 2025-05-24(금) 13:00~15:00에 수업 목적으로 예약 신청하였고, 현재 예약 대기 상태입니다."
                );
                List<String> initialAll = Arrays.asList(
                        "홍길동,컴퓨터공학과,...,예약 대기"
                );
                shownPending = new HashSet<>(initialPending);
                shownAll = new HashSet<>(initialAll);
            }

            @Override
            public Map<String, List<String>> detectNotificationChanges() {
                // 새로 들어온 예약 1건, 취소된 예약 1건 있다고 가정
                List<String> pending = Arrays.asList(
                        "홍길동님이 강의실 101호를 2025-05-24(금) 13:00~15:00에 수업 목적으로 예약 신청하였고, 현재 예약 대기 상태입니다.",
                        "김영희님이 실습실 202호를 2025-05-25(토) 10:00~12:00에 실습 목적으로 예약 신청하였고, 현재 예약 대기 상태입니다."
                );
                List<String> all = Arrays.asList(
                        "김영희,전자공학과,...,예약 대기"
                );

                Set<String> currentPendingSet = new HashSet<>(pending);
                Set<String> currentAllSet = new HashSet<>(all);

                List<String> newPendingMessages = new ArrayList<>();
                List<String> removedReservations = new ArrayList<>();

                for (String msg : pending) {
                    if (!shownPending.contains(msg)) {
                        newPendingMessages.add(msg);
                    }
                }

                for (String old : shownAll) {
                    if (!currentAllSet.contains(old)) {
                        removedReservations.add(old);
                    }
                }

                shownPending = currentPendingSet;
                shownAll = currentAllSet;

                Map<String, List<String>> result = new HashMap<>();
                result.put("newPending", newPendingMessages);
                result.put("removed", removedReservations);
                return result;
            }
        };

        controller.refreshNotifications(); // 초기 상태 설정
    }

    @Test
    void testNotificationChangeDetection() {
        Map<String, List<String>> changes = controller.detectNotificationChanges();

        assertEquals(1, changes.get("newPending").size());
        assertTrue(changes.get("newPending").get(0).contains("김영희"));

        assertEquals(1, changes.get("removed").size());
        assertTrue(changes.get("removed").get(0).contains("홍길동"));
    }
}
