package com.example.enac_project.vue;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.CameraModel;
import com.example.enac_project.model.Runway;
import javafx.beans.binding.Bindings;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;



public class MainView {
    private Scene scene;
    private final Group root = new Group();
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private Box airplane = new Box(30, 10, 10);
    private Box runway = new Box(300, 5, 1000);

    private Label airplanePositionLabel = new Label();
    private Label runwayPositionLabel = new Label();

    public MainView(Aircraft aircraft, Runway runwayModel, CameraModel cameraModel) {
        bindAircraft(aircraft);
        bindCamera(cameraModel);
        bindRunway(runwayModel);
        initialize();
    }

    private void initialize() {
        airplane.setMaterial(new PhongMaterial(Color.YELLOW));
        runway.setMaterial(new PhongMaterial(Color.BLACK));

        // Configuration de la caméra
        camera.getTransforms().addAll(new Translate(0, 0, -1000));
        camera.setNearClip(1);
        camera.setFarClip(2000);
        camera.setFieldOfView(60);

        VBox labelsBox = new VBox(airplanePositionLabel, runwayPositionLabel);
        labelsBox.setTranslateX(20);
        labelsBox.setTranslateY(20);

        root.getChildren().addAll(airplane, runway);
        SubScene subScene = new SubScene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTBLUE);
        subScene.setCamera(camera);

        Group mainRoot = new Group(subScene, labelsBox);
        this.scene = new Scene(mainRoot, 800, 600);
    }

    public void bindAircraft(Aircraft aircraft) {
        airplane.translateXProperty().bind(aircraft.xProperty());
        airplane.translateYProperty().bind(aircraft.yProperty());
        airplane.translateZProperty().bind(aircraft.zProperty());

        airplanePositionLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Avion: X=%.2f Y=%.2f Z=%.2f", aircraft.getX(), aircraft.getY(), aircraft.getZ()),
                aircraft.xProperty(), aircraft.yProperty(), aircraft.zProperty()));
    }

    public void bindCamera(CameraModel cameraModel) {
        camera.translateXProperty().bind(cameraModel.xProperty());
        camera.translateYProperty().bind(cameraModel.yProperty());
        camera.translateZProperty().bind(cameraModel.zProperty());
    }

    public void bindRunway(Runway runwayModel) {
        runway.translateXProperty().bind(runwayModel.xProperty());
        runway.translateYProperty().bind(runwayModel.yProperty());
        runway.translateZProperty().bind(runwayModel.zProperty());

        runwayPositionLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Piste: X=%.2f Y=%.2f Z=%.2f", runwayModel.getX(), runwayModel.getY(), runwayModel.getZ()),
                runwayModel.xProperty(), runwayModel.yProperty(), runwayModel.zProperty()));
    }


    public Scene getScene() {
        return this.scene;
    }
}