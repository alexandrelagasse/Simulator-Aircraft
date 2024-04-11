package com.example.enac_project.controller;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.ILS;
import com.example.enac_project.model.Point3DCustom;
import com.example.enac_project.model.RunwayView;
import com.example.enac_project.vue.ILSIndicator;
import com.example.enac_project.vue.MainView;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;

public class SceneController {
    private MainView mainView;
    private Aircraft aircraft;
    private Service<Void> aircraftSimulationService;
    private boolean stopSimulation = false;

    public SceneController(MainView mainView, Aircraft aircraft) {
        this.mainView = mainView;
        this.aircraft = aircraft;

        setupCameraControl();
        startAircraftSimulation();
    }

    private void setupCameraControl() {
        mainView.getScene().setOnKeyPressed(event -> {
            double deltaX = 0, deltaY = 0, deltaZ = 0;

            if (event.getCode() == KeyCode.UP) {
                deltaY = 10;
            } else if (event.getCode() == KeyCode.DOWN) {
                deltaY = -10;
            } else if (event.getCode() == KeyCode.RIGHT) {
                deltaX = 10;
            } else if (event.getCode() == KeyCode.LEFT) {
                deltaX = -10;
            } else if (event.getCode() == KeyCode.Z) {
                deltaZ = 10;
            } else if (event.getCode() == KeyCode.S) {
                deltaZ = -10;
            }

            aircraft.setX(aircraft.getX() + deltaX);
            aircraft.setY(aircraft.getY() + deltaY);
            aircraft.setZ(aircraft.getZ() + deltaZ);
            updateILSIndicator();
        });
    }

    private void startAircraftSimulation() {
        aircraftSimulationService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        while (!stopSimulation) {
                            Platform.runLater(() -> {
                                aircraft.updatePosition();
                                updateILSIndicator();
                            });
                            Thread.sleep(100);
                        }
                        return null;
                    }
                };
            }
        };
        aircraftSimulationService.start();
    }

    public void updateILSIndicator() {

        ILS ils = aircraft.getILS();
        Point3DCustom posAircraft = new Point3DCustom(aircraft.getX(), aircraft.getY(), aircraft.getZ());
        double deviationLocalizer = ils.calculateLocalizerBar(posAircraft);
        double deviationGlidePath = ils.calculateGlidePathBar(posAircraft);

        ILSIndicator indicator = mainView.getIndicator();
        indicator.adjustGlidePathBars(deviationGlidePath);
        indicator.moveLocalizerBar(deviationLocalizer);

    }


    public void stopSimulation() {
        stopSimulation = true;
        if (aircraftSimulationService != null) {
            aircraftSimulationService.cancel();
        }
    }

}