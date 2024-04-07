package com.example.enac_project.model;

public class ILS {

    private GlidePath glidePath;
    private Localizer localizer;
    private Markers markers;

    public ILS(GlidePath glidePath, Localizer localizer, Markers markers) {
        this.glidePath = glidePath;
        this.localizer = localizer;
        this.markers = markers;
    }

    public double calculerAngleGP(Point3DCustom point) {
        return glidePath.calculerAngleGP(point);
    }

    public double calculerAngleLOC(Point3DCustom point) {
        return localizer.calculerAngleLOC(point);
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
}
