/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visualization;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainView extends JFrame {
    private final ReservationModel model;
    private final ReservationController controller;

    public MainView(ReservationModel model, ReservationController controller) {
        this.model = model;
        this.controller = controller;
        setTitle("강의실 예약 통계");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addMouseListener(controller);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Map<String, Integer> data = model.getRoomTotals();
        int startX = 80, baseY = 400, barWidth = 50, spacing = 40, maxHeight = 200;
        int max = data.values().stream().max(Integer::compareTo).orElse(1);

        int i = 0;
        for (String room : data.keySet()) {
            int count = data.get(room);
            int height = (int) (count / (double) max * maxHeight);
            int x = startX + i * (barWidth + spacing);
            int y = baseY - height;

            g.setColor(new Color(100, 150, 255));
            g.fillRect(x, y, barWidth, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, height);
            g.drawString(room, x + 10, baseY + 15);
            g.drawString(String.valueOf(count), x + 15, y - 5);

            controller.setClickArea(i, x, room);
            i++;
        }
        g.drawString("강의실 예약 총합", 320, 80);
    }
}
