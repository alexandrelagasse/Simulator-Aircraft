package com.example.enac_project.vue;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.Point3DCustom;
import com.example.enac_project.model.RunwayView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.control.Label;


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

    private DMEIndicator DMEApproche;
    private AltitudeIndicator altitude;
    private HBox controls;

    public MainView(Aircraft aircraft, Point3DCustom runwayPoint) {
        cameraManager = new CameraManager(aircraft.getX(), aircraft.getY(), aircraft.getZ());
        runwayView = new RunwayView(runwayPoint);
        indicator = new ILSIndicator(new Point3DCustom(200,200, 0));
        DMEApproche = new DMEIndicator();
        altitude = new AltitudeIndicator();

        initialize();
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


    private SubScene createCameraView() {
        Group cameraViewRoot = new Group();
        cameraViewRoot.getChildren().add(runwayView);
        SubScene cameraView = new SubScene(cameraViewRoot, 800, 600, true, SceneAntialiasing.BALANCED);
        cameraView.setFill(Color.LIGHTBLUE);
        cameraView.setCamera(cameraManager.getCamera());
        return cameraView;
    }

    private SubScene createControlPanel() {
        controls = new HBox(5);
        controls.getChildren().addAll(startButton, stopButton, resetButton);
        controls.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_LEFT);
        return new SubScene(controls, 800, 50);
    }

    private SubScene createIndicatorPanel() {
        // Création d'un VBox pour contenir les indicateurs
        VBox indicatorPanel = new VBox(10); // L'espacement entre les éléments est de 10
        indicatorPanel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(5), Insets.EMPTY)));
        indicatorPanel.setPadding(new Insets(10));
        indicatorPanel.getChildren().add(indicator);

        indicatorPanel.getChildren().add(DMEApproche.getView());
        indicatorPanel.getChildren().add(altitude.getView());

        return new SubScene(indicatorPanel, 800, 200);
    }


    private void initialize() {
        SubScene controlPanelScene = createControlPanel();
        SubScene cameraViewScene = createCameraView();
        SubScene indicatorPanelScene = createIndicatorPanel();

        controlPanelScene.setLayoutY(0);
        cameraViewScene.setLayoutY(controlPanelScene.getHeight());
        indicatorPanelScene.setLayoutY(600 - indicatorPanelScene.getHeight() + 10);

        root.getChildren().add(controlPanelScene);
        root.getChildren().add(cameraViewScene);
        root.getChildren().add(indicatorPanelScene);

        this.scene = new Scene(root, 800, 600, Color.LIGHTBLUE);
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

    public void setAltitude(double alti) {
        altitude.updateAltitude(alti);
    }

    public void setDMEApproche(double dme) {
        DMEApproche.updateDistance(dme);
    }
}