package com.example.enac_project.vue;

import com.example.enac_project.model.Aircraft;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class CameraManager {
    private PerspectiveCamera camera;
    private final Rotate rotateX;
    private final Rotate rotateY;
    private final Rotate rotateZ;

    public CameraManager() {
        camera = new PerspectiveCamera(true);
        rotateX = new Rotate(0, Rotate.X_AXIS);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
        camera.setNearClip(1);
        camera.setFarClip(2000);
        camera.setFieldOfView(60);
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public void bindToAircraft(Aircraft aircraft) {
        camera.translateXProperty().bind(aircraft.xProperty());
        camera.translateYProperty().bind(aircraft.yProperty());
        camera.translateZProperty().bind(aircraft.zProperty());
    }

    public void updateOrientation(double yaw, double pitch, double roll) {
        rotateY.setAngle(yaw);
        rotateX.setAngle(pitch);
        rotateZ.setAngle(roll);
    }

    public void resetCamera() {
        camera.getTransforms().addAll(new Rotate(0, Rotate.X_AXIS), new Rotate(0, Rotate.Y_AXIS));
    }
}
