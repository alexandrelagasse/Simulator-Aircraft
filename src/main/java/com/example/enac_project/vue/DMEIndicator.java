package com.example.enac_project.vue;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class DMEIndicator {
    private Label distanceLabel;

    public DMEIndicator() {
        distanceLabel = new Label("DME: 0 nm");
    }

    public void updateDistance(double distance) {
        distanceLabel.setText(String.format("DME: %.2f nm", distance));
    }

    public Label getView() {
        return distanceLabel;
    }
}
