package com.example.enac_project.vue;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 * La classe MarkersIndicator gère les indicateurs visuels pour les trois types de marqueurs utilisés dans la navigation aérienne :
 * OM (Outer Marker), MM (Middle Marker) et IM (Inner Marker).
 * Chaque marqueur est représenté par un cercle coloré, initialisé en rouge et pouvant être mis à jour en vert.
 */
public class MarkersIndicator {

    private Circle om;  // Cercle représentant l'Outer Marker
    private Circle mm;  // Cercle représentant le Middle Marker
    private Circle im;  // Cercle représentant l'Inner Marker

    /**
     * Constructeur qui initialise les trois cercles avec une couleur rouge par défaut.
     */
    public MarkersIndicator() {
        om = new Circle(10, Color.RED); // OM setup avec couleur rouge
        mm = new Circle(10, Color.RED); // MM setup avec couleur rouge
        im = new Circle(10, Color.RED); // IM setup avec couleur rouge
    }

    /**
     * Obtient le cercle représentant le Outer Marker (OM).
     * @return Le cercle représentant le OM.
     */
    public Circle getOM() {
        return om;
    }

    /**
     * Obtient le cercle représentant le Middle Marker (MM).
     * @return Le cercle représentant le MM.
     */
    public Circle getMM() {
        return mm;
    }

    /**
     * Obtient le cercle représentant le Inner Marker (IM).
     * @return Le cercle représentant le IM.
     */
    public Circle getIM() {
        return im;
    }

    /**
     * Met à jour la couleur du cercle représentant le Outer Marker (OM) en vert pour indiquer son activation.
     */
    public void setOM() {
        om.setFill(Color.GREEN);
    }

    /**
     * Met à jour la couleur du cercle représentant le Middle Marker (MM) en vert pour indiquer son activation.
     */
    public void setMM() {
        mm.setFill(Color.GREEN);
    }

    /**
     * Met à jour la couleur du cercle représentant le Inner Marker (IM) en vert pour indiquer son activation.
     */
    public void setIM() {
        im.setFill(Color.GREEN);
    }

    /**
     * Réinitialise la couleur du cercle représentant le Outer Marker (OM) en rouge pour indiquer sa désactivation.
     */
    public void resetOM() {
        om.setFill(Color.RED);
    }

    /**
     * Réinitialise la couleur du cercle représentant le Middle Marker (MM) en rouge pour indiquer sa désactivation.
     */
    public void resetMM() {
        mm.setFill(Color.RED);
    }

    /**
     * Réinitialise la couleur du cercle représentant le Inner Marker (IM) en rouge pour indiquer sa désactivation.
     */
    public void resetIM() {
        im.setFill(Color.RED);
    }
}
