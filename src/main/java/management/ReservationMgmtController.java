/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import java.util.List;

/**
 *
 * @author suk22
 */
public class ReservationMgmtController {

    private static final String FILE_PATH = "src/main/resources/mgmt_reservation.txt";

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

    public void updateApprovalStatus(String studentId, String newStatus) {
        List<ReservationMgmtModel> reservations = getAllReservations();
        boolean updated = false;

        for (ReservationMgmtModel r : reservations) {
            if (r.getStudentId().equals(studentId)) {
                r.setApproved(newStatus);
                updated = true;
            }
        }

        if (updated) {
            saveAllReservations(reservations);
            javax.swing.JOptionPane.showMessageDialog(null,
                    "학번 " + studentId + "의 승인 여부가 '" + newStatus + "'(으)로 변경되었습니다.",
                    "승인 결과",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void saveAllReservations(List<ReservationMgmtModel> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ReservationMgmtModel r : reservations) {
                String line = String.join(",", r.getStudentId(), r.getDepartment(), r.getName(),
                        r.getRoom(), r.getTime(), r.getApproved());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
