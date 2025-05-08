/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TESTPAKAGE;
import javax.swing.*;

/**
 *
 * @author adsd3
 */
// Main Application - Calendar Reservation System (CalexndarReservationApp.java)
import javax.swing.*;

public class CalendarReservationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservationModel model = new ReservationModel();
            new CalendarView(model);
        });
    }
}

