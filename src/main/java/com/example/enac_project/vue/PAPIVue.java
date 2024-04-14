package com.example.enac_project.vue;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class PAPIVue {
    private Group spheresGroup = new Group();
    private final int numSpheres = 4;

    public PAPIVue() {
        createSpheres();
    }

    private void createSpheres() {
        double spacing = 20;
        for (int i = 0; i < numSpheres; i++) {
            Sphere sphere = new Sphere(10);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.WHITE);
            material.setSpecularColor(Color.BLUE);
            sphere.setMaterial(material);
            sphere.setTranslateX(i * spacing);
            spheresGroup.getChildren().add(sphere);
        }
    }

    public void setIndicatorState(int numLit) {
        if (numLit < 1 || numLit > 5) {
            throw new IllegalArgumentException("Number must be between 1 and 5.");
        }

        numLit = numLit - 1;
        for (int i = 0; i < numSpheres; i++) {
            Sphere sphere = (Sphere) spheresGroup.getChildren().get(i);
            if (i < numLit) {
                ((PhongMaterial) sphere.getMaterial()).setDiffuseColor(Color.RED);
            } else {
                ((PhongMaterial) sphere.getMaterial()).setDiffuseColor(Color.WHITE);
                ((PhongMaterial) sphere.getMaterial()).setSpecularColor(Color.BLUE);
            }
        }
    }

    public Group getSpheres() {
        return spheresGroup;
    }
}
