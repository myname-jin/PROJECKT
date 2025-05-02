/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservation;

import java.io.FileWriter;
import java.io.IOException;
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
        System.out.println(selectedName);

         // 5. 강의실 이름 일치 확인 → 있으면 예약 시간 보여주고 예약 완료
        for (RoomModel room : filtered) {
            if (room.getName().trim().equalsIgnoreCase(selectedName.trim())) {
                //예약 가능 시간대 보여주기
                view.showAvailableTimes(room.getAvailableTimes());

                System.out.print("\n예약할 시간대를 입력하세요: ");
                String selectedTime = view.getInput();

                //예약 결과 출력, 정보 저장
                System.out.println("\n[예약 완료] " + room.getName() + " - " + selectedTime);
                 saveReservation(room.getName(), selectedTime);
                return;
            }
        }
        //강의실 이름이 일치하지 않을 때 
        System.out.println("해당 강의실을 찾을 수 없습니다.");
    }
   // 예약 내역을 파일에 저장하는 메서드
    private void saveReservation(String roomName, String time) {
        try (FileWriter writer = new FileWriter("reservation.txt", true)) {
            writer.write(roomName + " - " + time + "\n");
        } catch (IOException e) {
            System.out.println("예약 기록 저장 실패: " + e.getMessage());
        }
    }

}