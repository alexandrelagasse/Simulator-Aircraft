package com.example.enac_project.model;

public class Localizer {
    private Point3DCustom seuilPiste;

    public Localizer(Point3DCustom seuilPiste) {

        this.seuilPiste = new Point3DCustom(seuilPiste.getX(), seuilPiste.getY(), seuilPiste.getZ() - 1250);
    }

    public double calculerAngleLOC(Point3DCustom positionAvion) {
        // Calculer la déviation de l'axe Z
        double dx = positionAvion.getX() - seuilPiste.getX();
        double dz = positionAvion.getZ() - seuilPiste.getZ();
        // Angle par rapport à l'axe Z
        double angleRadLOC = Math.atan2(dx, dz);
        return Math.toDegrees(angleRadLOC);
    }
}
