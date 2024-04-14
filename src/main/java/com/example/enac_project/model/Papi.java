package com.example.enac_project.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.PerspectiveCamera;

public class Papi {
    private Point3DCustom papiPosition;
    private double angleBeta = 0.55;
    private double angleAlpha = 0.15;
    private IntegerProperty papiLevel = new SimpleIntegerProperty(0);

    public Papi(Point3DCustom papiPosition) {
        this.papiPosition = papiPosition;
        this.angleBeta = 0.55;
        this.angleAlpha = 0.15;
    }

    public int getPapiLevel() {
        return papiLevel.get();
    }

    public IntegerProperty papiLevelProperty() {
        return papiLevel;
    }

    public void updatePapiState(PerspectiveCamera camera) {
        double angleGP = calculateAngle(camera);
        if (angleGP > angleBeta) {
            papiLevel.set(5);
        } else if (angleGP > angleAlpha) {
            papiLevel.set(4);
        } else if (angleGP > -angleAlpha) {
            papiLevel.set(3);
        } else if (angleGP > -angleBeta) {
            papiLevel.set(2);
        } else {
            papiLevel.set(1);
        }
    }

    public double calculateAngle(PerspectiveCamera camera) {
        Point3DCustom cameraPosition = new Point3DCustom(
                camera.getLocalToParentTransform().getTx(),
                camera.getLocalToParentTransform().getTy(),
                camera.getLocalToParentTransform().getTz()
        );
        double dx = papiPosition.getX() - cameraPosition.getX();
        double dy = papiPosition.getY() - cameraPosition.getY();
        double dz = papiPosition.getZ() - cameraPosition.getZ();

        Point3DCustom directionVector = new Point3DCustom(dx, dy, dz);
        Point3DCustom cameraForwardVector = new Point3DCustom(0, 0, -1);

        return Math.toDegrees(Point3DCustom.angleBetween(cameraForwardVector, directionVector));
    }
}
