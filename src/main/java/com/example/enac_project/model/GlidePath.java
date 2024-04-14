package com.example.enac_project.model;

/**
 * La classe GlidePath calcule l'angle de descente et la distance (DME) entre l'avion et le seuil de la piste.
 */
public class GlidePath {
    private Point3DCustom seuilPiste;  // Position du seuil de la piste

    /**
     * Constructeur de GlidePath.
     * Initialise la position du seuil de la piste en ajustant la coordonnée Z pour créer une marge de descente.
     *
     * @param seuilPiste Le point représentant le seuil de la piste avant ajustement.
     */
    public GlidePath(RunwayModel seuilPiste) {
        this.seuilPiste = seuilPiste.getThresholdPoint();
    }

    /**
     * Calcule l'angle de descente (Glide Path) de l'avion par rapport au seuil de la piste.
     * Cet angle est déterminé en fonction de la position actuelle de l'avion et du seuil de la piste.
     *
     * @param positionAvion La position actuelle de l'avion.
     * @return L'angle de descente en degrés.
     */
    public double calculerAngleGP(Point3DCustom positionAvion) {
        // Calculer la distance verticale entre l'avion et le seuil de la piste
        double dz = positionAvion.getZ() - seuilPiste.getZ();
        double DME = calculerDMEComplet(positionAvion);

        // Éviter la division par zéro
        if (DME == 0) {
            return 0;
        }

        // Calculer l'angle en radians puis convertir en degrés
        double angleRadGP = Math.atan(dz / DME);
        return Math.toDegrees(angleRadGP);
    }

    /**
     * Calcule la Distance Measuring Equipment (DME) entre l'avion et le seuil de la piste.
     * Cette mesure prend en compte toutes les dimensions spatiales (X, Y, Z).
     *
     * @param positionAvion La position actuelle de l'avion.
     * @return La distance totale en mètres.
     */
    public double calculerDMEComplet(Point3DCustom positionAvion) {
        double dx = positionAvion.getX() - seuilPiste.getX();
        double dy = positionAvion.getY() - seuilPiste.getY();
        double dz = positionAvion.getZ() - seuilPiste.getZ();

        // Calcul de la distance euclidienne en 3D
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
