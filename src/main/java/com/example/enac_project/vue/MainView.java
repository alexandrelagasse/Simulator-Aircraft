package com.example.enac_project.vue;

import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.Point3DCustom;

import com.example.enac_project.model.RunwayModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


/**
 * La classe MainView organise et affiche tous les composants graphiques nécessaires pour la simulation de vol.
 * Elle intègre des éléments comme la vue de la piste, les commandes de simulation et divers indicateurs (ILS, PAPI, altitude, DME).
 */
public class MainView {
    private Scene scene;
    private final Group root = new Group();
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private CameraManager cameraManager;
    private RunwayView runwayView;
    private ILSIndicator indicator;
    private PapiStatusLED papiStatusLED;
    private Button startButton = new Button("Start");
    private Button stopButton = new Button("Stop");
    private Button resetButton = new Button("Reset");

    private DMEIndicator DMEApproche;
    private AltitudeIndicator altitude;
    private HBox controls;
    private PAPIVue PAPI;
    private MarkersIndicator markers;

    /**
     * Constructeur qui initialise la vue principale avec l'avion et le modèle de piste spécifiés.
     *
     * @param aircraft L'avion utilisé dans la simulation.
     * @param runwayPoint Le modèle de la piste associée à la simulation.
     */
    public MainView(Aircraft aircraft, RunwayModel runwayPoint) {
        cameraManager = new CameraManager(aircraft.getX(), aircraft.getY(), aircraft.getZ());
        runwayView = new RunwayView(runwayPoint);
        indicator = new ILSIndicator(new Point3DCustom(200,200, 0));
        DMEApproche = new DMEIndicator();
        altitude = new AltitudeIndicator();
        PAPI = new PAPIVue(runwayPoint);
        papiStatusLED = new PapiStatusLED();
        markers = new MarkersIndicator();

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


    /**
     * Crée la vue de la caméra, qui inclut la piste et les indicateurs PAPI.
     * @return Un SubScene contenant la vue de la caméra.
     */
    private SubScene createCameraView() {
        Group cameraViewRoot = new Group();
        cameraViewRoot.getChildren().add(runwayView);

        Group papiView = PAPI.getSpheres();
        double papiX = runwayView.getBoundsInParent().getMaxX() - 200;
        //double papiZ = runwayView.getBoundsInParent().getMinZ() ;
        papiView.getTransforms().add(new Translate(papiX, 0, -1150));
        cameraViewRoot.getChildren().add(papiView);

        SubScene cameraView = new SubScene(cameraViewRoot, 800, 600, true, SceneAntialiasing.BALANCED);
        cameraView.setFill(Color.LIGHTBLUE);
        cameraView.setCamera(cameraManager.getCamera());
        return cameraView;
    }

    /**
     * Crée le panneau de contrôle contenant les boutons de démarrage, d'arrêt et de réinitialisation de la simulation.
     * @return Un SubScene contenant les contrôles.
     */
    private SubScene createControlPanel() {
        controls = new HBox(5);
        controls.getChildren().addAll(startButton, stopButton, resetButton);
        controls.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_LEFT);
        return new SubScene(controls, 800, 50);
    }


    /**
     * Crée le panneau des indicateurs qui inclut l'ILS, l'altitude, le DME et les indicateurs PAPI.
     * @return Un SubScene contenant les indicateurs.
     */
    private SubScene createIndicatorPanel() {
        HBox indicatorPanel = new HBox(8); // L'espacement entre les éléments est de 10
        indicatorPanel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(5), Insets.EMPTY)));
        indicatorPanel.setPadding(new Insets(10));
        indicatorPanel.getChildren().add(indicator);

        indicatorPanel.getChildren().add(DMEApproche.getView());
        indicatorPanel.getChildren().add(altitude.getView());
        indicatorPanel.getChildren().add(papiStatusLED);
        indicatorPanel.getChildren().add(markers.getIM());
        indicatorPanel.getChildren().add(markers.getMM());
        indicatorPanel.getChildren().add(markers.getOM());

        return new SubScene(indicatorPanel, 800, 125);
    }

    /**
     * Initialise et organise les composants de la scène principale.
     */
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

    public PAPIVue getPAPI(){return PAPI;}

    public PapiStatusLED getPAPILED(){return papiStatusLED;}

    public CameraManager getCameraManager() {return cameraManager;}

    public void resetCamera() {
        cameraManager.resetCamera();
        markers.resetMM();
        markers.resetIM();
        markers.resetOM();

    }

    public void setAltitude(double alti) {
        altitude.updateAltitude(alti);
    }

    public void setDMEApproche(double dme) {
        DMEApproche.updateDistance(dme);
    }

    public void setMarkersMM(boolean mm) {
        if (mm) {
            markers.setMM();
        }
    }

    public void setMarkersIM(boolean im) {
        if (im) {
            markers.setIM();
        }
    }

    public void setMarkersOM(boolean om) {
        if (om) {
            markers.setOM();
        }
    }

}