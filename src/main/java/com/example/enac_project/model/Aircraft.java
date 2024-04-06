package com.example.enac_project.model;

public class Aircraft extends Point3DCustom {

    private double speed = 1.0;

    public Aircraft() {
        super(0, -20, 0);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
