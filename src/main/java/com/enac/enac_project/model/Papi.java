package com.enac.enac_project.model;

import javafx.scene.PerspectiveCamera;

/**
 * Represents a Precision Approach Path Indicator (PAPI) system for aircraft descent guidance.
 */
public class Papi {
    private Point3DCustom papiPosition; // PAPI position
    private double angleBeta = 0.55;  // Upper critical angle
    private double angleAlpha = 0.15;  // Lower critical angle
    private int papiLevel = 0; // PAPI level
    private GlidePath glidePath; // Associated glide path
    private int idealDescentAngle = 3; // Ideal descent angle

    /**
     * Constructs a PAPI object with the specified position and associated glide path.
     *
     * @param papiPosition The PAPI position
     * @param glidepath    The associated glide path
     */
    public Papi(Point3DCustom papiPosition, GlidePath glidepath) {
        this.papiPosition = papiPosition;
        this.glidePath = glidepath;
    }

    /**
     * Gets the current PAPI level.
     *
     * @return The current PAPI level
     */
    public int getPapiLevel() {
        return papiLevel;
    }

    /**
     * Updates the PAPI state based on the aircraft position.
     *
     * @param aircraft The aircraft for which to update the PAPI state
     */
    public void updatePapiState(Aircraft aircraft) {
        double descentAngle = glidePath.calculateGlideSlopeAngle(new Point3DCustom(aircraft.getX(), aircraft.getY(), aircraft.getZ()));
        if (descentAngle > angleBeta + idealDescentAngle) {
            papiLevel = 1;
        } else if (descentAngle > angleAlpha + idealDescentAngle) {
            papiLevel = 2;
        } else if (descentAngle > -angleAlpha + idealDescentAngle) {
            papiLevel = 3;
        } else if (descentAngle > -angleBeta + idealDescentAngle) {
            papiLevel = 4;
        } else {
            papiLevel = 5;
        }
    }
}
