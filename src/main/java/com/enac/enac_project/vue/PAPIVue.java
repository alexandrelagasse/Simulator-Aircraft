package com.enac.enac_project.vue;

import com.enac.enac_project.model.Point3DCustom;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 * La classe PAPIVue gère la visualisation des lumières PAPI, qui sont des indicateurs de l'angle d'approche de la piste.
 * Chaque lumière peut être allumée en rouge ou blanche pour simuler l'indication visuelle du système PAPI.
 */
public class PAPIVue {
    private Group spheresGroup = new Group(); // Groupe contenant les sphères représentant les lumières PAPI
    private final int numSpheres = 4; // Nombre de sphères/lumières PAPI
    private Point3DCustom runwayPoint; // Point de référence sur la piste pour le placement des sphères

    /**
     * Constructeur qui initialise la vue PAPI avec un point de référence sur la piste.
     *
     * @param runwayPoint Le point sur la piste où les lumières PAPI doivent être positionnées.
     */
    public PAPIVue(Point3DCustom runwayPoint) {
        this.runwayPoint = runwayPoint;
        createSpheres();
        placeSphereAtRunwayPoint();
    }

    /**
     * Crée les sphères qui représentent les lumières PAPI et les ajoute au groupe de sphères.
     */
    private void createSpheres() {
        double spacing = 20; // Espacement entre chaque sphère
        for (int i = 0; i < numSpheres; i++) {
            Sphere sphere = new Sphere(10);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.WHITE); // Couleur initiale des sphères
            material.setSpecularColor(Color.BLUE); // Couleur spéculaire pour un effet visuel
            sphere.setMaterial(material);
            sphere.setTranslateX(i * spacing);
            spheresGroup.getChildren().add(sphere);
        }
    }

    /**
     * Place les sphères au point de référence du runway avec l'espacement approprié.
     */
    private void placeSphereAtRunwayPoint() {
        double spacing = 20; // Espacement entre les sphères
        for (int i = 0; i < numSpheres; i++) {
            Sphere sphere = (Sphere) spheresGroup.getChildren().get(i);
            double x = runwayPoint.getX() + (i * spacing);
            double y = runwayPoint.getY() - 10;
            double z = runwayPoint.getZ();
            sphere.setTranslateX(x);
            sphere.setTranslateY(y);
            sphere.setTranslateZ(z);
            Rotate rotate = new Rotate(0, Rotate.Y_AXIS); // Rotation autour de l'axe Y si nécessaire
            sphere.getTransforms().add(rotate);
        }
    }

    /**
     * Met à jour l'état des lumières PAPI en fonction du nombre spécifié de lumières allumées en rouge.
     *
     * @param numLit Le nombre de lumières à allumer en rouge.
     */
    public void setIndicatorState(int numLit) {
        if (numLit < 1 || numLit > numSpheres) {
            return; // Vérifie que le nombre est dans la plage valide
        }
        numLit = numLit - 1; // Ajustement pour l'indexation de tableau
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

    /**
     * Renvoie le groupe de sphères représentant les lumières PAPI.
     *
     * @return Le groupe de sphères.
     */
    public Group getSpheres() {
        return spheresGroup;
    }
}
