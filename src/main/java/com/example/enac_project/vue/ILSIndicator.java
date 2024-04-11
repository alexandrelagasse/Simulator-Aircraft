package com.example.enac_project.vue;

import com.example.enac_project.model.Point3DCustom;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


public class ILSIndicator extends Group {
    private static final int AXIS_LENGTH = 90; // Adjust the length as needed

    private Rectangle indicatorBackground; // The indicator's background
    private Circle centerCircle; // The circle in the center
    private Circle centerDot; // The center aiming dot
    private Rectangle localizerBar; // Le carré noir pour le localizer
    private Line glidePathHorizBar; // La barre supérieure du glide path
    private Line glidePathVertiBar; // La barre inférieure du glide path
    private Line verticalAxis;
    private Line horizontalAxis;
    double offsetForCenteringX = 0.0;
    double offsetForCenteringY = 0.0;

    public ILSIndicator(Point3DCustom posHUD) {
        initIndicatorBackground(posHUD);
        offsetForCenteringX =  indicatorBackground.getWidth() / 2 + posHUD.getX();
        offsetForCenteringY =  indicatorBackground.getHeight() / 2 + posHUD.getY();
        initAxes();
        initCenterCircle();
        initCenterDot();
        initLocalizerBar();
        initGlidePathBars();

        this.getChildren().addAll(indicatorBackground, centerCircle, verticalAxis, horizontalAxis,centerDot,  glidePathHorizBar, glidePathVertiBar, localizerBar);
    }

    private void initIndicatorBackground(Point3DCustom pos) {
        indicatorBackground = new Rectangle(100, 100, Color.DARKGRAY);
        indicatorBackground.setTranslateX(pos.getX());
        indicatorBackground.setTranslateY(pos.getY());
        indicatorBackground.setTranslateZ(pos.getZ());
        indicatorBackground.setArcWidth(30);
        indicatorBackground.setArcHeight(30);
        indicatorBackground.setStroke(Color.BLACK);
    }

    private void initAxes() {
        verticalAxis = createDashedLine(AXIS_LENGTH, false);
        horizontalAxis = createDashedLine(AXIS_LENGTH, true);
        verticalAxis.setTranslateX(offsetForCenteringX);
        verticalAxis.setTranslateY(offsetForCenteringY);
        horizontalAxis.setTranslateX(offsetForCenteringX);
        horizontalAxis.setTranslateY(offsetForCenteringY);
    }

    private void initCenterCircle() {
        // Créer un cercle noir avec une opacité (par exemple, 0.5 pour 50% d'opacité)
        Color fillColor = new Color(0, 0, 0, 0.5); // Noir avec 50% d'opacité
        centerCircle = new Circle(0, 0, 45);
        centerCircle.setFill(fillColor);
        centerCircle.setStroke(Color.WHITE); // La bordure reste blanche et pleinement opaque
        centerCircle.setTranslateX(offsetForCenteringX);
        centerCircle.setTranslateY(offsetForCenteringY);
    }

    private void initCenterDot() {
        centerDot = new Circle(0, 0, 6, Color.GREY);
        centerDot.setStroke(Color.WHITE);
        centerDot.setTranslateX(offsetForCenteringX);
        centerDot.setTranslateY(offsetForCenteringY);
    }

    private void initLocalizerBar() {
        localizerBar = new Rectangle(0, -20, 20, 10);
        localizerBar.setFill(null);
        localizerBar.setStroke(Color.BLACK);
        localizerBar.setTranslateX(offsetForCenteringX - localizerBar.getWidth() / 2);
        localizerBar.setTranslateY(offsetForCenteringY - localizerBar.getHeight() / 2);
    }

    private void initGlidePathBars() {
        // La barre horizontale doit s'étendre de gauche à droite à partir du centre.
        glidePathHorizBar = new Line(offsetForCenteringX - 25, offsetForCenteringY, offsetForCenteringX + 25, offsetForCenteringY);
        glidePathHorizBar.setStroke(Color.WHITE);
        glidePathHorizBar.setStrokeWidth(2);

        // La barre verticale doit s'étendre de haut en bas à partir du centre.
        glidePathVertiBar = new Line(offsetForCenteringX, offsetForCenteringY -  25, offsetForCenteringX, offsetForCenteringY + 25);
        glidePathVertiBar.setStroke(Color.WHITE);
        glidePathVertiBar.setStrokeWidth(2);
    }

    private Line createDashedLine(double length, boolean isHorizontal) {
        Line line;
        if (isHorizontal) {
            // Création d'un axe horizontal
            line = new Line(-length / 2, 0, length / 2, 0);
        } else {
            // Création d'un axe vertical
            line = new Line(0, -length / 2, 0, length / 2);
        }
        line.getStrokeDashArray().addAll(4.0, 6.0);
        line.setStroke(Color.WHITE);
        return line;
    }

    public void adjustGlidePathBars(double deviation) {
        // Déplacer la barre horizontale

        glidePathHorizBar.setTranslateY(offsetForCenteringY + deviation);

        // Déplacer la barre verticale
        glidePathVertiBar.setTranslateX(offsetForCenteringX + deviation);
    }

    public void moveLocalizerBar(double deviation) {
        // Déplacer le localizer bar horizontalement selon la déviation
        localizerBar.setTranslateX(offsetForCenteringX + deviation - localizerBar.getWidth() / 2);
    }


}
