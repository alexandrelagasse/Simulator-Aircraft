package com.enac.enac_project.vue;

import com.enac.enac_project.model.Point3DCustom;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * La classe ILSIndicator représente un indicateur graphique pour le système d'atterrissage aux instruments (ILS).
 * Elle affiche des éléments tels que des barres de localisation et de glide path, permettant de visualiser l'alignement de l'avion avec la piste.
 */
public class ILSIndicator extends Group {
    private static final int AXIS_LENGTH = 90; // Adjust the length as needed

    private Rectangle indicatorBackground; // The indicator's background
    private Circle centerCircle; // The circle in the center
    private Circle centerDot; // The center aiming dot
    private Line localizerHorizBar; // La barre supérieure du glide path
    private Line glidePathBar; // La barre inférieure du glide path
    private Line verticalAxis;
    private Line horizontalAxis;
    double offsetForCenteringX = 0.0;
    double offsetForCenteringY = 0.0;

    /**
     * Constructeur qui initialise l'indicateur ILS avec une position spécifiée.
     *
     * @param posHUD La position initiale du HUD où sera placé l'indicateur.
     */
    public ILSIndicator(Point3DCustom posHUD) {
        initIndicatorBackground(posHUD);
        offsetForCenteringX =  indicatorBackground.getWidth() / 2 + posHUD.getX();
        offsetForCenteringY =  indicatorBackground.getHeight() / 2 + posHUD.getY();
        initAxes();
        initCenterCircle();
        initCenterDot();
        initGlidePathBar();
        initLocalizerHoriBar();

        this.getChildren().addAll(indicatorBackground, centerCircle, verticalAxis, horizontalAxis,centerDot,  localizerHorizBar, glidePathBar);
    }

    // Méthodes privées pour initialiser les différents éléments graphiques
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

    private void initLocalizerHoriBar() {
        localizerHorizBar = new Line(0, 0, 50, 0);
        localizerHorizBar.setStroke(Color.WHITE);
        localizerHorizBar.setStrokeWidth(2);
        localizerHorizBar.setTranslateX(offsetForCenteringX - 25); // Centre - demi-longueur
        localizerHorizBar.setTranslateY(offsetForCenteringY); // Centre
    }

    private void initGlidePathBar() {

        // La barre verticale doit s'étendre de haut en bas à partir du centre.
        glidePathBar = new Line(0, 0, 0, 50);
        glidePathBar.setStroke(Color.WHITE);
        glidePathBar.setStrokeWidth(2);
        glidePathBar.setTranslateX(offsetForCenteringX); // Centre
        glidePathBar.setTranslateY(offsetForCenteringY - 25); // Centre - demi-longueur
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

    /**
     * Ajuste la position de la barre de glide path en fonction d'une déviation spécifiée.
     *
     * @param deviation La déviation à appliquer sur la position verticale de la barre.
     */
    public void adjustGlidePathBars(double deviation) {

        glidePathBar.setTranslateY(offsetForCenteringY - localizerHorizBar.getLayoutBounds().getWidth() / 2 + deviation);

    }

    /**
     * Déplace la barre de localisation horizontalement en fonction de la déviation spécifiée.
     *
     * @param deviation La déviation à appliquer sur la position horizontale de la barre.
     */
    public void moveLocalizerBar(double deviation) {
        // Déplacer le localizer bar horizontalement selon la déviation
        localizerHorizBar.setTranslateX(offsetForCenteringX - glidePathBar.getLayoutBounds().getHeight() / 2 + deviation);
    }


}
