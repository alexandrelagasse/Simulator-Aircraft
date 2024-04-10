package com.example.enac_project.model;

public class SimulationController {
    private ILS ilsSystem;
    private Aircraft aircraft;

    public SimulationController() {
        GlidePath glidePath = new GlidePath();
        Localizer localizer = new Localizer(new Point3DCustom(0, 0, -1250));

        /*OM : (0,0,-1250 - 7000) = (0,0,-8250)
        MM : (0,0,-1250 - 1000) = (0,0,-2250)
        IM : (0,0,-1250 - 100) = (0,0,-1350)*/
        double rayonDetection = 500;
        Markers markers = new Markers(
                new Point3DCustom(0, 0, -8250), // Position de l'OM
                new Point3DCustom(0, 0, -2250), // Position du MM
                new Point3DCustom(0, 0, -1350), // Position de l'IM
                rayonDetection                  // Rayon de détection pour le franchissement
        );

        ilsSystem = new ILS(glidePath, localizer, markers);

        aircraft = new Aircraft(0,-250,-3000, 30);
    }

    public Aircraft getAircraft() { return aircraft; }

    public void startSimulation() {
    }
}