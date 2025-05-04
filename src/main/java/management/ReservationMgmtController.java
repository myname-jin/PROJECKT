/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suk22
 */
public class ReservationMgmtController {
    private static final String FILE_PATH = "mgmt_reservation.txt";

    public List<ReservationMgmtModel> getAllReservations() {
        List<ReservationMgmtModel> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    ReservationMgmtModel reservation = new ReservationMgmtModel(
                            data[0], data[1], data[2],
                            data[3], data[4], data[5]
                    );
                    reservations.add(reservation);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
