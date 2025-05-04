/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import management.ReservationMgmtView;
import javax.swing.*;

/**
 *
 * @author suk22
 */
public class ReservationMgmt {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        } // 필요 시 완전 생략 가능
        
        SwingUtilities.invokeLater(() -> {
            ReservationMgmtView view = new ReservationMgmtView();
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }
}
