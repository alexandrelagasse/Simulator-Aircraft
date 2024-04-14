package com.example.enac_project.vue;

import com.example.enac_project.utils.PapiState;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PapiStatusLED extends Circle {
    private static final double DIAMETER = 20;
    private FadeTransition blinkTransition;

    public PapiStatusLED() {
        super(DIAMETER, Color.TRANSPARENT);
        setStroke(Color.BLACK); // Contour de la LED
        setupBlinking();
    }

    private void setupBlinking() {
        blinkTransition = new FadeTransition(Duration.seconds(0.5), this);
        blinkTransition.setFromValue(1.0);
        blinkTransition.setToValue(0.0);
        blinkTransition.setCycleCount(FadeTransition.INDEFINITE);
        blinkTransition.setAutoReverse(true);
    }

    public void updateStatus(PapiState state) {
        stopBlinking();  // Stop blinking for any state change
        switch (state) {
            case VERY_HIGH:
                setColor(Color.YELLOW);
                startBlinking();
                break;
            case HIGH:
                setColor(Color.YELLOW);
                break;
            case ON_COURSE:
                setColor(Color.GREEN);
                break;
            case LOW:
                setColor(Color.RED);
                break;
            case VERY_LOW:
                setColor(Color.RED);
                startBlinking();
                break;
            default:
                setColor(Color.TRANSPARENT);
                break;
        }
    }

    private void setColor(Color color) {
        setFill(color);
    }

    private void startBlinking() {
        if (!blinkTransition.getStatus().equals(FadeTransition.Status.RUNNING)) {
            blinkTransition.play();
        }
    }

    private void stopBlinking() {
        if (blinkTransition.getStatus().equals(FadeTransition.Status.RUNNING)) {
            blinkTransition.stop();
        }
        setOpacity(1.0);  // Ensure the LED is fully visible when not blinking
    }
}
