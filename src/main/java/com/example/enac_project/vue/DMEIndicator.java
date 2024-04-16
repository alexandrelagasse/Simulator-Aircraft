package com.example.enac_project.vue;

import javafx.scene.control.Label;

/**
 * La classe DMEIndicator gère l'affichage de la distance DME (Distance Measuring Equipment) dans une interface utilisateur.
 * Elle encapsule un Label de JavaFX pour montrer la distance jusqu'à un point de référence spécifique, typiquement lié à l'aviation.
 */
public class DMEIndicator {
    private Label distanceLabel;

    /**
     * Constructeur qui initialise l'indicateur DME.
     * Crée un label avec une distance initiale affichée à 0 milles nautiques.
     */
    public DMEIndicator() {
        distanceLabel = new Label("DME: 0 nm");
    }

    /**
     * Met à jour l'affichage de la distance DME dans l'interface utilisateur.
     * La distance est formatée pour s'afficher avec deux décimales.
     *
     * @param distance La distance actuelle à afficher, en milles nautiques.
     */
    public void updateDistance(double distance) {
        distanceLabel.setText(String.format("DME: %.2f nm", distance));
    }

    /**
     * Retourne le composant visuel (Label) qui affiche la distance DME.
     * Cette méthode permet d'intégrer l'indicateur DME dans d'autres composants de l'interface utilisateur.
     *
     * @return Le Label configuré pour afficher la distance DME.
     */
    public Label getView() {
        return distanceLabel;
    }
}
