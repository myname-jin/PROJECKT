/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ReservationGraph extends JFrame {

    private final Map<String, Integer> roomTotals = new TreeMap<>(); // 강의실별 총합
    private final Map<String, Map<String, Integer>> roomByDay = new HashMap<>(); // 강의실 → (요일 → 횟수)
    private final String[] rooms = {"911", "912", "913", "914", "915", "916", "917", "918"};
    private final String[] days = {"월", "화", "수", "목", "금"};
    private final String filePath = "C:\\SWG\\JAVAPROJECKT\\data.txt";
    private final int[] barXPositions = new int[rooms.length]; // 클릭 영역 저장

    public ReservationGraph() {
        readFile();
        setTitle("강의실 예약 통계");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addMouseListener(new ClickHandler());
        setVisible(true);
    }

    private void readFile() {
        // 초기화
        for (String room : rooms) {
            roomTotals.put(room, 0);
            roomByDay.put(room, new HashMap<>());
            for (String day : days) {
                roomByDay.get(room).put(day, 0);
            }
        }

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("월,911\n월,912\n화,911\n수,911\n수,913\n목,915\n금,911\n금,912\n화,914\n화,911\n목,911");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "파일 생성 실패");
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String day = parts[0].trim();
                    String room = parts[1].trim();
                    if (roomTotals.containsKey(room) && roomByDay.get(room).containsKey(day)) {
                        roomTotals.put(room, roomTotals.get(room) + 1);
                        Map<String, Integer> map = roomByDay.get(room);
                        map.put(day, map.get(day) + 1);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 읽기 실패");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int startX = 80;
        int baseY = 400;
        int barWidth = 50;
        int spacing = 40;
        int maxHeight = 200;

        g.drawString("강의실 예약 총합", 330, 80);

        int maxValue = roomTotals.values().stream().max(Integer::compareTo).orElse(1);

        for (int i = 0; i < rooms.length; i++) {
            String room = rooms[i];
            int count = roomTotals.get(room);
            int height = (int) ((count / (double) maxValue) * maxHeight);
            int x = startX + i * (barWidth + spacing);
            int y = baseY - height;

            g.setColor(new Color(100, 150, 255));
            g.fillRect(x, y, barWidth, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, height);
            g.drawString(room, x + 10, baseY + 15);
            g.drawString(String.valueOf(count), x + 15, y - 5);

            barXPositions[i] = x; // 클릭 영역 저장
        }
    }

    private class ClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mx = e.getX();
            int my = e.getY();

            for (int i = 0; i < barXPositions.length; i++) {
                int x = barXPositions[i];
                if (mx >= x && mx <= x + 50) {
                    String room = rooms[i];
                    new DayDetailFrame(room, roomByDay.get(room));
                    break;
                }
            }
        }
    }

    // 새 창에서 요일별 예약 그래프
    static class DayDetailFrame extends JFrame {
        private final String room;
        private final Map<String, Integer> data;

        public DayDetailFrame(String room, Map<String, Integer> data) {
            this.room = room;
            this.data = data;
            setTitle("강의실 " + room + " 요일별 예약 통계");
            setSize(500, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int startX = 80;
            int baseY = 300;
            int barWidth = 50;
            int spacing = 40;
            int maxHeight = 150;

            g.drawString("강의실 " + room + " 요일별 예약 수", 150, 80);

            int maxValue = data.values().stream().max(Integer::compareTo).orElse(1);

            int i = 0;
            for (String day : Arrays.asList("월", "화", "수", "목", "금")) {
                int count = data.get(day);
                int height = (int) ((count / (double) maxValue) * maxHeight);
                int x = startX + i * (barWidth + spacing);
                int y = baseY - height;

                g.setColor(new Color(255, 180, 100));
                g.fillRect(x, y, barWidth, height);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, height);
                g.drawString(day, x + 15, baseY + 15);
                g.drawString(String.valueOf(count), x + 15, y - 5);
                i++;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReservationGraph::new);
    }
}

