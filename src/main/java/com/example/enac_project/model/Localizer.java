package com.example.enac_project.model;

/**
 * La classe Localizer calcule l'angle de localisation pour aider à l'alignement de l'avion avec l'axe de la piste lors de l'approche.
 * Cet angle permet de déterminer à quel point l'avion est aligné avec le centre de la pitse
 */
public class Localizer {
    private Point3DCustom seuilPiste;

    /**
     * Constructeur de Localizer qui initialise la position du seuil de la piste.
     * Le seuil de la piste est ajusté en reculant de 1250 unités sur l'axe Z pour simuler le début qui est le début de la piste
     *
     * @param seuilPiste Le point représentant le seuil initial de la piste avant ajustement.
     */
    public Localizer(RunwayModel seuilPiste) {
        this.seuilPiste = seuilPiste.getOriginPoint();
    }

    /**
     * Calcule l'angle de localisation (Localizer) de l'avion par rapport à l'axe longitudinal de la piste.
     * Cette méthode mesure l'écart angulaire entre la position actuelle de l'avion et l'axe central de la piste.
     *
     * @param positionAvion La position actuelle de l'avion.
     * @return L'angle de déviation par rapport à l'axe de la piste, en degrés.
     */
    public double calculerAngleLOC(Point3DCustom positionAvion) {
        // Calculer la déviation horizontale et la distance le long de l'axe de la piste
        double dx = positionAvion.getX() - seuilPiste.getX();
        double dz = positionAvion.getZ() - seuilPiste.getZ();

        // Calculer l'angle en radians et le convertir en degrés
        double angleRadLOC = Math.atan2(dx, dz);
        return Math.toDegrees(angleRadLOC);
    }
}
