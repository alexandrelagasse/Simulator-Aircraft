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

import java.util.Objects;

/**
 * La classe RunwayView représente la vue de la piste dans un environnement de simulation de vol.
 * Elle permet de visualiser une piste avec des détails appropriés tels que dimensions et position.
 */
public class RunwayView extends Group {

    private Box piste;

    /**
     * Constructeur qui crée une vue de piste basée sur un modèle de piste.
     *
     * @param runwayPoint Le modèle de piste utilisé pour définir les dimensions et la position de la piste.
     */
    public RunwayView(RunwayModel runwayPoint) {
        super();
        placerLumiere();
        dessinerPiste(runwayPoint);
        dessinerTerrain(runwayPoint);
    }

    /**
     * Dessine la piste en utilisant les dimensions et la position fournies par le modèle de piste.
     *
     * @param runwayPoint Le modèle de piste fournissant les spécifications nécessaires.
     */
    private void dessinerPiste(RunwayModel runwayPoint) {
        Image texture = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images.jfif")));
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(texture);

        piste = new Box(runwayPoint.getWidth(), runwayPoint.getHeight(), runwayPoint.getLength());
        piste.setTranslateX(runwayPoint.getX());
        piste.setTranslateY(runwayPoint.getY());
        piste.setTranslateZ(runwayPoint.getZ());
        piste.setCullFace(CullFace.BACK);
        piste.setDrawMode(DrawMode.FILL);
        piste.setMaterial(material);
        this.getChildren().add(piste);
    }

    /**
     * Dessine un terrain autour de la piste pour simuler un environnement.
     * @param runwayPoint Le modèle de la piste autour de laquelle le terrain sera dessiné.
     */
    private void dessinerTerrain(RunwayModel runwayPoint) {
        // Charger la texture d'herbe
        Image grassTexture = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/herbe.jpg")));
        PhongMaterial grassMaterial = new PhongMaterial();
        grassMaterial.setDiffuseMap(grassTexture);

        // Créer un terrain plus grand que la piste
        double margin = 1000; // La marge autour de la piste, à ajuster selon besoin
        Box terrain = new Box(runwayPoint.getWidth() + margin, runwayPoint.getHeight(), runwayPoint.getLength() + margin);
        terrain.setTranslateX(runwayPoint.getX());
        terrain.setTranslateY(runwayPoint.getY() + 1); // Positionner en dessous de la piste avec +1 en Y
        terrain.setTranslateZ(runwayPoint.getZ());
        terrain.setCullFace(CullFace.BACK);
        terrain.setDrawMode(DrawMode.FILL);
        terrain.setMaterial(grassMaterial);

        // Ajouter le terrain à la scène, en dessous de la piste
        this.getChildren().add(0, terrain); // Ajouter en premier pour qu'il soit en dessous de la piste
    }


    /**
     * Place une source de lumière dans la scène pour éclairer la piste.
     */
    private void placerLumiere() {
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(500);
        pointLight.setTranslateY(-800);
        pointLight.setTranslateZ(-20);
        this.getChildren().addAll(pointLight);

        // Ajout d'éclairage ambiant pour éviter les zones trop sombres
        AmbientLight ambient = new AmbientLight(Color.rgb(50, 50, 50));
        this.getChildren().add(ambient);
    }

    /**
     * Retourne l'objet Box qui représente la piste.
     *
     * @return L'objet Box représentant la piste.
     */
    public Box getPiste() {
        return piste;
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
