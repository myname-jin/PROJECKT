/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suk22
 */
public class NotificationModel {

    private static final String RESERVATION_FILE = "src/main/resources/reservation.txt";

    public List<String> getPendingReservations() {
        List<String> pendingList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 12 && parts[11].trim().equals("예약 대기")) {
                    String name = parts[0].trim();
                    String roomType = parts[4].trim();  // 실습실 / 강의실
                    String roomNumber = parts[5].trim();
                    String date = parts[6].trim();
                    String dayOfWeek = parts[7].trim(); // 금
                    String startTime = parts[8].trim();
                    String endTime = parts[9].trim();
                    String purpose = parts[10].trim();

                    String message = String.format(
                            "%s님이 %s %s호를 %s(%s) %s~%s에 %s 목적으로 예약 신청하였고, 현재 예약 대기 상태입니다.",
                            name, roomType, roomNumber, date, dayOfWeek, startTime, endTime, purpose
                    );
                    pendingList.add(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pendingList;
    }
}
