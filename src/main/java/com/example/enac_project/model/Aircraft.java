package com.example.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Aircraft extends Point3DCustom {
    private DoubleProperty speed = new SimpleDoubleProperty();
    private DoubleProperty yaw = new SimpleDoubleProperty();   // Pour le lacet
    private DoubleProperty pitch = new SimpleDoubleProperty(); // Pour le tangage
    private DoubleProperty roll = new SimpleDoubleProperty();  // Pour le roulis
    private ILS ils;
    private static final double DEFAULT_X = 0;
    private static final double DEFAULT_Y = -250;
    private static final double DEFAULT_Z = -5000;
    private static final double DEFAULT_SPEED = 30;
    private Point3DCustom runwayPoint;
    private PapiStateController papiStateController;


    public Aircraft(Point3DCustom runwayPoint, Papi papi) {
        super(DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
        ils = new ILS(runwayPoint);
        this.speed.set(DEFAULT_SPEED);
        this.runwayPoint = runwayPoint;
        papiStateController = new PapiStateController(papi);
    }

    public double getSpeed() { return speed.get(); }
    public void setSpeed(double value) { speed.set(value); }
    public DoubleProperty speedProperty() { return speed; }

    public void decelerate(double amount) {
        double newSpeed = Math.max(5, getSpeed() - amount);
        setSpeed(newSpeed);
    }

    public void updatePosition() {
        // Calcule le vecteur de direction basé sur l'orientation de l'avion (yaw, pitch, roll)
        Point3DCustom direction = calculateDirectionVector(getYaw(), getPitch(), getRoll());

        // Met à jour la position de l'avion en fonction de la direction et de la vitesse
        setX(getX() + direction.getX() * getSpeed());
        setY(getY() + direction.getY() * getSpeed());
        setZ(getZ() + direction.getZ() * getSpeed());

        // Applique la décélération
        decelerate(0.1);
    }

    private Point3DCustom calculateDirectionVector(double yaw, double pitch, double roll) {
        // Convertit les angles en radians pour le calcul
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        // Calcule les composantes du vecteur de direction
        double x = Math.cos(pitchRad) * Math.sin(yawRad);
        double y = Math.sin(pitchRad);
        double z = Math.cos(pitchRad) * Math.cos(yawRad);

        // Retourne un nouveau Point3DCustom représentant le vecteur de direction
        return new Point3DCustom(x, y, z);
    }

    public double calculateAltitudeDifference() {
        // Obtenez la position Z de la piste depuis l'ILS
        double runwayZ = runwayPoint.getY();

        // Calculez la différence d'altitude
        return getY() - runwayZ;
    }

    public double getYaw() { return yaw.get(); }
    public void setYaw(double value) { yaw.set(value); }
    public DoubleProperty yawProperty() { return yaw; }

    public double getPitch() { return pitch.get(); }
    public void setPitch(double value) { pitch.set(value); }
    public DoubleProperty pitchProperty() { return pitch; }

    public double getRoll() { return roll.get(); }
    public void setRoll(double value) { roll.set(value); }
    public DoubleProperty rollProperty() { return roll; }

    public ILS getILS () { return ils; }
    public PapiStateController getPapiStateController() {return papiStateController;}
    public void reset() {

        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        setZ(DEFAULT_Z);
        setSpeed(DEFAULT_SPEED);
        setRoll(0);
        setPitch(0);
        setYaw(0);

    }

}
