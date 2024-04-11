package com.example.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class RunwayPoint extends Point3DCustom {

    private double v1;
    private double v2;
    private double v3;


    public RunwayPoint(double x, double y, double z, double v1, double v2, double v3) {
        super(x, y, z);
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }
    public double getV1() {
        return v1;
    }

    public double getV2() {
        return v2;
    }

    public double getV3() {
        return v3;
    }
}