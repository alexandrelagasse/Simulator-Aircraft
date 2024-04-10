package com.example.enac_project.vue;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.RunwayView;
import javafx.beans.binding.Bindings;
import javafx.scene.*;
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
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private RunwayView runwayView;

    private Label airplanePositionLabel = new Label();
    private Label runwayPositionLabel = new Label();

    public MainView(Aircraft aircraft) {
        runwayView = new RunwayView();

        bindAircraft(aircraft);

        initialize(new Translate(aircraft.getX(), aircraft.getY(), aircraft.getZ()));
    }

    private void initialize(Translate positionCamera) {

        // Configuration de la caméra
        camera.getTransforms().addAll(positionCamera);
        camera.setNearClip(1);
        camera.setFarClip(2000);
        camera.setFieldOfView(60);

        VBox labelsBox = new VBox(airplanePositionLabel, runwayPositionLabel);
        labelsBox.setTranslateX(20);
        labelsBox.setTranslateY(20);

        root.getChildren().addAll(runwayView);
        SubScene subScene = new SubScene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTBLUE);
        subScene.setCamera(camera);
        ILSIndicator indicator = new ILSIndicator();


// Positionner et dimensionner le fond de l'indicateur pour améliorer la visibilité
        Rectangle indicatorBackground = new Rectangle(200, 200, Color.DARKGRAY); // Ajuster les dimensions si nécessaire
        indicator.setTranslateX(300 - indicatorBackground.getWidth() / 2); // Ajustez en fonction de la position souhaitée
        indicator.setTranslateY(150 - indicatorBackground.getHeight() / 2);
        indicatorBackground.setArcWidth(30); // Pour les coins arrondis
        indicatorBackground.setArcHeight(30);
        indicator.setTranslateX(300); // Centre de la scène par exemple
        indicator.setTranslateY(150);

// Positionner l'indicateur au-dessus du fond
        indicator.setTranslateX(indicatorBackground.getX() + indicatorBackground.getWidth() / 2);
        indicator.setTranslateY(indicatorBackground.getY() + indicatorBackground.getHeight() / 2);


        Group mainRoot = new Group(subScene, labelsBox, indicator);
        this.scene = new Scene(mainRoot, 800, 600);
    }

    public void bindAircraft(Aircraft aircraft) {
        camera.translateXProperty().bind(aircraft.xProperty());
        camera.translateYProperty().bind(aircraft.yProperty());
        camera.translateZProperty().bind(aircraft.zProperty());

        airplanePositionLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Avion: X=%.2f Y=%.2f Z=%.2f", aircraft.getX(), aircraft.getY(), aircraft.getZ()),
                aircraft.xProperty(), aircraft.yProperty(), aircraft.zProperty()));
    }

    public Scene getScene() {
        return this.scene;
    }
}