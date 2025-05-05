package com.enac.enac_project.vue;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import java.util.Objects;

/**
 * The MarkersIndicator class represents the visual indicators for approach markers (OM, MM, IM)
 * in a 3D environment.
 */
public class MarkersIndicator extends Group {
    private Sphere outerMarker;
    private Sphere middleMarker;
    private Sphere innerMarker;
    private Timeline markerAnimation;

    public MarkersIndicator() {
        super();
        createMarkers();
        setupAnimation();
    }

    private void createMarkers() {
        // Outer Marker (OM)
        outerMarker = createMarker(0, -250, 0, Color.BLUE);
        
        // Middle Marker (MM)
        middleMarker = createMarker(0, -250, 6000, Color.YELLOW);
        
        // Inner Marker (IM)
        innerMarker = createMarker(0, -250, 6900, Color.WHITE);
    }

    private Sphere createMarker(double x, double y, double z, Color color) {
        Sphere marker = new Sphere(10);
        PhongMaterial material = new PhongMaterial(color);
        material.setSpecularColor(color.brighter());
        material.setSpecularPower(100);
        marker.setMaterial(material);
        marker.setTranslateX(x);
        marker.setTranslateY(y);
        marker.setTranslateZ(z);

        PointLight light = new PointLight(color);
        light.setTranslateX(x);
        light.setTranslateY(y);
        light.setTranslateZ(z);
        this.getChildren().add(light);

        this.getChildren().add(marker);
        return marker;
    }

    private void setupAnimation() {
        markerAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> {
                outerMarker.setVisible(!outerMarker.isVisible());
                middleMarker.setVisible(!middleMarker.isVisible());
                innerMarker.setVisible(!innerMarker.isVisible());
            })
        );
        markerAnimation.setCycleCount(Animation.INDEFINITE);
        markerAnimation.play();
    }

    public Sphere getOM() {
        return outerMarker;
    }

    public Sphere getMM() {
        return middleMarker;
    }

    public Sphere getIM() {
        return innerMarker;
    }

    public void resetOM() {
        outerMarker.setVisible(false);
    }

    public void resetMM() {
        middleMarker.setVisible(false);
    }

    public void resetIM() {
        innerMarker.setVisible(false);
    }

    public void setOM(boolean visible) {
        outerMarker.setVisible(visible);
    }

    public void setMM(boolean visible) {
        middleMarker.setVisible(visible);
    }

    public void setIM(boolean visible) {
        innerMarker.setVisible(visible);
    }

    public void stopAnimation() {
        if (markerAnimation != null) {
            markerAnimation.stop();
        }
    }
}
