package com.example.enac_project.model;

import com.example.enac_project.model.Point3DCustom;

/**
 * La classe Markers gère la détection du franchissement des marqueurs sur la piste d'atterrissage.
 * Les marqueurs OM (Outer Marker), MM (Middle Marker) et IM (Inner Marker) sont utilisés pour guider l'approche de l'avion.
 */
public class Markers {

    private Point3DCustom OM;
    private Point3DCustom MM;
    private Point3DCustom IM;
    private double rayon;  // Rayon de détection pour chaque marqueur

    /**
     * Constructeur de la classe Markers.
     * Initialise les positions des trois marqueurs ainsi que le rayon de détection commun.
     *
     * @param OM La position du Outer Marker.
     * @param MM La position du Middle Marker.
     * @param IM La position du Inner Marker.
     * @param rayon Le rayon de détection pour les marqueurs.
     */
    public Markers(Point3DCustom OM, Point3DCustom MM, Point3DCustom IM, double rayon) {
        this.OM = OM;
        this.MM = MM;
        this.IM = IM;
        this.rayon = rayon;
    }

    /**
     * Détermine si un point spécifié franchit le Outer Marker (OM).
     *
     * @param point La position actuelle de l'objet (ex. avion) à tester.
     * @return true si le point est à l'intérieur du rayon de détection du OM, false sinon.
     */
    public boolean franchissementOM(Point3DCustom point) {
        double distance = point.distance(OM);
        return distance <= rayon;
    }

    /**
     * Détermine si un point spécifié franchit le Middle Marker (MM).
     *
     * @param point La position actuelle de l'objet (ex. avion) à tester.
     * @return true si le point est à l'intérieur du rayon de détection du MM, false sinon.
     */
    public boolean franchissementMM(Point3DCustom point) {
        double distance = point.distance(MM);
        return distance <= rayon;
    }

    /**
     * Détermine si un point spécifié franchit le Inner Marker (IM).
     *
     * @param point La position actuelle de l'objet (ex. avion) à tester.
     * @return true si le point est à l'intérieur du rayon de détection du IM, false sinon.
     */
    public boolean franchissementIM(Point3DCustom point) {
        double distance = point.distance(IM);
        return distance <= rayon;
    }
}
