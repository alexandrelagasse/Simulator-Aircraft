package com.example.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Aircraft extends Point3DCustom {
    private DoubleProperty speed = new SimpleDoubleProperty();
    private ILS ils;


    public Aircraft(double x, double y, double z, double initialSpeed, RunwayPoint runwayPoint) {
        super(x, y, z);
        ils = new ILS((RunwayPoint) new RunwayPoint(0,1,-1250,0,0,0));
        this.speed.set(initialSpeed);
    }

    public double getSpeed() { return speed.get(); }
    public void setSpeed(double value) { speed.set(value); }
    public DoubleProperty speedProperty() { return speed; }

    public void decelerate(double amount) {
        double newSpeed = Math.max(5, getSpeed() - amount);
        setSpeed(newSpeed);
    }

    public void updatePosition() {
        double newZ = this.getZ() + this.getSpeed();
        double newY = this.getY() + this.getSpeed() * 0.1;
        this.setZ(newZ);
        this.setY(newY);
        this.decelerate(0.1);
    }

    public ILS getILS () { return ils; }

}
