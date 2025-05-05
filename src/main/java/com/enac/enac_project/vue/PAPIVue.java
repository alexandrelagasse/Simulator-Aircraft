package com.enac.enac_project.vue;

import com.enac.enac_project.model.RunwayModel;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import java.util.Objects;
import java.util.List;
import java.util.Arrays;

/**
 * The PAPIVue class represents the visual display of the Precision Approach Path Indicator (PAPI)
 * in a 3D environment.
 */
public class PAPIVue extends Group {
    private static final double LIGHT_SPACING = 20;
    private static final double LIGHT_HEIGHT = 2;
    private static final double LIGHT_RADIUS = 1;
    
    private Cylinder[] lights;
    private PointLight[] lightSources;
    private Group lightsGroup;

    public PAPIVue(RunwayModel runwayModel) {
        super();
        lightsGroup = new Group();
        createPAPILights();
    }

    private void createPAPILights() {
        lights = new Cylinder[4];
        lightSources = new PointLight[4];
        
        for (int i = 0; i < 4; i++) {
            Cylinder light = new Cylinder(LIGHT_RADIUS, LIGHT_HEIGHT);
            PhongMaterial material = new PhongMaterial(Color.WHITE);
            material.setSpecularColor(Color.WHITE.brighter());
            material.setSpecularPower(100);
            light.setMaterial(material);
            light.setTranslateX(i * LIGHT_SPACING - 30);
            light.setTranslateY(0);
            light.setTranslateZ(0);
            light.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
            
            PointLight pointLight = new PointLight(Color.WHITE);
            pointLight.setTranslateX(i * LIGHT_SPACING - 30);
            pointLight.setTranslateY(0);
            pointLight.setTranslateZ(0);
            
            lights[i] = light;
            lightSources[i] = pointLight;
            
            lightsGroup.getChildren().addAll(light, pointLight);
        }
        this.getChildren().add(lightsGroup);
    }

    public void setIndicatorState(int level) {
        // Reset all lights to white
        for (int i = 0; i < 4; i++) {
            lights[i].setMaterial(new PhongMaterial(Color.WHITE));
            lightSources[i].setColor(Color.WHITE);
        }

        // Set appropriate colors based on PAPI level
        switch (level) {
            case 1: // Too high
                for (int i = 0; i < 4; i++) {
                    lights[i].setMaterial(new PhongMaterial(Color.RED));
                    lightSources[i].setColor(Color.RED);
                }
                break;
            case 2: // Slightly high
                for (int i = 0; i < 3; i++) {
                    lights[i].setMaterial(new PhongMaterial(Color.RED));
                    lightSources[i].setColor(Color.RED);
                }
                lights[3].setMaterial(new PhongMaterial(Color.WHITE));
                lightSources[3].setColor(Color.WHITE);
                break;
            case 3: // On glide path
                for (int i = 0; i < 2; i++) {
                    lights[i].setMaterial(new PhongMaterial(Color.RED));
                    lightSources[i].setColor(Color.RED);
                }
                for (int i = 2; i < 4; i++) {
                    lights[i].setMaterial(new PhongMaterial(Color.WHITE));
                    lightSources[i].setColor(Color.WHITE);
                }
                break;
            case 4: // Slightly low
                lights[0].setMaterial(new PhongMaterial(Color.RED));
                lightSources[0].setColor(Color.RED);
                for (int i = 1; i < 4; i++) {
                    lights[i].setMaterial(new PhongMaterial(Color.WHITE));
                    lightSources[i].setColor(Color.WHITE);
                }
                break;
            case 5: // Too low
                for (int i = 0; i < 4; i++) {
                    lights[i].setMaterial(new PhongMaterial(Color.WHITE));
                    lightSources[i].setColor(Color.WHITE);
                }
                break;
        }
    }

    public Group getSpheres() {
        return lightsGroup;
    }
}
