/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

/**
 *
 * @author adsd3
 */
import java.time.LocalDate;

public class CalendarController {
    private final ReservationServiceModel service;
    private final CalendarView view;

    public CalendarController(ReservationServiceModel service) {
        this.service = service;
        this.view = new CalendarView();
        // 뷰의 더블클릭 핸들러 등록
        view.setDayDoubleClickHandler(this::onDateDoubleClick);
    }

    private void onDateDoubleClick(LocalDate date) {
        new DialogController(service, view).handleDate(date);
    }

    public void start() {
        // 화면 띄우기는 CalendarView 생성자에서 이미 처리됨
    }
}