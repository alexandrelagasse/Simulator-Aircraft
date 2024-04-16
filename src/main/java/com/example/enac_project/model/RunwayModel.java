package com.example.enac_project.model;

/**
 * La classe RunwayModel représente une piste d'atterrissage avec des dimensions spécifiques et une position définie.
 * Elle hérite de Point3DCustom pour utiliser sa position comme seuil de la piste.
 */
public class RunwayModel extends Point3DCustom {
    private double width;
    private double height;
    private double length;

    /**
     * Constructeur de RunwayModel qui initialise la piste avec une position et des dimensions spécifiques.
     *
     * @param x La coordonnée x du seuil de la piste.
     * @param y La coordonnée y du seuil de la piste.
     * @param z La coordonnée z du seuil de la piste.
     * @param width La largeur de la piste.
     * @param height La hauteur de la piste.
     * @param length La longueur de la piste.
     */
    public RunwayModel(double x, double y, double z, double width, double height, double length) {
        super(x, y, z);
        this.width = width;
        this.height = height;
        this.length = length;
    }

    /**
     * Obtient le point de seuil de la piste, qui est la position de base héritée de Point3DCustom.
     *
     * @return Le point de seuil de la piste.
     */
    public Point3DCustom getThresholdPoint() {
        return new Point3DCustom(getX(), getY(), getZ() - length / 2);
    }

    /**
     * Obtient le point d'origine de la piste, qui est calculé en ajoutant la longueur de la piste à la position de seuil.
     *
     * @return Le point d'origine de la piste, situé à l'opposé du seuil.
     */
    public Point3DCustom getOriginPoint() {
        return new Point3DCustom(getX(), getY(), getZ());
    }

    // Méthodes getter pour les dimensions de la piste
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getLength() { return length; }
}
