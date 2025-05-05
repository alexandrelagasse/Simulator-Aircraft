package com.enac.enac_project.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Localizer class calculates the localization angle to help align the aircraft
 * with the runway axis during approach. This angle determines how well the aircraft
 * is aligned with the runway centerline.
 */
public class Localizer {
    private static final Logger logger = LoggerFactory.getLogger(Localizer.class);

    private final Point3DCustom runwayThreshold;

    // Constants for calculations
    private static final double MIN_DISTANCE = 0.1;  // Minimum distance to avoid division by zero
    private static final double MAX_ANGLE = 45.0;    // Maximum localizer angle in degrees
    private static final double MIN_ANGLE = -45.0;   // Minimum localizer angle in degrees

    /**
     * Constructs a Localizer that initializes the runway threshold position.
     * The threshold is adjusted by moving back 1250 units on the Z axis to simulate
     * the beginning of the runway.
     *
     * @param runway The runway model containing the initial threshold point
     * @throws IllegalArgumentException if runway is null
     */
    public Localizer(RunwayModel runway) {
        if (runway == null) {
            String message = "Runway model cannot be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        this.runwayThreshold = runway.getThresholdPoint();
        logger.info("Localizer initialized with runway threshold at ({}, {}, {})",
                   runwayThreshold.getX(), runwayThreshold.getY(), runwayThreshold.getZ());
    }

    /**
     * Calculates the localization angle (Localizer) of the aircraft relative to the
     * runway's longitudinal axis. This method measures the angular deviation between
     * the current aircraft position and the runway centerline.
     *
     * @param aircraftPosition The current aircraft position
     * @return The deviation angle from the runway axis in degrees
     * @throws IllegalArgumentException if aircraftPosition is null
     */
    public double calculateLocalizerAngle(Point3DCustom aircraftPosition) {
        if (aircraftPosition == null) {
            String message = "Aircraft position cannot be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        double deltaX = aircraftPosition.getX() - runwayThreshold.getX();
        double deltaZ = aircraftPosition.getZ() - runwayThreshold.getZ();

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        if (distance < MIN_DISTANCE) {
            distance = MIN_DISTANCE; // Prevent division by zero
        }

        // Determine the sign based on which side of the runway the aircraft is on
        double sign = (aircraftPosition.getX() > runwayThreshold.getX()) ? -1 : 1;

        // Calculate the angle using the dot product of the vectors
        double angle = Math.toDegrees(Math.acos(-deltaZ / distance)) * sign;

        // Clamp the angle to valid range
        angle = Math.min(Math.max(angle, MIN_ANGLE), MAX_ANGLE);

        logger.debug("Calculated localizer angle: {} degrees (deltaX: {}, deltaZ: {}, distance: {})",
                    angle, deltaX, deltaZ, distance);
        return angle;
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
        return String.format("Localizer[threshold=(%f, %f, %f)]",
                           runwayThreshold.getX(),
                           runwayThreshold.getY(),
                           runwayThreshold.getZ());
    }
}
