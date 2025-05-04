package com.enac.enac_project.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * La classe Point3DCustom représente un point dans un espace tridimensionnel avec des propriétés liées aux axes X, Y et Z.
 * Elle permet la manipulation facile des coordonnées et fournit des méthodes pour calculer la distance et l'angle entre les points.
 */
public class Point3DCustom {
    protected final DoubleProperty x = new SimpleDoubleProperty();
    protected final DoubleProperty y = new SimpleDoubleProperty();
    protected final DoubleProperty z = new SimpleDoubleProperty();

    /**
     * Constructeur qui initialise un point avec des coordonnées spécifiées.
     *
     * @param x Coordonnée sur l'axe X.
     * @param y Coordonnée sur l'axe Y.
     * @param z Coordonnée sur l'axe Z.
     */
    public Point3DCustom(double x, double y, double z) {
        this.x.set(x);
        this.y.set(y);
        this.z.set(z);
    }

    /**
     * Obtient la valeur de la coordonnée X.
     * @return La valeur de la coordonnée X.
     */
    public double getX() { return x.get(); }

    /**
     * Définit la valeur de la coordonnée X.
     * @param value Nouvelle valeur pour la coordonnée X.
     */
    public void setX(double value) { x.set(value); }

    /**
     * Propriété JavaFX pour la coordonnée X.
     * @return La propriété de la coordonnée X.
     */
    public DoubleProperty xProperty() { return x; }

    /**
     * Obtient la valeur de la coordonnée Y.
     * @return La valeur de la coordonnée Y.
     */
    public double getY() { return y.get(); }

    /**
     * Définit la valeur de la coordonnée Y.
     * @param value Nouvelle valeur pour la coordonnée Y.
     */
    public void setY(double value) { y.set(value); }

    /**
     * Propriété JavaFX pour la coordonnée Y.
     * @return La propriété de la coordonnée Y.
     */
    public DoubleProperty yProperty() { return y; }

    /**
     * Obtient la valeur de la coordonnée Z.
     * @return La valeur de la coordonnée Z.
     */
    public double getZ() { return z.get(); }

    /**
     * Définit la valeur de la coordonnée Z.
     * @param value Nouvelle valeur pour la coordonnée Z.
     */
    public void setZ(double value) { z.set(value); }

    /**
     * Propriété JavaFX pour la coordonnée Z.
     * @return La propriété de la coordonnée Z.
     */
    public DoubleProperty zProperty() { return z; }

    /**
     * Calcule la distance euclidienne entre ce point et un autre point spécifié.
     *
     * @param other Autre point avec lequel calculer la distance.
     * @return La distance euclidienne entre les deux points.
     */
    public double distance(Point3DCustom other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
