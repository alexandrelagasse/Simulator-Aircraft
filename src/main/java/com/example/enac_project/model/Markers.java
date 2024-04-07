package com.example.enac_project.model;

import com.example.enac_project.model.Point3DCustom;

public class Markers {

    private Point3DCustom OM;
    private Point3DCustom MM;
    private Point3DCustom IM;
    private double rayon;

    public Markers(Point3DCustom OM, Point3DCustom MM, Point3DCustom IM, double rayon) {
        this.OM = OM;
        this.MM = MM;
        this.IM = IM;
        this.rayon = rayon;
    }

    public boolean franchissementOM(Point3DCustom point) {
        double distance = point.distance(OM);
        return distance <= rayon;
    }

    public boolean franchissementMM(Point3DCustom point) {
        double distance = point.distance(MM);
        return distance <= rayon;
    }

    public boolean franchissementIM(Point3DCustom point) {
        double distance = point.distance(IM);
        return distance <= rayon;
    }
}
