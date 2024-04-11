package com.example.enac_project.model;

public class ILS {

    private GlidePath glidePath;
    private Localizer localizer;
    private Markers markers;
    double degreesPerPixel = 10.0 / 45.0;  // 10 degrés pour 45 pixels

    public ILS(RunwayPoint point) {
        glidePath = new GlidePath();
        localizer = new Localizer(point);
        double rayonDetection = 500;
        markers = new Markers(
                new Point3DCustom(0, 0, -8250), // Position de l'OM
                new Point3DCustom(0, 0, -2250), // Position du MM
                new Point3DCustom(0, 0, -1350), // Position de l'IM
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

        double displacement = angleLOC / degreesPerPixel;
        // Limiter le déplacement pour rester dans le cercle
        displacement = Math.min(Math.max(displacement, -45), 45); // -45 à 45 pixels
        return displacement;
    }

    public double calculateGlidePathBar(Point3DCustom point) {
        double angleGP = glidePath.calculerAngleGP(point);

        double displacement = angleGP / degreesPerPixel;
        // Limiter le déplacement pour rester dans le cercle
        displacement = Math.min(Math.max(displacement, -45), 45); // -45 à 45 pixels
        return displacement;
    }
}
