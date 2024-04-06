package com.example.enac_project.controller;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.CameraModel;
import com.example.enac_project.vue.MainView;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

public class SceneController {
    private MainView mainView;
    private Aircraft aircraft;
    private CameraModel cameraModel;
    private Service<Void> aircraftSimulationService;
    private boolean stopSimulation = false;

    public SceneController(MainView mainView, Aircraft aircraft, CameraModel cameraModel) {
        this.mainView = mainView;
        this.aircraft = aircraft;
        this.cameraModel = cameraModel;
        setupCameraControl();

        mainView.bindAircraft(aircraft);
        mainView.bindCamera(cameraModel);
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
            cameraModel.setX(cameraModel.getX() + deltaX);
            cameraModel.setY(cameraModel.getY() + deltaY);
            cameraModel.setZ(cameraModel.getZ() + deltaZ);
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
                            double newZ = aircraft.getZ() + aircraft.getSpeed();
                            Platform.runLater(() -> {
                                aircraft.setZ(newZ);
                                cameraModel.setZ(newZ);
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

    public void stopSimulation() {
        stopSimulation = true;
        if (aircraftSimulationService != null) {
            aircraftSimulationService.cancel();
        }
    }

}