/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

/**
 *
 * @author adsd3
 */

//애플리케이션 시작 및 Controller 초기화

import javax.swing.SwingUtilities;

public class CalendarReservationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Repository → Service → Controller 순 의존성 주입
            ReservationRepositoryModel repo = new ReservationRepositoryModel();
            ReservationServiceModel service = new ReservationServiceModel(repo);
            new CalendarController(service).start();
        });
    }
}