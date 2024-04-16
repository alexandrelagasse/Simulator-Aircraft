package com.example.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;



/**
 * La classe Aircraft modélise un avion avec ses propriétés de position et de mouvement dans un environnement 3D.
 * Elle gère les propriétés de l'avion telles que la vitesse, l'orientation (lacet, tangage, roulis) et la position,
 * ainsi que l'interaction avec des systèmes de navigation comme l'ILS et le PAPI.
 */
public class Aircraft extends Point3DCustom {
    private DoubleProperty speed = new SimpleDoubleProperty();   // Vitesse de l'avion
    private DoubleProperty yaw = new SimpleDoubleProperty();     // Lacet (rotation autour de l'axe Y)
    private DoubleProperty pitch = new SimpleDoubleProperty();   // Tangage (rotation autour de l'axe X)
    private DoubleProperty roll = new SimpleDoubleProperty();    // Roulis (rotation autour de l'axe Z)
    private ILS ils;                                             // Système d'atterrissage aux instruments
    private Papi papi;
    private static final double DEFAULT_X = 0;                   // Position X par défaut
    private static final double DEFAULT_Y = -250;                // Position Y par défaut
    private static final double DEFAULT_Z = 0;               // Position Z par défaut
    private static final double DEFAULT_SPEED = 50;              // Vitesse par défaut
    private RunwayModel runwayModel;                           // Point de référence de la piste

    /**
     * Constructeur de la classe Aircraft.
     */
    public Aircraft() {
        super(DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
        runwayModel = new RunwayModel(0, 0, 7000, 400, 2, 2500);
        GlidePath glidePath = new GlidePath(runwayModel);
        ils = new ILS(runwayModel, glidePath);
        papi = new Papi(runwayModel, glidePath);
        this.speed.set(DEFAULT_SPEED);
    }

    public double getSpeed() { return speed.get(); }
    public void setSpeed(double value) { speed.set(value); }
    public DoubleProperty speedProperty() { return speed; }

    /**
     * Ralentit l'avion d'une certaine quantité.
     * @param amount La quantité à décélérer.
     */
    public void decelerate(double amount) {
        double newSpeed = Math.max(10, getSpeed() - amount);
        setSpeed(newSpeed);
    }

    /**
     * Met à jour la position de l'avion en fonction de sa vitesse et de son orientation.
     */
    public void updatePosition() {
        // Calcule le vecteur de direction basé sur l'orientation de l'avion (yaw, pitch, roll)
        Point3DCustom direction = calculateDirectionVector(getYaw(), getPitch(), getRoll());

        // Met à jour la position de l'avion en fonction de la direction et de la vitesse
        setX(getX() + direction.getX() * getSpeed());
        setY(getY() + direction.getY() * getSpeed());
        setZ(getZ() + direction.getZ() * getSpeed());

        // Applique la décélération
        decelerate(0.1);
    }

    /**
     * Calcule le vecteur de direction de l'avion basé sur ses angles de lacet, tangage et roulis.
     * Cette méthode utilise des transformations trigonométriques pour convertir les angles en radians
     * et calculer les composantes x, y et z du vecteur de direction dans l'espace 3D.
     *
     * @param yaw L'angle de lacet de l'avion (rotation autour de l'axe vertical).
     * @param pitch L'angle de tangage de l'avion (inclinaison avant ou arrière).
     * @param roll L'angle de roulis de l'avion (rotation autour de l'axe longitudinal).
     * @return Un nouveau Point3DCustom qui représente le vecteur de direction dans l'espace 3D.
     */
    private Point3DCustom calculateDirectionVector(double yaw, double pitch, double roll) {
        // Convertit les angles en radians pour le calcul
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        // Calcule les composantes du vecteur de direction
        double x = Math.cos(pitchRad) * Math.sin(yawRad);
        double y = Math.sin(pitchRad);
        double z = Math.cos(pitchRad) * Math.cos(yawRad);

        // Retourne un nouveau Point3DCustom représentant le vecteur de direction
        return new Point3DCustom(x, y, z);
    }


    /**
     * Calcule la différence d'altitude entre l'avion et la piste.
     * @return La différence d'altitude en unités.
     */
    public double calculateAltitudeDifference() {
        // Obtenez la position Z de la piste depuis l'ILS
        double runwayZ = runwayModel.getY();

        // Calculez la différence d'altitude
        return getY() - runwayZ;
    }


    /**
     * Obtient l'angle de lacet actuel de l'avion.
     * Le lacet est la rotation autour de l'axe vertical de l'avion.
     * @return L'angle de lacet en degrés.
     */
    public double getYaw() { return yaw.get(); }

    /**
     * Définit l'angle de lacet de l'avion.
     * @param value Nouvel angle de lacet en degrés.
     */
    public void setYaw(double value) { yaw.set(value); }

    /**
     * Propriété JavaFX pour l'angle de lacet, permettant le binding et d'autres interactions.
     * @return La propriété de lacet.
     */
    public DoubleProperty yawProperty() { return yaw; }

    /**
     * Obtient l'angle de tangage actuel de l'avion.
     * Le tangage est l'inclinaison verticale de l'avion, affectant la montée et la descente.
     * @return L'angle de tangage en degrés.
     */
    public double getPitch() { return pitch.get(); }

    /**
     * Définit l'angle de tangage de l'avion.
     * @param value Nouvel angle de tangage en degrés.
     */
    public void setPitch(double value) { pitch.set(value); }

    /**
     * Propriété JavaFX pour l'angle de tangage, permettant le binding et d'autres interactions.
     * @return La propriété de tangage.
     */
    public DoubleProperty pitchProperty() { return pitch; }

    /**
     * Obtient l'angle de roulis actuel de l'avion.
     * Le roulis est la rotation autour de l'axe longitudinal de l'avion.
     * @return L'angle de roulis en degrés.
     */
    public double getRoll() { return roll.get(); }

    /**
     * Définit l'angle de roulis de l'avion.
     * @param value Nouvel angle de roulis en degrés.
     */
    public void setRoll(double value) { roll.set(value); }

    /**
     * Propriété JavaFX pour l'angle de roulis, permettant le binding et d'autres interactions.
     * @return La propriété de roulis.
     */
    public DoubleProperty rollProperty() { return roll; }

    /**
     * Obtient le système d'atterrissage aux instruments (ILS) associé à cet avion.
     * L'ILS aide à l'approche et à l'atterrissage en fournissant des informations sur la trajectoire de descente et l'alignement avec la piste.
     * @return L'objet ILS associé à l'avion.
     */
    public ILS getILS() { return ils; }


    /**
     * Réinitialise l'état de l'avion aux valeurs par défaut.
     */
    public void reset() {

        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        setZ(DEFAULT_Z);
        setSpeed(DEFAULT_SPEED);
        setRoll(0);
        setPitch(0);
        setYaw(0);

    }

    public Papi getPapi() { return papi; }

    public RunwayModel getRunwayModel() { return runwayModel; }

}
