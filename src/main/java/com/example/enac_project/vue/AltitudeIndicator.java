package com.example.enac_project.vue;

import javafx.scene.control.Label;

public class AltitudeIndicator {
    private Label altitudeLabel;

    public AltitudeIndicator() {
        altitudeLabel = new Label("Altitude: 0 ft");
    }

    public void updateAltitude(double altitude) {
        altitudeLabel.setText(String.format("Altitude: %.2f ft", altitude));
    }

    public Label getView() {
        return altitudeLabel;
    }
}
