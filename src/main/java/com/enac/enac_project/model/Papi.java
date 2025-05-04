package com.enac.enac_project.model;

import javafx.scene.PerspectiveCamera;

/**
 * Représente un système de repérage visuel de descente d'aéronef (PAPI - Precision Approach Path Indicator).
 */
public class Papi {
    private Point3DCustom papiPosition; // Position du PAPI
    private double angleBeta = 0.55;  // Angle supérieur critique
    private double angleAlpha = 0.15;  // Angle inférieur critique
    private int papiLevel = 0; // Niveau du PAPI
    private GlidePath glidePath; // La trajectoire de descente associée au PAPI
    private int planDescenteIdeal = 3; // Plan de descente idéal

    /**
     * Construit un objet PAPI avec la position spécifiée et la trajectoire de descente associée.
     *
     * @param papiPosition La position du PAPI
     * @param glidepath    La trajectoire de descente associée au PAPI
     */
    public Papi(Point3DCustom papiPosition, GlidePath glidepath) {
        this.papiPosition = papiPosition;
        this.glidePath = glidepath;
    }

    /**
     * Récupère le niveau actuel du PAPI.
     *
     * @return Le niveau actuel du PAPI
     */
    public int getPapiLevel() {
        return papiLevel;
    }

    /**
     * Met à jour l'état du PAPI en fonction de la position de l'aéronef.
     *
     * @param aircraft L'aéronef pour lequel mettre à jour l'état du PAPI
     */
    public void updatePapiState(Aircraft aircraft) {
        double planDescente = glidePath.calculerAngleGP(new Point3DCustom(aircraft.getX(), aircraft.getY(), aircraft.getZ()));
        if (planDescente > angleBeta + planDescenteIdeal) {
            papiLevel = 1;
        } else if (planDescente > angleAlpha + planDescenteIdeal) {
            papiLevel = 2;
        } else if (planDescente > -angleAlpha + planDescenteIdeal) {
            papiLevel = 3;
        } else if (planDescente > -angleBeta + planDescenteIdeal) {
            papiLevel = 4;
        } else {
            papiLevel = 5;
        }
    }
}
