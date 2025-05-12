/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Reservation;


public class Main {
    public static void main(String[] args) {
        boolean useGUI = true;
        
        if(useGUI) {
            new ReservationGUIController();
        }
        else {
        new ReservationController().start();
        }  
    }
}