/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classroom;


/**
 *
 * @author adsd3
 */
/**
 *
 * @author adsd3
 */
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 * 컨트롤러: 모델에 정의된 테이블 위치마다
 *  - 테이블
 *  - 컴퓨터 베이스
 *  - 모니터(스크린)
 * 를 생성하여 root 그룹에 추가합니다.
 */
public class Classroom3DController {
    private final ClassroomModel model;
    private final Group root;

    // 테이블 크기
    private static final double TABLE_WIDTH  = 600;
    private static final double TABLE_HEIGHT = 10;
    private static final double TABLE_DEPTH  = 50;

    // 컴퓨터 베이스(케이스) 크기
    private static final double BASE_WIDTH  = 20;
    private static final double BASE_HEIGHT = 20;
    private static final double BASE_DEPTH  = 15;

    // 모니터(스크린) 크기
    private static final double SCREEN_WIDTH  = 80;
    private static final double SCREEN_HEIGHT = 50;
    private static final double SCREEN_DEPTH  = 2;

    public Classroom3DController(ClassroomModel model, Group root) {
        this.model = model;
        this.root  = root;
    }

    /**
     * 테이블 위에 4대의 컴퓨터(베이스+스크린)를 올립니다.
     */
    public void buildScene() {
        PhongMaterial matTable  = new PhongMaterial(Color.BEIGE);
        PhongMaterial matBase   = new PhongMaterial(Color.DARKGRAY);
        PhongMaterial matScreen = new PhongMaterial(Color.BLACK);

        for (ClassroomModel.Table t : model.getTables()) {
            // ─── 테이블
            Box table = new Box(TABLE_WIDTH, TABLE_HEIGHT, TABLE_DEPTH);
            table.setMaterial(matTable);
            table.setTranslateX(t.x);
            table.setTranslateY(TABLE_HEIGHT / 2);
            table.setTranslateZ(t.z);
            root.getChildren().add(table);

            // ─── 컴퓨터 4대 (베이스 + 모니터)
            double spacing = TABLE_WIDTH / 4.0;
            for (int i = 0; i < 4; i++) {
                double offsetX = -TABLE_WIDTH/2 + spacing/2 + i * spacing;

                // • 베이스 (케이스)
                Box base = new Box(BASE_WIDTH, BASE_HEIGHT, BASE_DEPTH);
                base.setMaterial(matBase);
                base.setTranslateX(t.x + offsetX);
                base.setTranslateY(TABLE_HEIGHT + BASE_HEIGHT / 2);
                base.setTranslateZ(t.z - TABLE_DEPTH/2 + BASE_DEPTH/2 + 5);
                root.getChildren().add(base);

                // • 스크린 (모니터)
                Box screen = new Box(SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_DEPTH);
                screen.setMaterial(matScreen);
                screen.setTranslateX(t.x + offsetX);
                screen.setTranslateY(TABLE_HEIGHT + BASE_HEIGHT + SCREEN_HEIGHT/2-100);
                screen.setTranslateZ(t.z - TABLE_DEPTH/2 + SCREEN_DEPTH/2);
                // 화면이 살짝 위를 향하도록 X축 기울기만 적용
                screen.getTransforms().setAll(
                    new Rotate(-10, Rotate.X_AXIS)
                );
                root.getChildren().add(screen);
            }
        }
    }
}