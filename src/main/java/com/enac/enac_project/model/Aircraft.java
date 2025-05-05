package com.enac.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Aircraft class models an aircraft with its position and movement properties in a 3D environment.
 * It manages aircraft properties such as speed, orientation (yaw, pitch, roll) and position,
 * as well as interaction with navigation systems like ILS and PAPI.
 */
public class Aircraft extends Point3DCustom {
    private static final Logger logger = LoggerFactory.getLogger(Aircraft.class);
    
    private final DoubleProperty speed = new SimpleDoubleProperty();
    private final DoubleProperty yaw = new SimpleDoubleProperty();
    private final DoubleProperty pitch = new SimpleDoubleProperty();
    private final DoubleProperty roll = new SimpleDoubleProperty();
    private final ILS ils;
    private final Papi papi;
    private final RunwayModel runwayModel;
    
    // Default values
    private static final double DEFAULT_X = 0;
    private static final double DEFAULT_Y = -250;
    private static final double DEFAULT_Z = 0;
    private static final double DEFAULT_SPEED = 50;
    private static final double MIN_SPEED = 0;
    private static final double MAX_SPEED = 500;
    private static final double MIN_ANGLE = -180;
    private static final double MAX_ANGLE = 180;

    /**
     * Constructor for the Aircraft class.
     * Initializes the aircraft with default values and creates navigation systems.
     */
    public Aircraft() {
        super(DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
        try {
            runwayModel = new RunwayModel(0, 0, 8250, 400, 2, 2500);
            GlidePath glidePath = new GlidePath(runwayModel);
            ils = new ILS(runwayModel, glidePath);
            papi = new Papi(runwayModel, glidePath);
            this.speed.set(DEFAULT_SPEED);
            logger.info("Aircraft initialized with default values");
        } catch (Exception e) {
            logger.error("Failed to initialize aircraft", e);
            throw new RuntimeException("Failed to initialize aircraft", e);
        }
    }

    public double getSpeed() { return speed.get(); }
    
    public void setSpeed(double value) {
        if (value < MIN_SPEED || value > MAX_SPEED) {
            logger.warn("Attempted to set speed to {} which is outside valid range [{}, {}]", 
                value, MIN_SPEED, MAX_SPEED);
            return;
        }
        speed.set(value);
    }

    /**
     * Decelerates the aircraft by a specified amount.
     * @param amount The amount to decelerate by
     * @param minSpeed The minimum speed to maintain
     */
    public void decelerate(double amount, double minSpeed) {
        double newSpeed = Math.max(minSpeed, getSpeed() - amount);
        setSpeed(newSpeed);
        logger.debug("Aircraft decelerated to speed: {}", newSpeed);
    }

    /**
     * Updates the aircraft's position based on its speed and orientation.
     * Calculates the direction vector based on aircraft orientation (yaw, pitch, roll)
     * and adjusts position accordingly. Also applies deceleration.
     */
    public void updatePosition() {
        try {
            Point3DCustom direction = calculateDirectionVector(getYaw(), getPitch(), getRoll());

            double newX = getX() + direction.getX() * getSpeed();
            double newY = getY() + direction.getY() * getSpeed();
            double newZ = getZ() + direction.getZ() * getSpeed();

            if (isOnRunway(newX, newY, newZ, 20)) {
                if (getSpeed() >= 0) {
                    setX(newX);
                    setZ(newZ);
                    decelerate(1, MIN_SPEED);
                }
            } else {
                setX(newX);
                setY(newY);
                setZ(newZ);
                decelerate(0.1, 10);
            }
            
            logger.debug("Aircraft position updated to: x={}, y={}, z={}", newX, newY, newZ);
        } catch (Exception e) {
            logger.error("Error updating aircraft position", e);
        }
    }

    /**
     * Checks if the aircraft coordinates are on or near the runway, including altitude.
     * @param x The x coordinate of the aircraft
     * @param y The y coordinate of the aircraft
     * @param z The z coordinate of the aircraft
     * @param lowerAltitudeLimit The safety margin below which the aircraft is considered to be on the runway
     * @return true if the aircraft is on or near the runway, including altitude, false otherwise
     */
    private boolean isOnRunway(double x, double y, double z, double lowerAltitudeLimit) {
        double runwayStartX = runwayModel.getX() - runwayModel.getWidth() / 2;
        double runwayEndX = runwayModel.getX() + runwayModel.getWidth() / 2;
        double runwayStartZ = runwayModel.getZ() - runwayModel.getLength() / 2;
        double runwayEndZ = runwayModel.getZ() + runwayModel.getLength() / 2;
        double lowerAltitudeLimitValue = runwayModel.getY() - lowerAltitudeLimit;

        return (x >= runwayStartX && x <= runwayEndX) &&
               (z >= runwayStartZ && z <= runwayEndZ) &&
               (y >= lowerAltitudeLimitValue);
    }

    /**
     * Calculates the direction vector of the aircraft based on its yaw, pitch, and roll angles.
     * This method uses trigonometric transformations to convert angles to radians
     * and calculate the x, y, and z components of the direction vector in 3D space.
     *
     * @param yaw The yaw angle of the aircraft (rotation around the vertical axis)
     * @param pitch The pitch angle of the aircraft (forward or backward tilt)
     * @param roll The roll angle of the aircraft (rotation around the longitudinal axis)
     * @return A new Point3DCustom representing the direction vector in 3D space
     */
    private Point3DCustom calculateDirectionVector(double yaw, double pitch, double roll) {
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        double x = Math.cos(pitchRad) * Math.sin(yawRad);
        double y = Math.sin(pitchRad);
        double z = Math.cos(pitchRad) * Math.cos(yawRad);

        return new Point3DCustom(x, y, z);
    }

    /**
     * Calculates the altitude difference between the aircraft and the runway.
     * @return The altitude difference in units
     */
    public double calculateAltitudeDifference() {
        double runwayZ = runwayModel.getY();
        double altitude = Math.abs(getY() - runwayZ);
        logger.debug("Altitude difference calculated: {}", altitude);
        return altitude;
    }

    public double getYaw() { return yaw.get(); }
    
    public void setYaw(double value) {
        if (value < MIN_ANGLE || value > MAX_ANGLE) {
            logger.warn("Attempted to set yaw to {} which is outside valid range [{}, {}]", 
                value, MIN_ANGLE, MAX_ANGLE);
            return;
        }
        yaw.set(value);
    }

    public double getPitch() { return pitch.get(); }
    
    public void setPitch(double value) {
        if (value < MIN_ANGLE || value > MAX_ANGLE) {
            logger.warn("Attempted to set pitch to {} which is outside valid range [{}, {}]", 
                value, MIN_ANGLE, MAX_ANGLE);
            return;
        }
        pitch.set(value);
    }

    public double getRoll() { return roll.get(); }
    
    public void setRoll(double value) {
        if (value < MIN_ANGLE || value > MAX_ANGLE) {
            logger.warn("Attempted to set roll to {} which is outside valid range [{}, {}]", 
                value, MIN_ANGLE, MAX_ANGLE);
            return;
        }
        roll.set(value);
    }

    public ILS getILS() { return ils; }

    /**
     * Resets the aircraft state to default values.
     */
    public void reset() {
        try {
            setX(DEFAULT_X);
            setY(DEFAULT_Y);
            setZ(DEFAULT_Z);
            setSpeed(DEFAULT_SPEED);
            setRoll(0);
            setPitch(0);
            setYaw(0);
            logger.info("Aircraft state reset to default values");
        } catch (Exception e) {
            logger.error("Error resetting aircraft state", e);
        }
    }

    public Papi getPapi() { return papi; }

    public RunwayModel getRunwayModel() { return runwayModel; }
}
