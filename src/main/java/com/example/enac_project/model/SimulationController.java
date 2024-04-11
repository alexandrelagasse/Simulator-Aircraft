package com.example.enac_project.model;

public class SimulationController {
    private ILS ilsSystem;
    private Aircraft aircraft;
    private RunwayPoint runwayPoint;

    public SimulationController() {

        runwayPoint = new RunwayPoint(0,0,0, 400, 2, 2500);
        aircraft = new Aircraft(0,-250,-3000, 30, runwayPoint);

    }

    public Aircraft getAircraft() { return aircraft; }

    public Point3DCustom getRunwayPoint() { return runwayPoint; }

    public void startSimulation() {
    }
}