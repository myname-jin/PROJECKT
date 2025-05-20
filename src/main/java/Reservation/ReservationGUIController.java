package Reservation;

import ServerClient.LogoutUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.event.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReservationGUIController {
    private ReservationView view;
    private static final String EXCEL_PATH = "src/main/resources/available_rooms.xlsx";
    private static final List<String> LAB_ROOMS = Arrays.asList("911", "915", "916", "918");
    private List<RoomModel> allRooms = new ArrayList<>();
    public Workbook workbook;
    
    private String userName = "김민준";
    private String userId = "20211111";
    private String userDept = "컴퓨터소프트웨어공학";
    private String userType = "학생"; // "학생" 또는 "교수"
  //private final ReservationView view;
  //private final Socket socket;
  //private final BufferedWriter out;
  //private final String userId;
    private Socket socket;
    private BufferedWriter out;

    
//클라이언트-서버 연결 코드(로그인과 사용자 페이지 연결되면 주석 해제)
    public ReservationGUIController(String userId, Socket socket, BufferedWriter out) {
    this.userId = userId;
    this.socket = socket;
    this.out = out;

    this.userName = "김민준";  // 이후 실제 로그인 정보로 대체
    this.userDept = "컴퓨터소프트웨어공학";
    this.userType = "학생"; // 또는 "교수"

    view = new ReservationView(); // 이 클래스가 JFrame이라면 가능
    view.setUserInfo(userName, userId, userDept);

    LogoutUtil.attach(view, userId, socket, out);  
    }

    
    public ReservationGUIController() {
        view = new ReservationView();
        view.setUserInfo(userName, userId, userDept);
        
        if (userType.equals("교수")) {
            view.enableProfessorMode(); // View 내부에서 교수 전용 UI 구역 활성화
        }
        
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
    ActionListener timeUpdateListener = e -> {
        updateAvailableTimes();  // 예약 가능한 시간대 갱신
        String selectedRoom = view.getSelectedRoom();
        if (selectedRoom != null && !selectedRoom.isEmpty()) {
            String roomInfo = getRoomInfo(selectedRoom);  // 강의실 정보 가져오기
            view.setRoomInfoText(roomInfo);               // View에 표시
        }
    };
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
            
            saveReservation(userName, userType, userId, userDept,
                    selectedRoom.getType(), selectedRoom.getName(),
                    date, dayOfWeek, startTime, endTime, purpose, "예약대기");
                }
            }
        });

        view.setVisible(true);
    }
    
    private String getRoomInfo(String roomName) {
    String filePath = "src/main/resources/classroom.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", -1);
            if (parts.length == 4 && parts[0].equals(roomName)) {
                return String.format("%s, %s, %s", parts[1], parts[2], parts[3]); // 위치, 인원, 비고
            }
        }
    } catch (IOException e) {
        System.out.println("강의실 정보 읽기 실패: " + e.getMessage());
    }
    return "정보 없음";
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
    
    public int calculateTotalDuration(List<String> times) {
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

    public void loadRoomsFromExcel() {
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

    public int getDayColumnIndex(String selectedDate) {
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

    public List<String> getAvailableTimesByDay(Sheet sheet, int dayCol) {
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
    
    public String getDayOfWeek(String dateStr) {
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
