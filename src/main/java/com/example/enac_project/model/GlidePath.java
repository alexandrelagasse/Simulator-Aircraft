package com.example.enac_project.model;

public class GlidePath {
    private Point3DCustom seuilPiste;  // Position du seuil de la piste

    public GlidePath(Point3DCustom seuilPiste) {
        this.seuilPiste = new Point3DCustom(seuilPiste.getX(), seuilPiste.getY(), seuilPiste.getZ() - 1250);
    }

    public double calculerAngleGP(Point3DCustom positionAvion) {
        // Calculer la distance horizontale et verticale entre l'avion et le seuil de la piste
        double dx = positionAvion.getX() - seuilPiste.getX();
        double dy = positionAvion.getY() - seuilPiste.getY();
        double dz = positionAvion.getZ() - seuilPiste.getZ();

        double DME = Math.sqrt(dx * dx + dy * dy); // Distance horizontale
        if (DME == 0) {
            return 0;  // Pour éviter la division par zéro
        }

        double angleRadGP = Math.atan(dz / DME);
        return Math.toDegrees(angleRadGP); // Convertir en degrés
    }
}
