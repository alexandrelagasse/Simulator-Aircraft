package com.enac.enac_project.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an Instrument Landing System (ILS) for aircraft navigation.
 * The ILS provides guidance for aircraft during approach and landing.
 */
public class ILS {
    private static final Logger logger = LoggerFactory.getLogger(ILS.class);

    private final GlidePath glidePath;      // The glide path component of the ILS
    private final Localizer localizer;      // The localizer component of the ILS
    private final Markers markers;          // The markers component of the ILS
    private final double descentAngle;      // The descent angle for the glide path

    // Constants for ILS configuration
    private static final double DEFAULT_DESCENT_ANGLE = 3.0;
    private static final double DETECTION_RADIUS = 1000.0;
    private static final double DEGREES_PER_PIXEL = 4.5;  // 45 pixels for 10 degrees
    private static final double MAX_BAR_DISPLACEMENT = 22.5;
    private static final double MIN_BAR_DISPLACEMENT = -22.5;

    // Marker positions
    private static final Point3DCustom OM_POSITION = new Point3DCustom(0, 0, 0);      // Outer Marker position
    private static final Point3DCustom MM_POSITION = new Point3DCustom(0, 0, 6000);   // Middle Marker position
    private static final Point3DCustom IM_POSITION = new Point3DCustom(0, 0, 6900);   // Inner Marker position

    /**
     * Constructs an ILS object with the specified runway point and glide path.
     *
     * @param runway    The runway model associated with the ILS
     * @param glidePath The glide path of the ILS
     * @throws IllegalArgumentException if runway or glidePath is null
     */
    public ILS(RunwayModel runway, GlidePath glidePath) {
        if (runway == null || glidePath == null) {
            String message = "Runway and glide path must not be null";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        this.glidePath = glidePath;
        this.localizer = new Localizer(runway);
        this.markers = new Markers(OM_POSITION, MM_POSITION, IM_POSITION, DETECTION_RADIUS);
        this.descentAngle = DEFAULT_DESCENT_ANGLE;

        logger.info("ILS initialized with runway at ({}, {}, {})",
                   runway.getX(), runway.getY(), runway.getZ());
    }

    /**
     * Checks if the aircraft has crossed the Outer Marker (OM).
     *
     * @param position The aircraft position
     * @return true if the aircraft has crossed the OM, false otherwise
     */
    public boolean hasPassedOuterMarker(Point3DCustom position) {
        boolean passed = markers.hasPassedOuterMarker(position);
        logger.debug("Aircraft at ({}, {}, {}) has {} passed outer marker",
                    position.getX(), position.getY(), position.getZ(),
                    passed ? "" : "not");
        return passed;
    }

    /**
     * Checks if the aircraft has crossed the Middle Marker (MM).
     *
     * @param position The aircraft position
     * @return true if the aircraft has crossed the MM, false otherwise
     */
    public boolean hasPassedMiddleMarker(Point3DCustom position) {
        boolean passed = markers.hasPassedMiddleMarker(position);
        logger.debug("Aircraft at ({}, {}, {}) has {} passed middle marker",
                    position.getX(), position.getY(), position.getZ(),
                    passed ? "" : "not");
        return passed;
    }

    /**
     * Checks if the aircraft has crossed the Inner Marker (IM).
     *
     * @param position The aircraft position
     * @return true if the aircraft has crossed the IM, false otherwise
     */
    public boolean hasPassedInnerMarker(Point3DCustom position) {
        boolean passed = markers.hasPassedInnerMarker(position);
        logger.debug("Aircraft at ({}, {}, {}) has {} passed inner marker",
                    position.getX(), position.getY(), position.getZ(),
                    passed ? "" : "not");
        return passed;
    }

    /**
     * Calculates the localizer bar position.
     *
     * @param aircraftPosition The aircraft position
     * @return The localizer bar displacement in pixels
     */
    public double calculateLocalizerBar(Point3DCustom aircraftPosition) {
        if (aircraftPosition == null) {
            logger.error("Aircraft position cannot be null");
            throw new IllegalArgumentException("Aircraft position cannot be null");
        }

        double angle = localizer.calculateLocalizerAngle(aircraftPosition);
        double displacement = angle * DEGREES_PER_PIXEL;
        displacement = Math.min(Math.max(displacement, MIN_BAR_DISPLACEMENT), MAX_BAR_DISPLACEMENT);

        logger.debug("Localizer angle: {}, displacement: {} pixels", angle, displacement);
        return displacement;
    }

    /**
     * Calculates the glide path bar position.
     *
     * @param aircraftPosition The aircraft position
     * @return The glide path bar displacement in pixels
     */
    public double calculateGlidePathBar(Point3DCustom aircraftPosition) {
        if (aircraftPosition == null) {
            logger.error("Aircraft position cannot be null");
            throw new IllegalArgumentException("Aircraft position cannot be null");
        }

        double angle = glidePath.calculateGlideSlopeAngle(aircraftPosition);
        double displacement = (angle - descentAngle) * DEGREES_PER_PIXEL;
        displacement = Math.min(Math.max(displacement, MIN_BAR_DISPLACEMENT), MAX_BAR_DISPLACEMENT);

        logger.debug("Glide path angle: {}, displacement: {} pixels", angle, displacement);
        return displacement;
    }

    /**
     * Calculates the DME (Distance Measuring Equipment) distance.
     *
     * @param position The position for which to calculate the DME distance
     * @return The DME distance from the specified position
     */
    public double calculateDME(Point3DCustom position) {
        if (position == null) {
            logger.error("Position cannot be null");
            throw new IllegalArgumentException("Position cannot be null");
        }

        double distance = glidePath.calculateDME(position);
        logger.debug("DME distance calculated: {} meters", distance);
        return distance;
    }

    /**
     * Gets the current descent angle.
     *
     * @return The descent angle in degrees
     */
    public double getDescentAngle() {
        return descentAngle;
    }

    /**
     * Gets the glide path component.
     *
     * @return The glide path
     */
    public GlidePath getGlidePath() {
        return glidePath;
    }

    /**
     * Gets the localizer component.
     *
     * @return The localizer
     */
    public Localizer getLocalizer() {
        return localizer;
    }

    /**
     * Gets the markers component.
     *
     * @return The markers
     */
    public Markers getMarkers() {
        return markers;
    }
}
