package com.example.enac_project;

import com.example.enac_project.controller.SceneController;
import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.Papi;
import com.example.enac_project.model.Point3DCustom;
import com.example.enac_project.vue.MainView;
import com.example.enac_project.vue.PAPIVue;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        boolean is3DSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        if(!is3DSupported) {
            System.out.println("Sorry, 3D is not supported in JavaFX on this platform.");
            return;
        }

        Point3DCustom runwayPoint = new Point3DCustom(0, 0, -1250);


        Point3DCustom papiPosition = new Point3DCustom(runwayPoint.getX() + 20, runwayPoint.getY(), runwayPoint.getZ());
        Papi papi = new Papi(papiPosition);
        Aircraft aircraft = new Aircraft(runwayPoint, papi);
        MainView mainView = new MainView(aircraft, runwayPoint);
        SceneController sceneController = new SceneController(mainView, aircraft, papi);

        primaryStage.setScene(mainView.getScene());
        primaryStage.setTitle("Simulateur d'Atterrissage");
        primaryStage.show();

        mainView.getScene().getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}