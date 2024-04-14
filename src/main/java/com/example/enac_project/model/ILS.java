package com.example.enac_project.model;

public class ILS {

    private GlidePath glidePath;
    private Localizer localizer;
    private Markers markers;

    public ILS(RunwayModel point) {
        glidePath = new GlidePath(point);
        localizer = new Localizer(point);
        double rayonDetection = 1000;
        markers = new Markers(
                new Point3DCustom(0, 0, 0), // Position de l'OM
                new Point3DCustom(0, 0, 6000), // Position du MM
                new Point3DCustom(0, 0, 6900), // Position de l'IM
                rayonDetection                  // Rayon de détection pour le franchissement
        );
        //markers = new Markers(point);
    }
    public boolean franchissementMarkersOM(Point3DCustom point) {
        return markers.franchissementOM(point);
    }
    public boolean franchissementMarkersMM(Point3DCustom point) {
        return markers.franchissementMM(point);
    }

    public boolean franchissementMarkersIM(Point3DCustom point) {
        return markers.franchissementIM(point);
    }

    public double calculateLocalizerBar(Point3DCustom point) {
        double angleLOC = localizer.calculerAngleLOC(point);
        double degreesPerPixel = 4.5; // 45 pixels pour 10 degrés

        double displacement = angleLOC * degreesPerPixel;
        // Limiter le déplacement à la moitié de l'indicateur pour que les barres ne dépassent pas
        displacement = Math.min(Math.max(displacement, -22.5), 22.5); // -22.5 à 22.5 pixels
        return displacement;
    }

    public double calculateGlidePathBar(Point3DCustom point) {
        double angleGP = glidePath.calculerAngleGP(point);
        double degreesPerPixel = 4.5; // 45 pixels pour 10 degrés

        double displacement = angleGP * degreesPerPixel;
        System.out.println("Displacement" + displacement);
        // Limiter le déplacement à la moitié de l'indicateur pour que les barres ne dépassent pas
        displacement = Math.min(Math.max(displacement, -22.5), 22.5); // -22.5 à 22.5 pixels
        return displacement;
    }

    public double calculateDME(Point3DCustom point) {
        return glidePath.calculerDMEComplet(point);
    }
}
