/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

/**
 *
 * @author adsd3
 */
// 뷰와 서비스 연결 및 날짜 더블클릭 이벤트 처리
import java.time.LocalDate;
import management.ReservationMgmtView;

public class CalendarController {
    private final ReservationServiceModel service;
    private final CalendarView view;

    public CalendarController(ReservationServiceModel service) {
        this.service = service;
        this.view = new CalendarView();

        // 날짜 더블클릭 핸들러 등록
        view.setDayDoubleClickHandler(this::onDateDoubleClick);

        // 🔁 뒤로가기 버튼 핸들러 등록
        view.addBackButtonListener(e -> {
            view.dispose();
            ReservationMgmtView mgmtView = new ReservationMgmtView();
            mgmtView.setLocationRelativeTo(null); // 정중앙에 띄우기
            mgmtView.setVisible(true);
        });
    }

    private void onDateDoubleClick(LocalDate date) {
        new DialogController(service, view).handleDate(date);
    }

    public void start() {
        // 화면 띄우기는 CalendarView 생성자에서 이미 처리됨
    }
}