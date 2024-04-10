package com.example.enac_project.vue;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class ILSIndicator extends Group {
        private Line verticalAxis; // Axe pour le localizer
        private Line horizontalAxis; // Axe pour le glide path
        private Rectangle aircraftMarker; // Marqueur pour la position de l'avion
        private Rectangle centerBox; // Rectangle central pour la zone de sécurité
        private Circle centerDot; // Point central de visée

        private static final double AXIS_LENGTH = 100.0; // Longueur des axes de l'indicateur

        public ILSIndicator() {
            // Dessiner les axes avec des lignes pointillées
            verticalAxis = createDashedLine(AXIS_LENGTH);
            horizontalAxis = createDashedLine(AXIS_LENGTH);

            // Créer le marqueur de l'avion
            aircraftMarker = new Rectangle(-5, -5, 10, 10); // Petit rectangle pour représenter l'avion
            aircraftMarker.setFill(Color.RED);

            // Créer le rectangle central
            centerBox = new Rectangle(-20, -20, 40, 40);
            centerBox.setFill(null);
            centerBox.setStroke(Color.WHITE);

            // Créer le point central de visée
            centerDot = new Circle(0, 0, 3);
            centerDot.setFill(Color.WHITE);

            // Ajouter les éléments au groupe
            this.getChildren().addAll(verticalAxis, horizontalAxis, centerBox, centerDot, aircraftMarker);
        }

        private Line createDashedLine(double length) {
            Line line = new Line(0, -length / 2, 0, length / 2);
            // Ajustez ces valeurs pour correspondre à l'apparence souhaitée
            line.getStrokeDashArray().addAll(2.0, 4.0); // Tirets plus courts avec moins d'espace entre eux
            line.setStroke(Color.WHITE);
            return line;
        }

        private void createBackground() {
            Rectangle background = new Rectangle(-50, -50, 100, 100); // Ajustez la taille selon les besoins
            background.setFill(Color.DARKGRAY);
            background.setStroke(Color.WHITE); // Ajoutez une bordure blanche autour du fond
            background.setStrokeWidth(2); // Ajustez l'épaisseur de la bordure
            this.getChildren().add(background);
        }



    // Méthode pour mettre à jour la position du marqueur de l'avion en fonction des angles de déviation
    public void updateMarkerPosition(double angleGP, double angleLOC) {
        // Ces valeurs doivent être adaptées à l'échelle de votre indicateur
        double verticalOffset = calculateOffsetFromAngle(angleGP, AXIS_LENGTH);
        double horizontalOffset = calculateOffsetFromAngle(angleLOC, AXIS_LENGTH);

        // Mettre à jour la position du marqueur
        aircraftMarker.setTranslateX(horizontalOffset);
        aircraftMarker.setTranslateY(verticalOffset);
    }

    // Convertir un angle de déviation en un décalage linéaire pour le marqueur
    private double calculateOffsetFromAngle(double angle, double axisLength) {
        // Cela dépend de la façon dont vous voulez traduire l'angle en déplacement sur l'indicateur
        // Cet exemple suppose une relation linéaire simple (ce qui pourrait ne pas être le cas dans la réalité)
        return (angle / 90.0) * (axisLength / 2); // Exemple : un angle de 90° donne un décalage maximal
    }
}
