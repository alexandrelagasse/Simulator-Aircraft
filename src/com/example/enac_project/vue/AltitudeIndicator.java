package com.example.enac_project.vue;

import javafx.scene.control.Label;

/**
 * La classe AltitudeIndicator gère l'affichage de l'altitude sur une interface utilisateur.
 */
public class AltitudeIndicator {
    private Label altitudeLabel;

    /**
     * Constructeur qui initialise l'indicateur d'altitude.
     * Crée un label avec une altitude initiale affichée à 0 mètre.
     */
    public AltitudeIndicator() {
        altitudeLabel = new Label("Altitude: 0 m");
    }

    /**
     * Met à jour l'affichage de l'altitude dans l'interface utilisateur.
     * L'altitude est formatée pour s'afficher avec deux décimales et ajustée pour être positive si elle est négative.
     *
     * @param altitude L'altitude actuelle à afficher, en mètres.
     */
    public void updateAltitude(double altitude) {
        altitudeLabel.setText(String.format("Altitude: %.2f m", altitude));
    }

    /**
     * Retourne le composant visuel (Label) qui affiche l'altitude.
     * Cette méthode permet d'intégrer l'indicateur d'altitude dans d'autres composants de l'interface utilisateur.
     *
     * @return Le Label configuré pour afficher l'altitude.
     */
    public Label getView() {
        return altitudeLabel;
    }
}
