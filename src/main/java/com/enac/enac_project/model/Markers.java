package com.enac.enac_project.model;

import com.enac.enac_project.model.Point3DCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Markers class manages the detection of marker crossings on the landing runway.
 * The markers OM (Outer Marker), MM (Middle Marker), and IM (Inner Marker) are used
 * to guide the aircraft's approach.
 */
public class Markers {
    private static final Logger logger = LoggerFactory.getLogger(Markers.class);

    private final Point3DCustom outerMarker;
    private final Point3DCustom middleMarker;
    private final Point3DCustom innerMarker;
    private final double detectionRadius;  // Detection radius for each marker

    // Constants for validation
    private static final double MIN_RADIUS = 10.0;    // Minimum detection radius
    private static final double MAX_RADIUS = 5000.0;  // Maximum detection radius

    /**
     * Constructs a Markers object.
     * Initializes the positions of the three markers and their common detection radius.
     *
     * @param outerMarker The position of the Outer Marker
     * @param middleMarker The position of the Middle Marker
     * @param innerMarker The position of the Inner Marker
     * @param detectionRadius The detection radius for the markers
     * @throws IllegalArgumentException if any parameter is null or radius is invalid
     */
    public Markers(Point3DCustom outerMarker, Point3DCustom middleMarker, 
                  Point3DCustom innerMarker, double detectionRadius) {
        // Validate parameters
        if (outerMarker == null || middleMarker == null || innerMarker == null) {
            String message = "Marker positions cannot be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        if (detectionRadius < MIN_RADIUS || detectionRadius > MAX_RADIUS) {
            String message = String.format("Detection radius must be between %.1f and %.1f meters",
                                         MIN_RADIUS, MAX_RADIUS);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        this.outerMarker = outerMarker;
        this.middleMarker = middleMarker;
        this.innerMarker = innerMarker;
        this.detectionRadius = detectionRadius;

        logger.info("Markers initialized with detection radius: {} meters", detectionRadius);
        logger.debug("Outer Marker position: ({}, {}, {})",
                    outerMarker.getX(), outerMarker.getY(), outerMarker.getZ());
        logger.debug("Middle Marker position: ({}, {}, {})",
                    middleMarker.getX(), middleMarker.getY(), middleMarker.getZ());
        logger.debug("Inner Marker position: ({}, {}, {})",
                    innerMarker.getX(), innerMarker.getY(), innerMarker.getZ());
    }

    /**
     * Determines if a point has crossed the Outer Marker (OM).
     *
     * @param position The current position of the object (e.g., aircraft) to test
     * @return true if the point is within the detection radius of the OM, false otherwise
     * @throws IllegalArgumentException if position is null
     */
    public boolean hasPassedOuterMarker(Point3DCustom position) {
        validatePosition(position, "Outer");
        double distance = position.distance(outerMarker);
        boolean passed = distance <= detectionRadius;
        
        logger.debug("Distance to Outer Marker: {} meters (passed: {})", distance, passed);
        return passed;
    }

    /**
     * Determines if a point has crossed the Middle Marker (MM).
     *
     * @param position The current position of the object (e.g., aircraft) to test
     * @return true if the point is within the detection radius of the MM, false otherwise
     * @throws IllegalArgumentException if position is null
     */
    public boolean hasPassedMiddleMarker(Point3DCustom position) {
        validatePosition(position, "Middle");
        double distance = position.distance(middleMarker);
        boolean passed = distance <= detectionRadius;
        
        logger.debug("Distance to Middle Marker: {} meters (passed: {})", distance, passed);
        return passed;
    }

    /**
     * Determines if a point has crossed the Inner Marker (IM).
     *
     * @param position The current position of the object (e.g., aircraft) to test
     * @return true if the point is within the detection radius of the IM, false otherwise
     * @throws IllegalArgumentException if position is null
     */
    public boolean hasPassedInnerMarker(Point3DCustom position) {
        validatePosition(position, "Inner");
        double distance = position.distance(innerMarker);
        boolean passed = distance <= detectionRadius;
        
        logger.debug("Distance to Inner Marker: {} meters (passed: {})", distance, passed);
        return passed;
    }

    /**
     * Validates the position parameter.
     *
     * @param position The position to validate
     * @param markerName The name of the marker being checked
     * @throws IllegalArgumentException if position is null
     */
    private void validatePosition(Point3DCustom position, String markerName) {
        if (position == null) {
            String message = String.format("Position for %s Marker check cannot be null", markerName);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Gets the Outer Marker position.
     *
     * @return The Outer Marker position
     */
    public Point3DCustom getOuterMarker() {
        return outerMarker;
    }

    /**
     * Gets the Middle Marker position.
     *
     * @return The Middle Marker position
     */
    public Point3DCustom getMiddleMarker() {
        return middleMarker;
    }

    /**
     * Gets the Inner Marker position.
     *
     * @return The Inner Marker position
     */
    public Point3DCustom getInnerMarker() {
        return innerMarker;
    }

    /**
     * Gets the detection radius.
     *
     * @return The detection radius in meters
     */
    public double getDetectionRadius() {
        return detectionRadius;
    }

    @Override
    public String toString() {
        return String.format("Markers[radius=%.1f, OM=(%f, %f, %f), MM=(%f, %f, %f), IM=(%f, %f, %f)]",
                           detectionRadius,
                           outerMarker.getX(), outerMarker.getY(), outerMarker.getZ(),
                           middleMarker.getX(), middleMarker.getY(), middleMarker.getZ(),
                           innerMarker.getX(), innerMarker.getY(), innerMarker.getZ());
    }
}
