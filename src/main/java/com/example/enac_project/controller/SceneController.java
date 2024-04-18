package com.example.enac_project.controller;

import com.example.enac_project.model.*;
import com.example.enac_project.vue.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.PerspectiveCamera;

/**
 * Cette classe gère les interactions et le contrôle de la simulation d'un avion dans une scène graphique.
 * Elle initialise et met à jour les différentes composantes visuelles et comportementales de la simulation.
 */
public class SceneController {
    private MainView mainView;
    private Service<Void> aircraftSimulationService;
    private Aircraft aircraft;
    private boolean stopSimulation = false;
    private Papi papi;

    /**
     * Constructeur de la classe, initialise les vues et les modèles d'avion et de PAPI,
     * et configure les contrôles et la simulation.
     *
     * @param mainView La vue principale de l'application.
     * @param aircraft Le modèle de l'avion à simuler.
     */
    public SceneController(MainView mainView, Aircraft aircraft) {
        this.mainView = mainView;
        this.aircraft = aircraft;
        this.papi = aircraft.getPapi();

        setupAircraftControl();
        startAircraftSimulation();
        setupControls();
        mainView.getCameraManager().attachToAircraft(aircraft);
    }

    /**
     * Configure les boutons de la vue pour contrôler la simulation.
     */
    private void setupControls() {
        mainView.getStartButton().setOnAction(e -> {
            startAircraftSimulation();
            mainView.getRoot().requestFocus(); // Demande le focus après l'action
        });
        mainView.getStopButton().setOnAction(e -> {
            stopSimulation();
            mainView.getRoot().requestFocus(); // Demande le focus après l'action
        });
        mainView.getResetButton().setOnAction(e -> {
            resetSimulation();
            mainView.getRoot().requestFocus(); // Demande le focus après l'action
        });
    }


    /**
     * Configure les commandes de l'avion liées aux touches du clavier pour piloter l'avion pendant la simulation.
     */
    private void setupAircraftControl() {
        mainView.getScene().setOnKeyPressed(event -> {
            double yaw = 0;   // Rotation autour de l'axe Y
            double pitch = 0; // Rotation autour de l'axe X
            double roll = 0;  // Rotation autour de l'axe Z

            if (!stopSimulation) {
                switch (event.getCode()) {
                    case LEFT:    yaw = -1; break;
                    case RIGHT:   yaw = 1; break;
                    case UP:      pitch = 1; break;
                    case DOWN:    pitch = -1; break;
                    case Q:       roll = -1; break;
                    case D:       roll = 1; break;
                    case ENTER:   resetSimulation(); break;
                    default:      // Autres touches non gérées
                        break;
                }
            }

            aircraft.setYaw(aircraft.getYaw() + yaw);
            aircraft.setPitch(aircraft.getPitch() + pitch);
            aircraft.setRoll(aircraft.getRoll() + roll);
            mainView.getCameraManager().updateOrientation(aircraft.getYaw(), aircraft.getPitch(), aircraft.getRoll());
            updateILSIndicator();
        });
    }

    /**
     * Démarre la simulation de l'avion en arrière-plan.
     */
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
                                updatePAPIIndicator();
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

    /**
     * Met à jour les indicateurs ILS de la simulation.
     */
    public void updateILSIndicator() {
        ILS ils = aircraft.getILS();
        Point3DCustom posAircraft = new Point3DCustom(aircraft.getX(), aircraft.getY(), aircraft.getZ());
        double deviationLocalizer = ils.calculateLocalizerBar(posAircraft);
        double deviationGlidePath = ils.calculateGlidePathBar(posAircraft);
        double DME = ils.calculateDME(posAircraft);
        double altitude = aircraft.calculateAltitudeDifference();
        boolean om = ils.franchissementMarkersOM(posAircraft);
        boolean im = ils.franchissementMarkersIM(posAircraft);
        boolean mm = ils.franchissementMarkersMM(posAircraft);

        ILSIndicator indicator = mainView.getIndicator();
        indicator.adjustGlidePathBars(deviationGlidePath);
        indicator.moveLocalizerBar(deviationLocalizer);
        mainView.setAltitude(altitude);
        mainView.setDMEApproche(DME);
        mainView.setMarkersIM(im);
        mainView.setMarkersMM(mm);
        mainView.setMarkersOM(om);
    }

    /**
     * Met à jour les indicateurs PAPI de la simulation.
     */
    public void updatePAPIIndicator() {
        PAPIVue papiVue = mainView.getPAPI();
        PerspectiveCamera camera = mainView.getCameraManager().getCamera();

        papi.updatePapiState(aircraft);
        papiVue.setIndicatorState(papi.getPapiLevel());
        PapiStatusLED papiLED = mainView.getPAPILED();

        papiLED.updateStatus(papi.getPapiLevel());
    }

    /**
     * Arrête la simulation de l'avion.
     */
    public void stopSimulation() {
        stopSimulation = true;
        if (aircraftSimulationService != null) {
            aircraftSimulationService.cancel();
        }
    }

    /**
     * Réinitialise la simulation à son état initial.
     */
    public void resetSimulation() {
        stopSimulation = false;
        aircraft.reset();
        mainView.resetCamera();
    }
}
