package Reservation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReservationGUIController {
    private ReservationView view;
    private static final String EXCEL_PATH = "src/main/resources/available_rooms.xlsx";
    private static final List<String> LAB_ROOMS = Arrays.asList("911", "915", "916", "918");
    private List<RoomModel> allRooms = new ArrayList<>();
    private Workbook workbook;
    private String userName = "김민준";
    private String userId = "20211111";
    private String userDept = "컴퓨터소프트웨어공학";

    public ReservationGUIController() {
        view = new ReservationView();
        view.setUserInfo(userName, userId, userDept);
        loadRoomsFromExcel();

 // 강의실 유형 선택시 → 해당 방 목록 표시
        view.setRoomTypeList(Arrays.asList("강의실", "실습실"));
        view.addRoomTypeSelectionListener(e -> {
            String selectedType = view.getSelectedRoomType();
            List<String> filtered = allRooms.stream()
                    .filter(r -> r.getType().equals(selectedType))
                    .map(RoomModel::getName)
                    .collect(Collectors.toList());
            view.setRoomList(filtered);
        });


        // 날짜 or 강의실 선택 변경 시 시간대 갱신
        ActionListener timeUpdateListener = e -> updateAvailableTimes();
        view.addRoomSelectionListener(timeUpdateListener);
        view.addDateSelectionListener(timeUpdateListener);

        // 예약 버튼 이벤트 처리
        view.addReserveButtonListener(e -> {
            String date = view.getSelectedDate();
            List<String> times = view.getSelectedTimes();
            String time = view.getSelectedTime();
            String purpose = view.getSelectedPurpose();
            String selectedRoomName = view.getSelectedRoom();
            RoomModel selectedRoom = getRoomByName(selectedRoomName);

            if (date.isEmpty() || time.isEmpty() || purpose.isEmpty() || selectedRoom == null) {
                view.showMessage("모든 항목을 입력해주세요.");
                return;
            }
            if (isUserAlreadyReserved(userId, date)) {
                view.showMessage("해당 날짜에 이미 예약이 존재합니다. 하루 1회만 예약할 수 있습니다.");
                return;
            }

            int totalMinutes = calculateTotalDuration(times);
            if (totalMinutes > 120) {
                view.showMessage("총 예약 시간이 2시간(120분)을 초과할 수 없습니다.");
                return;
            }
            
            view.showMessage("예약이 등록되었습니다. 관리자의 승인을 기다리는 중입니다.");
            
            String dayOfWeek = getDayOfWeek(date);
            
            for (String selectedTime : times) {
                String[] split = selectedTime.split("~");
                if (split.length == 2) {
                    String startTime = split[0].trim();
                    String endTime = split[1].trim();
            
            saveReservation(userName, "학생", userId, userDept,
                    selectedRoom.getType(), selectedRoom.getName(),
                    date, dayOfWeek, startTime, endTime, purpose, "예약대기");
                }
            }
        });

        view.setVisible(true);
    }
    

    private void updateAvailableTimes() {
        String date = view.getSelectedDate();
        String roomName = view.getSelectedRoom();
        if (date == null || date.isEmpty() || roomName == null) return;

        int dayCol = getDayColumnIndex(date);
        //주말 예약 불가 로직
        //if (dayCol == -1) {
          //  view.clearTimeSlots();
            // view.showMessage("주말은 예약할 수 없습니다.");
            //return;
        // }

        Sheet sheet = workbook.getSheet(roomName);
        List<String> availableTimes = getAvailableTimesByDay(sheet, dayCol);

        view.clearTimeSlots();
        for (String time : availableTimes) {
            view.addTimeSlot(time, e -> {
                int total = calculateTotalDuration(view.getSelectedTimes());
                view.setTotalDuration(total + "분");
            });
        }
    }
    
    private int calculateTotalDuration(List<String> times) {
        int total = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (String time : times) {
            try {
                String[] parts = time.split("~");
                Date start = sdf.parse(parts[0]);
                Date end = sdf.parse(parts[1]);
                long diff = (end.getTime() - start.getTime()) / (1000 * 60);
                total += diff;
            } catch (ParseException e) {
                System.out.println("시간 파싱 오류: " + time);
            }
        }
        return total;
    }

    private boolean isUserAlreadyReserved(String userId, String date) {
        String path = "src/main/resources/reservation.txt";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    if (parts[2].equals(userId) && parts[6].equals(date)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("예약 기록 읽기 실패: " + e.getMessage());
        }
        return false;
    }

    private RoomModel getRoomByName(String name) {
        for (RoomModel r : allRooms) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    private void loadRoomsFromExcel() {
        try (InputStream fis = new FileInputStream(EXCEL_PATH)) {
            workbook = new XSSFWorkbook(fis);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String roomName = sheet.getSheetName();
                RoomModel room = new RoomModel(roomName,
                        LAB_ROOMS.contains(roomName) ? "실습실" : "강의실",
                        new String[0]);
                allRooms.add(room);
            }
        } catch (IOException e) {
            System.out.println("엑셀 파일 읽기 오류: " + e.getMessage());
        }
    }

    private int getDayColumnIndex(String selectedDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(selectedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return switch (dayOfWeek) {
                case Calendar.MONDAY -> 1;
                case Calendar.TUESDAY -> 2;
                case Calendar.WEDNESDAY -> 3;
                case Calendar.THURSDAY -> 4;
                case Calendar.FRIDAY -> 5;
                case Calendar.SATURDAY -> 6;
                case Calendar.SUNDAY -> 7;
                    
                default -> -1;
            };
        } catch (Exception e) {
            return -1;
        }
    }

    private List<String> getAvailableTimesByDay(Sheet sheet, int dayCol) {
        List<String> times = new ArrayList<>();
        for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;

            Cell timeCell = row.getCell(0);
            if (timeCell == null || timeCell.getCellType() != CellType.STRING) continue;

            String time = timeCell.getStringCellValue();
            Cell cell = row.getCell(dayCol);
            if (cell != null && "비어있음".equals(cell.getStringCellValue().trim())) {
                times.add(time);
            }
        }
        return times;
    }
    
    private void saveReservation(String name, String userType, String userId, String department,
                                 String roomType, String roomNumber,
                                 String date, String dayOfWeek, String startTime, String endTime,
                                 String purpose, String status) {
        String filePath = "src/main/resources/reservation.txt";
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"))) {
            writer.write(String.join(",", name, userType, userId, department,
                    roomType, roomNumber, date, dayOfWeek, startTime, endTime,
                    purpose, status));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("예약 저장 실패: " + e.getMessage());
        }
    }
    
    private String getDayOfWeek(String dateStr) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY -> "일";
            case Calendar.MONDAY -> "월";
            case Calendar.TUESDAY -> "화";
            case Calendar.WEDNESDAY -> "수";
            case Calendar.THURSDAY -> "목";
            case Calendar.FRIDAY -> "금";
            case Calendar.SATURDAY -> "토";
            default -> "";
            };
        } catch (Exception e) {
            return "";
        }
    }
}
