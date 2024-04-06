package com.example.enac_project;

import com.example.enac_project.controller.SceneController;
import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.CameraModel;
import com.example.enac_project.model.Runway;
import com.example.enac_project.vue.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Aircraft aircraft = new Aircraft();
        Runway runway = new Runway();
        CameraModel cameraModel = new CameraModel();
        MainView mainView = new MainView(aircraft, runway, cameraModel);
        SceneController sceneController = new SceneController(mainView, aircraft, runway, cameraModel);

        primaryStage.setScene(mainView.getScene());
        primaryStage.setTitle("Simulateur d'Atterrissage");
        primaryStage.show();

        mainView.getScene().getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}