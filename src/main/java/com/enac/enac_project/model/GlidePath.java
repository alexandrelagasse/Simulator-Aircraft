package com.enac.enac_project.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The GlidePath class calculates the descent angle and distance (DME)
 * between the aircraft and the runway threshold.
 */
public class GlidePath {
    private static final Logger logger = LoggerFactory.getLogger(GlidePath.class);

    private final Point3DCustom runwayThreshold;  // Runway threshold position

    // Constants for calculations
    private static final double MIN_DISTANCE = 0.1;  // Minimum distance to avoid division by zero
    private static final double MAX_ANGLE = 45.0;    // Maximum descent angle in degrees
    private static final double MIN_ANGLE = -45.0;   // Minimum descent angle in degrees

    /**
     * Constructs a GlidePath object.
     * Initializes the runway threshold position by adjusting the Z coordinate to create a descent margin.
     *
     * @param runway The runway model containing the threshold point
     * @throws IllegalArgumentException if runway is null
     */
    public GlidePath(RunwayModel runway) {
        if (runway == null) {
            String message = "Runway model cannot be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        this.runwayThreshold = runway.getThresholdPoint();
        logger.info("GlidePath initialized with runway threshold at ({}, {}, {})",
                   runwayThreshold.getX(), runwayThreshold.getY(), runwayThreshold.getZ());
    }

    /**
     * Calculates the descent angle (Glide Path) of the aircraft relative to the runway threshold.
     * This angle is determined based on the current aircraft position and the runway threshold.
     *
     * @param aircraftPosition The current aircraft position
     * @return The descent angle in degrees
     * @throws IllegalArgumentException if aircraftPosition is null
     */
    public double calculateGlideSlopeAngle(Point3DCustom aircraftPosition) {
        if (aircraftPosition == null) {
            String message = "Aircraft position cannot be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        double distance = calculateDME(aircraftPosition);
        if (distance < MIN_DISTANCE) {
            distance = MIN_DISTANCE; // Prevent division by zero
        }

        double height = aircraftPosition.getY() - runwayThreshold.getY();
        double angle = Math.toDegrees(Math.atan(-height / distance));

        // Clamp the angle to valid range
        angle = Math.min(Math.max(angle, MIN_ANGLE), MAX_ANGLE);

        logger.debug("Calculated glide slope angle: {} degrees (height: {}, distance: {})",
                    angle, height, distance);
        return angle;
    }

    /**
     * Calculates the Distance Measuring Equipment (DME) between the aircraft and the runway threshold.
     *
     * @param aircraftPosition The current aircraft position
     * @return The total distance in meters
     * @throws IllegalArgumentException if aircraftPosition is null
     */
    public double calculateDME(Point3DCustom aircraftPosition) {
        if (aircraftPosition == null) {
            String message = "Aircraft position cannot be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        double deltaX = aircraftPosition.getX() - runwayThreshold.getX();
        double deltaZ = aircraftPosition.getZ() - runwayThreshold.getZ();
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        logger.debug("Calculated DME distance: {} meters (deltaX: {}, deltaZ: {})",
                    distance, deltaX, deltaZ);
        return distance;
    }

    /**
     * Gets the runway threshold position.
     *
     * @return The runway threshold point
     */
    public Point3DCustom getRunwayThreshold() {
        return runwayThreshold;
    }

    @Override
    public String toString() {
        return String.format("GlidePath[threshold=(%f, %f, %f)]",
                           runwayThreshold.getX(),
                           runwayThreshold.getY(),
                           runwayThreshold.getZ());
    }
}
