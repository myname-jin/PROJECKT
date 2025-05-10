/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classroom;

/**
 *
 * @author adsd3
 */
import java.util.ArrayList;
import java.util.List;

/**
 * 모델: 4인용 긴 테이블(한 테이블당 4대 모니터)의 중심 위치 리스트
 */
public class ClassroomModel {
    public static class Table {
        public final double x, y, z;
        public Table(double x, double y, double z) {
            this.x = x; this.y = y; this.z = z;
        }
    }

    private final List<Table> tables = new ArrayList<>();

    /**
     * @param rows 테이블 행 개수
     * @param cols 테이블 열 개수
     */
    public ClassroomModel(int rows, int cols) {
        double dx = 650;  // 테이블 간 가로 간격(center-to-center)
        double dz = 200;  // 테이블 간 세로(깊이) 간격

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = c * dx;
                double z = r * dz;
                tables.add(new Table(x, 0, z));
            }
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}
