/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

/**
 *
 * @author adsd3
 */
 //다이얼로그 순차 흐름 제어 (블록→방종류→호수→시간대)

import java.time.LocalDate;
import java.util.List;

public class DialogController {
    private final ReservationServiceModel service;
    private final CalendarView view;

    public DialogController(ReservationServiceModel service, CalendarView view) {
        this.service = service;
        this.view = view;
    }

    public void handleDate(LocalDate date) {
        // 1) 전체/일부/차단취소 선택
        BlockTypeDialogView blockDlg = new BlockTypeDialogView();
        // 이미 전체 차단된 날짜면 '일부 차단' 비활성화
        boolean isFull = service.isFullyBlocked(date.toString());
        blockDlg.setDisablePartialBlock(isFull);

        blockDlg.setHandler(new BlockTypeDialogView.Handler() {
            @Override public void onBlockFull() {
                service.blockAll(date.toString());
                view.refresh();
                blockDlg.close();
            }
            @Override public void onBlockPartial() {
                blockDlg.close();
                showRoomType(date);
            }
            @Override public void onUnblock() {
                service.unblock(date.toString());
                view.refresh();
                blockDlg.close();
            }
            @Override public void onCancel() {
                blockDlg.close();
            }
        });
        blockDlg.show();  // 크기 조정은 뷰 내부에서 처리
    }

    private void showRoomType(LocalDate date) {
        RoomTypeDialogView rt = new RoomTypeDialogView();
        rt.setHandler(new RoomTypeDialogView.Handler() {
            @Override public void onSelectLecture() {
                rt.close(); showRoomNumber(date, "강의실");
            }
            @Override public void onSelectLab() {
                rt.close(); showRoomNumber(date, "실습실");
            }
            @Override public void onCancel() { rt.close(); }
        });
        rt.show();
    }

    private void showRoomNumber(LocalDate date, String roomType) {
        RoomNumberDialogView rnd = new RoomNumberDialogView();
        rnd.setHandler(new RoomNumberDialogView.Handler() {
            @Override public void onSelect(String roomNumber) {
                rnd.close(); showTimeSlot(date, roomType, roomNumber);
            }
            @Override public void onCancel() { rnd.close(); }
        });
        rnd.show();
    }

    private void showTimeSlot(LocalDate date, String roomType, String roomNumber) {
        // 2) 이미 차단된 시간대 읽어와서 뷰에 넘김
        List<String> blockedSlots = service.getBlockedTimeSlots(
            date.toString(), roomType, roomNumber);

        TimeSlotDialogView ts = new TimeSlotDialogView(blockedSlots);
        ts.setHandler(new TimeSlotDialogView.Handler() {
            @Override public void onSelect(String slot) {
                service.blockPartial(date.toString(), roomType, roomNumber, slot);
                view.refresh();
                ts.close();
            }
            @Override public void onCancel() {
                ts.close();
            }
        });
        ts.show();
    }
}