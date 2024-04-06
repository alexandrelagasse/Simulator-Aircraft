package com.example.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Point3DCustom {
    protected final DoubleProperty x = new SimpleDoubleProperty();
    protected final DoubleProperty y = new SimpleDoubleProperty();
    protected final DoubleProperty z = new SimpleDoubleProperty();

    public Point3DCustom(double x, double y, double z) {
        this.x.set(x);
        this.y.set(y);
        this.z.set(z);
    }

    // Getters et setters
    public double getX() { return x.get(); }
    public void setX(double value) { x.set(value); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double value) { y.set(value); }
    public DoubleProperty yProperty() { return y; }

    public double getZ() { return z.get(); }
    public void setZ(double value) { z.set(value); }
    public DoubleProperty zProperty() { return z; }
}
