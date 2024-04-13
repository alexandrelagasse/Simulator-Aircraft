package com.example.enac_project.vue;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.Point3DCustom;
import com.example.enac_project.model.RunwayView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;



public class MainView {
    private Scene scene;
    private final Group root = new Group();
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private CameraManager cameraManager;
    private RunwayView runwayView;
    private ILSIndicator indicator;
    private Button startButton = new Button("Start");
    private Button stopButton = new Button("Stop");
    private Button resetButton = new Button("Reset");

    private Label airplanePositionLabel = new Label();
    private Label runwayPositionLabel = new Label();
    private VBox controls;

    public MainView(Aircraft aircraft, Point3DCustom runwayPoint) {
        cameraManager = new CameraManager();
        runwayView = new RunwayView(runwayPoint);
        indicator = new ILSIndicator(new Point3DCustom(200,200, 0));

        bindAircraft(aircraft);

        setupControls();
        initialize(new Translate(aircraft.getX(), aircraft.getY(), aircraft.getZ()));
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    private void setupControls() {
        controls = new VBox(5, startButton, stopButton, resetButton);
        controls.setTranslateX(20);
        controls.setTranslateY(20);
    }

    private void initialize(Translate positionCamera) {

        // Configuration de la caméra

        VBox labelsBox = new VBox(airplanePositionLabel, runwayPositionLabel);
        labelsBox.setTranslateX(20);
        labelsBox.setTranslateY(20);

        root.getChildren().addAll(runwayView);
        SubScene subScene = new SubScene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTBLUE);
        subScene.setCamera(cameraManager.getCamera());

        BorderPane layout = new BorderPane();
        layout.setTop(controls);  // Place les contrôles dans la partie supérieure
        layout.setCenter(subScene);  // La subscene au centre
        layout.setBottom(labelsBox);  // Les labels en bas

        // Marges pour les contrôles
        BorderPane.setMargin(controls, new Insets(10, 10, 10, 10));

        Group mainRoot = new Group(subScene, labelsBox, indicator, layout);
        this.scene = new Scene(mainRoot, 800, 600);
    }

    public void bindAircraft(Aircraft aircraft) {
        cameraManager.bindToAircraft(aircraft);

        airplanePositionLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Avion: X=%.2f Y=%.2f Z=%.2f", aircraft.getX(), aircraft.getY(), aircraft.getZ()),
                aircraft.xProperty(), aircraft.yProperty(), aircraft.zProperty()));
    }

    public Scene getScene() {
        return this.scene;
    }

    public ILSIndicator getIndicator() {return indicator;}

    public RunwayView getRunwayView() {return runwayView;}

    public CameraManager getCameraManager() {return cameraManager;}

    public void resetCamera() {
        cameraManager.resetCamera();
    }
}