/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TESTPAKAGE;

/**
 *
 * @author adsd3
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationModel {
    private final String FILE_NAME = "blocked_dates.txt";

    public void saveReservation(String reservationType, String date, String roomType, String roomNumber, String timeSlot) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = String.join(",", reservationType, date, roomType, roomNumber, timeSlot);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> loadReservations() {
        List<String> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reservations.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public List<String> getBlockedDates() {
        // 📅 차단된 모든 날짜를 중복 없이 반환
        return loadReservations().stream()
            .map(line -> line.split(",")[1])  // 날짜 추출
            .distinct()
            .collect(Collectors.toList());
    }

    public boolean isFullyBlocked(String date) {
        return loadReservations().stream()
            .anyMatch(line -> line.startsWith("모두," + date + ","));
    }

    public List<String> getBlockedTimeSlots(String date, String roomType, String roomNumber) {
        return loadReservations().stream()
            .filter(line -> line.contains(date + "," + roomType + "," + roomNumber + ","))
            .map(line -> line.split(",")[4])
            .distinct()
            .collect(Collectors.toList());
    }

    public void removeReservation(String date) {
        try {
            List<String> updatedReservations = loadReservations().stream()
                .filter(line -> !line.contains(date + ","))
                .collect(Collectors.toList());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (String reservation : updatedReservations) {
                    writer.write(reservation);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}