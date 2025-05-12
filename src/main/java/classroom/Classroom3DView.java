///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package classroom;
//
///**
// *
// * @author adsd3
// */
//import javafx.application.Application;
//import javafx.scene.AmbientLight;
//import javafx.scene.Group;
//import javafx.scene.PointLight;
//import javafx.scene.PerspectiveCamera;
//import javafx.scene.Scene;
//import javafx.scene.SceneAntialiasing;
//import javafx.scene.paint.Color;
//import javafx.scene.transform.Rotate;
//import javafx.stage.Stage;
//
//public class Classroom3DView extends Application {
//    @Override
//    public void start(Stage stage) {
//        Group root = new Group();
//
//        // 모델(4행×5열) + 컨트롤러
//        ClassroomModel model = new ClassroomModel(4, 5);
//        new Classroom3DController(model, root).buildScene();
//
//        // ─── 조명 세팅 ──────────────────────────────────────
//        AmbientLight ambient = new AmbientLight(Color.color(0.6, 0.6, 0.6));
//        root.getChildren().add(ambient);
//
//        PointLight point = new PointLight(Color.WHITE);
//        point.setTranslateX(0);
//        point.setTranslateY(-1000);
//        point.setTranslateZ(-1000);
//        root.getChildren().add(point);
//
//        // ─── 카메라 세팅 ──────────────────────────────────────
//        int rows = 4, cols = 5;
//        double hGap = 650, vGap = 200;
//        double totalWidth = (cols - 1) * hGap;   // = 2600
//        double totalDepth = (rows - 1) * vGap;   // =  600
//
//        PerspectiveCamera camera = new PerspectiveCamera(true);
//        // X 중앙, Y 위로, Z 뒤로 물러나게
//        camera.setTranslateX(totalWidth / 2.0 +300);
//        camera.setTranslateY(-700);
//        camera.setTranslateZ(-(totalDepth + 350));
//
//        camera.getTransforms().addAll(
//            new Rotate(-30, Rotate.X_AXIS),  // 내려다보기
//            new Rotate(0, Rotate.Y_AXIS)   // 측면 살짝 보기
//        );
//
//        camera.setNearClip(0.1);
//        camera.setFarClip(5000);
//
//        // ─── Scene & Stage ────────────────────────────────────
//        Scene scene = new Scene(root, 800, 600, true, SceneAntialiasing.BALANCED);
//        scene.setFill(Color.LIGHTGRAY);
//        scene.setCamera(camera);
//
//        stage.setTitle("3D Classroom (4인용 테이블 배열)");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}