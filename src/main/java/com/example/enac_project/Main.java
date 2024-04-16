package com.example.enac_project;

import com.example.enac_project.controller.SceneController;
import com.example.enac_project.model.Aircraft;
import com.example.enac_project.model.Papi;
import com.example.enac_project.model.Point3DCustom;
import com.example.enac_project.model.RunwayModel;
import com.example.enac_project.vue.MainView;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * La classe Main est le point d'entrée de l'application de simulation de vol.
 * Elle initialise et lance l'interface utilisateur en utilisant JavaFX, en vérifiant la prise en charge des fonctionnalités 3D.
 */
public class Main extends Application {

    /**
     * Démarrage de l'application JavaFX. Cette méthode configure et affiche la fenêtre principale de l'application.
     *
     * @param primaryStage Le stage principal fourni par JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        // Vérifie si les fonctionnalités 3D sont supportées sur cette plateforme.
        boolean is3DSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        if (!is3DSupported) {
            System.out.println("Sorry, 3D is not supported in JavaFX on this platform.");
            return;
        }

        // Création du modèle de piste et positionnement des composants associés.
        Aircraft aircraft = new Aircraft();

        // Initialisation de la vue principale et du contrôleur de scène.
        MainView mainView = new MainView(aircraft);
        SceneController sceneController = new SceneController(mainView, aircraft);

        // Configuration et affichage de la scène principale.
        primaryStage.setScene(mainView.getScene());
        primaryStage.setTitle("Simulateur d'Atterrissage");
        primaryStage.show();

        // Demande de focus pour permettre la gestion des entrées utilisateur.
        mainView.getScene().getRoot().requestFocus();
    }

    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args Arguments de ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
