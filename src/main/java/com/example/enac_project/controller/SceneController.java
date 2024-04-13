package com.example.enac_project.controller;

import com.example.enac_project.model.*;
import com.example.enac_project.vue.CameraManager;
import com.example.enac_project.vue.ILSIndicator;
import com.example.enac_project.vue.MainView;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SceneController {
    private MainView mainView;
    private Service<Void> aircraftSimulationService;
    private Aircraft aircraft;
    private boolean stopSimulation = false;

    public SceneController(MainView mainView, Aircraft aircraft) {
        this.mainView = mainView;
        this.aircraft = aircraft;

        setupAircraftControl();
        startAircraftSimulation();
        setupControls();
    }

    private void setupControls() {
        // Associer les boutons de la vue avec des méthodes du contrôleur
        mainView.getStartButton().setOnAction(e -> startAircraftSimulation());
        mainView.getStopButton().setOnAction(e -> stopSimulation());
        mainView.getResetButton().setOnAction(e -> resetSimulation());
    }

    private void setupAircraftControl() {
        mainView.getScene().setOnKeyPressed(event -> {
            double yaw = 0;   // Lacet : rotation autour de l'axe Y
            double pitch = 0; // Tangage : rotation autour de l'axe X
            double roll = 0;  // Roulis : rotation autour de l'axe Z

            if (!stopSimulation) {
                switch (event.getCode()) {
                    case LEFT:    // Lacet gauche
                        yaw = -1;
                        break;
                    case RIGHT:   // Lacet droit
                        yaw = 1;
                        break;
                    case UP:      // Tangage haut
                        pitch = 1;
                        break;
                    case DOWN:    // Tangage bas
                        pitch = -1;
                        break;
                    case Q:       // Roulis gauche
                        roll = -1;
                        break;
                    case D:       // Roulis droit
                        roll = 1;
                        break;
                    case ENTER:
                        resetSimulation();
                        break;
                    default:
                        // Gérer les autres touches si nécessaire
                        break;
                }
            }

            // Appliquer les changements à l'orientation de l'avion
            aircraft.setYaw(aircraft.getYaw() + yaw);
            aircraft.setPitch(aircraft.getPitch() + pitch);
            aircraft.setRoll(aircraft.getRoll() + roll);

            mainView.getCameraManager().updateOrientation(aircraft.getYaw(), aircraft.getPitch(), aircraft.getRoll());

            // Mettre à jour l'indicateur ILS après le changement d'orientation
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
        stopSimulation = false;
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

    public void resetSimulation () {
        stopSimulation = false;
        aircraft.reset();
        mainView.resetCamera();
    }

}