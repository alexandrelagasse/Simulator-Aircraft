package com.enac.enac_project;

import com.enac.enac_project.controller.SceneController;
import com.enac.enac_project.model.Aircraft;
import com.enac.enac_project.model.Papi;
import com.enac.enac_project.model.Point3DCustom;
import com.enac.enac_project.model.RunwayModel;
import com.enac.enac_project.vue.MainView;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main class is the entry point of the flight simulation application.
 * It initializes and launches the user interface using JavaFX, checking for 3D feature support.
 */
public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Starts the JavaFX application. This method configures and displays the main application window.
     *
     * @param primaryStage The primary stage provided by JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Check if 3D features are supported on this platform
            boolean is3DSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
            if (!is3DSupported) {
                logger.error("3D is not supported in JavaFX on this platform");
                System.exit(1);
            }

            // Create aircraft model
            Aircraft aircraft = new Aircraft();

            // Initialize main view and scene controller
            MainView mainView = new MainView(aircraft);
            SceneController sceneController = new SceneController(mainView, aircraft);

            // Configure and display the main scene
            primaryStage.setScene(mainView.getScene());
            primaryStage.setTitle("Landing Simulator");
            primaryStage.show();

            // Request focus for user input handling
            mainView.getScene().getRoot().requestFocus();
            
            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
            System.exit(1);
        }
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            logger.error("Application failed to launch", e);
            System.exit(1);
        }
    }
}
