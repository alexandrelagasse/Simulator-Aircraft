package com.example.enac_project.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.PerspectiveCamera;

/**
 * La classe Papi modélise un système PAPI qui aide les pilotes à maintenir le bon angle de descente lors de l'approche d'une piste.
 * Cette classe calcule l'angle de descente de l'avion et ajuste le niveau de PAPI en conséquence.
 */
public class Papi {
    private Point3DCustom papiPosition;
    private double angleBeta = 0.55;  // Angle supérieur critique
    private double angleAlpha = 0.15;  // Angle inférieur critique
    private int papiLevel = 0;
    private GlidePath glidePath;
    private int planDescenteIdeal = 3;

    /**
     * Constructeur qui initialise la position du système PAPI.
     * Les angles de référence pour les lumières PAPI sont également initialisés à des valeurs standard.
     *
     * @param papiPosition La position du système PAPI sur la piste.
     */
    public Papi(Point3DCustom papiPosition, GlidePath glidepath) {
        this.papiPosition = papiPosition;
        this.glidePath = glidepath;
    }

    /**
     * Récupère le niveau actuel de PAPI, qui indique la position de l'avion par rapport à l'angle de descente optimal.
     * Les valeurs vont de 1 (beaucoup trop bas) à 5 (beaucoup trop haut).
     *
     * @return Le niveau actuel de PAPI.
     */
    public int getPapiLevel() {
        return papiLevel;
    }

    /**
     * Met à jour le niveau de PAPI en fonction de l'angle de descente actuel calculé par rapport à la position de la caméra.
     * Cela permet d'ajuster visuellement les indicateurs pour le pilote.
     *
     * @param camera La caméra représentant la position et l'orientation de l'avion.
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
