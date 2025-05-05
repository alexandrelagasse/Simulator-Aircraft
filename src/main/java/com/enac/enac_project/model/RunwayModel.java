package com.enac.enac_project.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RunwayModel class represents a landing runway with specific dimensions and a defined position.
 * It inherits from Point3DCustom to use its position as the runway threshold.
 */
public class RunwayModel extends Point3DCustom {
    private static final Logger logger = LoggerFactory.getLogger(RunwayModel.class);

    private final double width;
    private final double height;
    private final double length;

    // Minimum and maximum values for runway dimensions (in meters)
    private static final double MIN_WIDTH = 30;    // Minimum runway width
    private static final double MAX_WIDTH = 1000;  // Maximum runway width
    private static final double MIN_HEIGHT = 0;    // Minimum runway height
    private static final double MAX_HEIGHT = 10;   // Maximum runway height
    private static final double MIN_LENGTH = 1000; // Minimum runway length
    private static final double MAX_LENGTH = 10000;// Maximum runway length

    /**
     * Constructor for RunwayModel that initializes the runway with a specific position and dimensions.
     *
     * @param x The x-coordinate of the runway threshold
     * @param y The y-coordinate of the runway threshold
     * @param z The z-coordinate of the runway threshold
     * @param width The width of the runway (must be between MIN_WIDTH and MAX_WIDTH)
     * @param height The height of the runway (must be between MIN_HEIGHT and MAX_HEIGHT)
     * @param length The length of the runway (must be between MIN_LENGTH and MAX_LENGTH)
     * @throws IllegalArgumentException if any dimension is outside its valid range
     */
    public RunwayModel(double x, double y, double z, double width, double height, double length) {
        super(x, y, z);
        
        // Validate dimensions
        validateDimension("width", width, MIN_WIDTH, MAX_WIDTH);
        validateDimension("height", height, MIN_HEIGHT, MAX_HEIGHT);
        validateDimension("length", length, MIN_LENGTH, MAX_LENGTH);

        this.width = width;
        this.height = height;
        this.length = length;

        logger.info("Runway created at position ({}, {}, {}) with dimensions: width={}, height={}, length={}",
                   x, y, z, width, height, length);
    }

    /**
     * Validates that a dimension is within its specified range.
     * 
     * @param dimensionName The name of the dimension being validated
     * @param value The value to validate
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @throws IllegalArgumentException if the value is outside the valid range
     */
    private void validateDimension(String dimensionName, double value, double min, double max) {
        if (value < min || value > max) {
            String message = String.format("%s must be between %.2f and %.2f, but was %.2f",
                                        dimensionName, min, max, value);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Gets the threshold point of the runway, which is the base position inherited from Point3DCustom.
     *
     * @return The runway threshold point
     */
    public Point3DCustom getThresholdPoint() {
        Point3DCustom thresholdPoint = new Point3DCustom(getX(), getY(), getZ() - length / 2);
        logger.debug("Calculated threshold point: ({}, {}, {})",
                    thresholdPoint.getX(), thresholdPoint.getY(), thresholdPoint.getZ());
        return thresholdPoint;
    }

    /**
     * Gets the center point of the runway.
     *
     * @return The runway center point
     */
    public Point3DCustom getCenterPoint() {
        Point3DCustom centerPoint = new Point3DCustom(getX(), getY(), getZ());
        logger.debug("Calculated center point: ({}, {}, {})",
                    centerPoint.getX(), centerPoint.getY(), centerPoint.getZ());
        return centerPoint;
    }

    /**
     * Gets the touchdown point of the runway.
     *
     * @return The runway touchdown point
     */
    public Point3DCustom getTouchdownPoint() {
        Point3DCustom touchdownPoint = new Point3DCustom(getX(), getY(), getZ() - length / 3);
        logger.debug("Calculated touchdown point: ({}, {}, {})",
                    touchdownPoint.getX(), touchdownPoint.getY(), touchdownPoint.getZ());
        return touchdownPoint;
    }

    // Getter methods for runway dimensions
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getLength() { return length; }

    @Override
    public String toString() {
        return String.format("RunwayModel[pos=(%f, %f, %f), width=%f, height=%f, length=%f]",
                           getX(), getY(), getZ(), width, height, length);
    }
}
