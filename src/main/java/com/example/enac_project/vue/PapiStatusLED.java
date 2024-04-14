package com.example.enac_project.vue;

import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * La classe PapiStatusLED est une représentation visuelle d'une LED qui change de couleur et peut clignoter pour indiquer différents états du PAPI.
 * Cette LED peut afficher différentes couleurs et clignotements pour représenter visuellement l'état de conformité du système PAPI.
 */
public class PapiStatusLED extends Circle {
    private static final double DIAMETER = 20; // Diamètre de la LED
    private FadeTransition blinkTransition; // Transition pour le clignotement

    /**
     * Constructeur qui initialise la LED avec un diamètre spécifié et configure son clignotement.
     */
    public PapiStatusLED() {
        super(DIAMETER, Color.TRANSPARENT);
        setStroke(Color.BLACK); // Définit la couleur du contour de la LED
        setupBlinking();
    }

    /**
     * Configure la transition de clignotement pour la LED.
     */
    private void setupBlinking() {
        blinkTransition = new FadeTransition(Duration.seconds(0.5), this);
        blinkTransition.setFromValue(1.0);
        blinkTransition.setToValue(0.0);
        blinkTransition.setCycleCount(FadeTransition.INDEFINITE);
        blinkTransition.setAutoReverse(true);
    }

    /**
     * Met à jour l'état de la LED en fonction de l'état du système PAPI.
     * @param state L'état du système PAPI qui détermine la couleur et le clignotement de la LED.
     */
    public void updateStatus(int state) {
        stopBlinking(); // Arrête le clignotement pour tout changement d'état
        switch (state) {
            case 1:
                setColor(Color.YELLOW);
                startBlinking(); // Commence à clignoter pour l'état 1
                break;
            case 2:
                setColor(Color.YELLOW);
                break;
            case 3:
                setColor(Color.GREEN);
                break;
            case 4:
                setColor(Color.RED);
                break;
            case 5:
                setColor(Color.RED);
                startBlinking(); // Commence à clignoter pour l'état 5
                break;
            default:
                setColor(Color.TRANSPARENT); // Aucun état valide, rend la LED transparente
                break;
        }
    }

    /**
     * Définit la couleur de remplissage de la LED.
     * @param color La couleur à appliquer à la LED.
     */
    private void setColor(Color color) {
        setFill(color);
    }

    /**
     * Commence le clignotement de la LED si elle n'est pas déjà en train de clignoter.
     */
    private void startBlinking() {
        if (blinkTransition.getStatus() != FadeTransition.Status.RUNNING) {
            blinkTransition.play();
        }
    }

    /**
     * Arrête le clignotement de la LED et assure qu'elle est complètement visible.
     */
    private void stopBlinking() {
        if (blinkTransition.getStatus() == FadeTransition.Status.RUNNING) {
            blinkTransition.stop();
        }
        setOpacity(1.0); // Assure que la LED est entièrement visible quand elle ne clignote pas
    }
}
