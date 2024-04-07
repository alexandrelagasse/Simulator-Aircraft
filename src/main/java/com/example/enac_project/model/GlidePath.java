package com.example.enac_project.model;

public class GlidePath {
    public double calculerAngleGP(Point3DCustom point) {
        double DME = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        double angleRadGP = Math.atan(point.getZ() / DME);
        double angleGP = Math.toDegrees(angleRadGP);
        return angleGP;
    }
}
