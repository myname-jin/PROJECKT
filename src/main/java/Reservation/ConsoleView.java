/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservation;

import java.util.Scanner;

/**
 *
 * @author scq37
 */
public class ConsoleView { //입출력 클래스
     private Scanner scanner = new Scanner(System.in);

    public String selectRoomType() {
    System.out.println("[강의실 예약 시스템]");
    System.out.println("1. 강의실");
    System.out.println("2. 실습실");
    System.out.print("> ");
    int input = scanner.nextInt();  //숫자 선택
    scanner.nextLine(); 
    return input == 1 ? "강의실" : "실습실";  // 반환
}

//필터링된 강의실 목록 출력
    public void showRooms(RoomModel[] rooms) {
        System.out.println("\n[강의실 목록]");
        for (RoomModel room : rooms) {
            System.out.println("- " + room.getName());
        }
        System.out.print("\n원하는 강의실을 입력하세요: ");
    }

    //예약 가능 시간대 출력 
    public void showAvailableTimes(String[] times) {
        System.out.println("\n예약 가능한 시간대:");
        for (String time : times) {
            System.out.println("- " + time);
        }
    }

    public String getInput() {
        return scanner.nextLine().trim();
    }
    
    public static void main(String[] args) {
        ConsoleView c = new ConsoleView();
        
        String s = c.selectRoomType();
        System.out.println("테스트"+s);
    }
}


