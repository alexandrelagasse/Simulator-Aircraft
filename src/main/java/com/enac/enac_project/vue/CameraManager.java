package com.enac.enac_project.vue;

import com.enac.enac_project.model.Aircraft;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

/**
 * La classe CameraManager gère la configuration et la manipulation d'une caméra perspective dans un environnement 3D.
 * Elle permet de positionner et d'orienter la caméra en fonction des mouvements et de la position d'un avion.
 */
public class CameraManager {
    private PerspectiveCamera camera;
    private final Rotate rotateX;
    private final Rotate rotateY;
    private final Rotate rotateZ;

    /**
     * Constructeur qui initialise la caméra avec des paramètres de position et d'orientation spécifiques.
     *
     * @param x Position initiale de la caméra sur l'axe X.
     * @param y Position initiale de la caméra sur l'axe Y.
     * @param z Position initiale de la caméra sur l'axe Z.
     */
    public CameraManager(double x, double y, double z) {
        camera = new PerspectiveCamera(true);
        rotateX = new Rotate(0, Rotate.X_AXIS);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        camera.setTranslateX(x);
        camera.setTranslateY(y);
        camera.setTranslateZ(z);

        camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
        camera.setNearClip(1);
        camera.setFarClip(20000);
        camera.setFieldOfView(60);
    }

    /**
     * Renvoie l'objet PerspectiveCamera géré par cette classe.
     *
     * @return La caméra perspective utilisée pour les visualisations.
     */
    public PerspectiveCamera getCamera() {
        return camera;
    }

    /**
     * Attache la caméra à un avion, permettant à la caméra de suivre automatiquement les changements de position de l'avion.
     *
     * @param aircraft L'avion auquel la caméra doit être attachée.
     */
    public void attachToAircraft(Aircraft aircraft) {
        aircraft.xProperty().addListener((observable, oldValue, newValue) -> {
            camera.setTranslateX(newValue.doubleValue());
        });
        aircraft.yProperty().addListener((observable, oldValue, newValue) -> {
            camera.setTranslateY(newValue.doubleValue());
        });
        aircraft.zProperty().addListener((observable, oldValue, newValue) -> {
            camera.setTranslateZ(newValue.doubleValue());
        });
    }

    /**
     * Met à jour l'orientation de la caméra en fonction des valeurs de lacet, de tangage et de roulis fournies.
     *
     * @param yaw Angle de lacet à appliquer à la caméra.
     * @param pitch Angle de tangage à appliquer à la caméra.
     * @param roll Angle de roulis à appliquer à la caméra.
     */
    public void updateOrientation(double yaw, double pitch, double roll) {
        rotateY.setAngle(yaw);
        rotateX.setAngle(pitch);
        rotateZ.setAngle(roll);
    }

    /**
     * Réinitialise les transformations appliquées à la caméra pour les remettre aux valeurs par défaut.
     */
    public void resetCamera() {
        camera.getTransforms().clear();
        camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
    }
}
