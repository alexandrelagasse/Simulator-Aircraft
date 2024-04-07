package com.example.enac_project.model;

public class Localizer {
    private Point3DCustom seuilPiste;

    public Localizer(Point3DCustom seuilPiste) {
        this.seuilPiste = seuilPiste;
    }

    public double calculerAngleLOC(Point3DCustom point) {
        double angleRadLOC = Math.atan2(point.getY() - seuilPiste.getY(), point.getX() - seuilPiste.getX());
        double angleLOC = Math.toDegrees(angleRadLOC);
        return angleLOC;
    }
}
