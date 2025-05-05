/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visualization;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// 막대그래프 클릭 이벤트를 처리하여 해당 강의실의 상세 요일별 통계를 보여주는 컨트롤러.

public class ReservationController extends MouseAdapter {
    private final ReservationModel model;
    private final Map<Integer, String> roomIndexMap = new HashMap<>();
    private final Map<Integer, Integer> xPositionMap = new HashMap<>();

    public ReservationController(ReservationModel model) {
        this.model = model;
    }

    public void setClickArea(int index, int x, String room) {
        xPositionMap.put(index, x);
        roomIndexMap.put(index, room);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();

        for (int i : xPositionMap.keySet()) {
            int x = xPositionMap.get(i);
            if (mx >= x && mx <= x + 50) {
                String room = roomIndexMap.get(i);
                new DetailView(room, model.getRoomByDay(room));
                break;
            }
        }
    }
}
