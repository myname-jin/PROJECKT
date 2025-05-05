/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visualization;

public class MainApp {
    public static void main(String[] args) {
        ReservationModel model = new ReservationModel();
        ReservationController controller = new ReservationController(model);
        new MainView(model, controller);
    }
}