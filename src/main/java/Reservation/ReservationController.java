/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservation;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;


/**
 *
 * @author scq37
 */
public class ReservationController {  //예약 제어 클래스
    
    private RoomModel[] allRooms = { //더미 데이터. 엑셀이나 메모장 파일로 불러올 예정
        new RoomModel("912", "강의실", new String[]{"09:00", "11:00", "15:00"}),
        new RoomModel("913", "강의실", new String[]{"09:00", "13:00"}),
        new RoomModel("911", "실습실", new String[]{"10:00", "13:00"})
    };

    private ConsoleView view = new ConsoleView(); //사용자 인터페이스 객체 생성

    public void start() {
         // 사용자 정보 입력
        String name = view.inputName();
        String userType = view.inputUserType();
        String userId = view.inputUserId();
        String department = view.inputDepartment();
        
        //1. 강의실 또는 실습실 중 하나 선택
        String type = view.selectRoomType();  

        //2. 해당 유형에 맞는 강의실 유형 출력
        RoomModel[] filtered = Arrays.stream(allRooms)
            .filter(r -> r.getType().equals(type))  
            .toArray(RoomModel[]::new);

        //3. 필터링된 강의실 없으면 종료
        if (filtered.length == 0) {
            System.out.println("해당 유형에 대한 강의실이 없습니다.");
            return;
        }

         //4. 강의실 목록 보여주기
        view.showRooms(filtered);
        String selectedName = view.getInput();
        RoomModel selectedRoom = null;
        for (RoomModel room : filtered) {
            if (room.getName().equalsIgnoreCase(selectedName.trim())) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("해당 강의실을 찾을 수 없습니다.");
            return;
        }

        // 예약 상세 정보 입력
        view.showAvailableTimes(selectedRoom.getAvailableTimes());
        String date = view.inputDate();
        String startTime = view.inputStartTime();
        String endTime = view.inputEndTime();
        String purpose = view.inputPurpose();
        String status = "예약완료";

        // 결과 출력
        System.out.println("\n[예약 완료]");
        System.out.println("강의실: " + selectedRoom.getName());
        System.out.println("시간: " + startTime + " ~ " + endTime);

        // 파일 저장
        saveReservation(name, userType, userId, department,
                        selectedRoom.getType(), selectedRoom.getName(),
                        date, startTime, endTime, purpose, status);
    }

    private void saveReservation(String name, String userType, String userId, String department,
                                  String roomType, String roomNumber,
                                  String date, String startTime, String endTime,
                                  String purpose, String status) {
               String filePath = "src/main/resources/reservation.txt"; 
               
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"))) {

            writer.write(String.join(",", name, userType, userId, department,
                                     roomType, roomNumber, date, startTime, endTime,
                                     purpose, status));
            writer.newLine();

        } catch (IOException e) {
            System.out.println("예약 저장 실패: " + e.getMessage());
        }
    }
}