package com.enac.enac_project.vue;

import com.enac.enac_project.model.RunwayModel;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.PointLight;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Objects;

/**
 * La classe RunwayView représente la vue de la piste dans un environnement de simulation de vol.
 * Elle permet de visualiser une piste avec des détails appropriés tels que dimensions et position.
 */
public class RunwayView extends Group {

    private Box runway;
    private Timeline markerLightsTimeline;

    /**
     * Constructeur qui crée une vue de piste basée sur un modèle de piste.
     *
     * @param runwayModel Le modèle de piste utilisé pour définir les dimensions et la position de la piste.
     */
    public RunwayView(RunwayModel runwayModel) {
        super();
        setupLighting();
        drawRunway(runwayModel);
        drawTerrain(runwayModel);
        drawRunwayMarkings(runwayModel);
        drawRunwayLights(runwayModel);
    }

    /**
     * Dessine la piste en utilisant les dimensions et la position fournies par le modèle de piste.
     *
     * @param runwayModel Le modèle de piste fournissant les spécifications nécessaires.
     */
    private void drawRunway(RunwayModel runwayModel) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(200, 200, 200)); // Gris clair pour la piste
        material.setSpecularColor(Color.WHITE);
        material.setSpecularPower(50);

        runway = new Box(runwayModel.getWidth(), runwayModel.getHeight(), runwayModel.getLength());
        runway.setTranslateX(runwayModel.getX());
        runway.setTranslateY(runwayModel.getY());
        runway.setTranslateZ(runwayModel.getZ());
        runway.setCullFace(CullFace.BACK);
        runway.setDrawMode(DrawMode.FILL);
        runway.setMaterial(material);
        this.getChildren().add(runway);
    }

    /**
     * Dessine un terrain autour de la piste pour simuler un environnement.
     * @param runwayModel Le modèle de la piste autour de laquelle le terrain sera dessiné.
     */
    private void drawTerrain(RunwayModel runwayModel) {
        PhongMaterial grassMaterial = new PhongMaterial();
        grassMaterial.setDiffuseColor(Color.rgb(34, 139, 34)); // Vert foncé pour l'herbe
        grassMaterial.setSpecularColor(Color.rgb(50, 205, 50)); // Vert plus clair pour les reflets
        grassMaterial.setSpecularPower(30);

        double margin = 1000;
        Box terrain = new Box(runwayModel.getWidth() + margin, runwayModel.getHeight(), runwayModel.getLength() + margin);
        terrain.setTranslateX(runwayModel.getX());
        terrain.setTranslateY(runwayModel.getY() + 1);
        terrain.setTranslateZ(runwayModel.getZ());
        terrain.setCullFace(CullFace.BACK);
        terrain.setDrawMode(DrawMode.FILL);
        terrain.setMaterial(grassMaterial);
        this.getChildren().add(0, terrain);
    }

    private void drawRunwayMarkings(RunwayModel runwayModel) {
        // Centerline
        Box centerline = new Box(1, 0.1, runwayModel.getLength());
        centerline.setMaterial(new PhongMaterial(Color.WHITE));
        centerline.setTranslateX(runwayModel.getX());
        centerline.setTranslateY(runwayModel.getY() + 0.1);
        centerline.setTranslateZ(runwayModel.getZ());
        this.getChildren().add(centerline);

        // Threshold markings
        double thresholdWidth = 30;
        Box threshold = new Box(runwayModel.getWidth(), 0.1, thresholdWidth);
        threshold.setMaterial(new PhongMaterial(Color.WHITE));
        threshold.setTranslateX(runwayModel.getX());
        threshold.setTranslateY(runwayModel.getY() + 0.1);
        threshold.setTranslateZ(runwayModel.getZ() - runwayModel.getLength()/2 + thresholdWidth/2);
        this.getChildren().add(threshold);

        // Touchdown zone markings
        double touchdownWidth = 20;
        for (int i = 0; i < 3; i++) {
            Box touchdown = new Box(runwayModel.getWidth(), 0.1, touchdownWidth);
            touchdown.setMaterial(new PhongMaterial(Color.WHITE));
            touchdown.setTranslateX(runwayModel.getX());
            touchdown.setTranslateY(runwayModel.getY() + 0.1);
            touchdown.setTranslateZ(runwayModel.getZ() - runwayModel.getLength()/3 + i * touchdownWidth * 2);
            this.getChildren().add(touchdown);
        }
    }

    private void drawRunwayLights(RunwayModel runwayModel) {
        // Edge lights
        double spacing = 50;
        int numLights = (int)(runwayModel.getLength() / spacing);
        for (int i = 0; i < numLights; i++) {
            // Left edge
            createRunwayLight(
                runwayModel.getX() - runwayModel.getWidth()/2,
                runwayModel.getY(),
                runwayModel.getZ() - runwayModel.getLength()/2 + i * spacing,
                Color.WHITE
            );
            // Right edge
            createRunwayLight(
                runwayModel.getX() + runwayModel.getWidth()/2,
                runwayModel.getY(),
                runwayModel.getZ() - runwayModel.getLength()/2 + i * spacing,
                Color.WHITE
            );
        }

        // Threshold lights
        for (int i = 0; i < 6; i++) {
            createRunwayLight(
                runwayModel.getX() - runwayModel.getWidth()/2 + i * runwayModel.getWidth()/5,
                runwayModel.getY(),
                runwayModel.getZ() - runwayModel.getLength()/2,
                Color.GREEN
            );
        }
    }

    private void createRunwayLight(double x, double y, double z, Color color) {
        Cylinder light = new Cylinder(1, 2);
        PhongMaterial material = new PhongMaterial(color);
        material.setSpecularColor(color.brighter());
        material.setSpecularPower(100);
        light.setMaterial(material);
        light.setTranslateX(x);
        light.setTranslateY(y);
        light.setTranslateZ(z);
        light.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        this.getChildren().add(light);

        PointLight pointLight = new PointLight(color);
        pointLight.setTranslateX(x);
        pointLight.setTranslateY(y);
        pointLight.setTranslateZ(z);
        this.getChildren().add(pointLight);
    }

    /**
     * Place une source de lumière dans la scène pour éclairer la piste.
     */
    private void setupLighting() {
        PointLight mainLight = new PointLight(Color.WHITE);
        mainLight.setTranslateX(500);
        mainLight.setTranslateY(-800);
        mainLight.setTranslateZ(-20);
        this.getChildren().add(mainLight);

        // Ajout d'éclairage ambiant pour éviter les zones trop sombres
        AmbientLight ambient = new AmbientLight(Color.rgb(50, 50, 50));
        this.getChildren().add(ambient);
    }

    /**
     * Retourne l'objet Box qui représente la piste.
     *
     * @return L'objet Box représentant la piste.
     */
    public Box getRunway() {
        return runway;
    }

    /**
     * Méthode optionnelle pour tracer un repère tridimensionnel centré sur la piste pour aider à la visualisation.
     */
    private void tracerRepere() {
        Sphere zero = new Sphere(20);
        zero.setMaterial(new PhongMaterial(Color.RED));
        this.getChildren().add(zero);
        Box axeX = new Box(500, 3, 3);
        axeX.setTranslateX(250);
        axeX.setMaterial(new PhongMaterial(Color.BLUE));
        this.getChildren().add(axeX);
        Box axeY = new Box(3, 500, 3);
        axeY.setTranslateY(250);
        axeY.setMaterial(new PhongMaterial(Color.RED));
        this.getChildren().add(axeY);
        Box axeZ = new Box(3, 3, 500);
        axeZ.setTranslateZ(250);
        axeZ.setMaterial(new PhongMaterial(Color.GREEN));
        this.getChildren().add(axeZ);
    }
}
