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

    public double getX() { return x.get(); }
    public void setX(double value) { x.set(value); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double value) { y.set(value); }
    public DoubleProperty yProperty() { return y; }

    public double getZ() { return z.get(); }
    public void setZ(double value) { z.set(value); }
    public DoubleProperty zProperty() { return z; }

    public double distance(Point3DCustom other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static double angleBetween(Point3DCustom p1, Point3DCustom p2) {
        double dotProduct = p1.getX() * p2.getX() + p1.getY() * p2.getY() + p1.getZ() * p2.getZ();
        double magnitudeP1 = Math.sqrt(p1.getX() * p1.getX() + p1.getY() * p1.getY() + p1.getZ() * p1.getZ());
        double magnitudeP2 = Math.sqrt(p2.getX() * p2.getX() + p2.getY() * p2.getY() + p2.getZ() * p2.getZ());
        return Math.acos(dotProduct / (magnitudeP1 * magnitudeP2));
    }
}
