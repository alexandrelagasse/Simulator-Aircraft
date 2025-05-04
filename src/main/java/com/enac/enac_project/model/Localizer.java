package com.enac.enac_project.model;

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
        this.seuilPiste = seuilPiste.getThresholdPoint();
    }

    /**
     * Calcule l'angle de localisation (Localizer) de l'avion par rapport à l'axe longitudinal de la piste.
     * Cette méthode mesure l'écart angulaire entre la position actuelle de l'avion et l'axe central de la piste.
     *
     * @param positionAvion La position actuelle de l'avion.
     * @return L'angle de déviation par rapport à l'axe de la piste, en degrés.
     */
    public double calculerAngleLOC(Point3DCustom positionAvion) {
        double x = positionAvion.getX() - seuilPiste.getX();
        double z = positionAvion.getZ() - seuilPiste.getZ();

        double distance = Math.sqrt(x*x + z*z);

        double coteAdjacent = positionAvion.getZ() - seuilPiste.getZ();
        double signe = 1;
        if (positionAvion.getX() > seuilPiste.getX()) {
            signe = -1;
        }

        System.out.println("Cote adja" + coteAdjacent);

        double angle = Math.abs(Math.acos((coteAdjacent * -1) / distance));


        return Math.toDegrees(angle * signe);
    }
}
