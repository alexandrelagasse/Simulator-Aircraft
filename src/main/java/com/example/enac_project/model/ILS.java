package com.example.enac_project.model;

/**
 * Représente un système d'atterrissage aux instruments (ILS) pour la navigation des avions.
 */
public class ILS {

    private GlidePath glidePath; // La composante de trajectoire de descente de l'ILS
    private Localizer localizer; // La composante de localisation de l'ILS
    private Markers markers; // La composante des marqueurs de l'ILS
    private double angleDescente = 3; // L'angle de descente pour la trajectoire de descente

    /**
     * Construit un objet ILS avec le point de piste spécifié et la trajectoire de descente.
     *
     * @param point     Le point de piste associé à l'ILS
     * @param glidePath La trajectoire de descente de l'ILS
     */
    public ILS(RunwayModel point, GlidePath glidePath) {
        this.glidePath = glidePath;
        localizer = new Localizer(point);
        double rayonDetection = 1000;
        markers = new Markers(
                new Point3DCustom(0, 0, 0), // Position de l'OM
                new Point3DCustom(0, 0, 6000), // Position du MM
                new Point3DCustom(0, 0, 6900), // Position de l'IM
                rayonDetection                  // Rayon de détection pour le franchissement
        );
    }

    /**
     * Vérifie si l'avion a franchi le Marqueur Extérieur (OM).
     *
     * @param point La position de l'avion
     * @return Vrai si l'avion a franchi l'OM, sinon faux
     */
    public boolean franchissementMarkersOM(Point3DCustom point) {
        return markers.franchissementOM(point);
    }

    /**
     * Vérifie si l'avion a franchi le Marqueur Intermédiaire (MM).
     *
     * @param point La position de l'avion
     * @return Vrai si l'avion a franchi le MM, sinon faux
     */
    public boolean franchissementMarkersMM(Point3DCustom point) {
        return markers.franchissementMM(point);
    }

    /**
     * Vérifie si l'avion a franchi le Marqueur Intérieur (IM).
     *
     * @param point La position de l'avion
     * @return Vrai si l'avion a franchi l'IM, sinon faux
     */
    public boolean franchissementMarkersIM(Point3DCustom point) {
        return markers.franchissementIM(point);
    }

    /**
     * Calcule la position de la barre de localisation.
     *
     * @param posAircraft La position de l'avion
     * @return Le déplacement de la barre de localisation en pixels
     */
    public double calculateLocalizerBar(Point3DCustom posAircraft) {
        double angle = localizer.calculerAngleLOC(posAircraft);
        System.out.println("angle =" + angle);
        double degreesPerPixel = 4.5; // 45 pixels pour 10 degrés

        double displacement = angle * degreesPerPixel;
        // Limiter le déplacement à la moitié de l'indicateur pour que les barres ne dépassent pas
        displacement = Math.min(Math.max(displacement, -22.5), 22.5); // -22.5 à 22.5 pixels
        return displacement;
    }

    /**
     * Calcule la position de la barre de trajectoire de descente.
     *
     * @param posAircraft La position de l'avion
     * @return Le déplacement de la barre de trajectoire de descente en pixels
     */
    public double calculateGlidePathBar(Point3DCustom posAircraft) {
        double angle = glidePath.calculerAngleGP(posAircraft);
        double degreesPerPixel = 4.5; // 45 pixels pour 10 degrés

        double displacement = (angle - angleDescente) * degreesPerPixel;
        // Limiter le déplacement à la moitié de l'indicateur pour que les barres ne dépassent pas
        displacement = Math.min(Math.max(displacement, -22.5), 22.5); // -22.5 à 22.5 pixels
        return displacement;
    }

    /**
     * Calcule la distance DME (Distance Measuring Equipment).
     *
     * @param point Le point pour lequel calculer la distance DME
     * @return La distance DME par rapport au point spécifié
     */
    public double calculateDME(Point3DCustom point) {
        return glidePath.calculerDMEComplet(point);
    }
}
