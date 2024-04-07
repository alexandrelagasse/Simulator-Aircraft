package com.example.enac_project.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.PointLight;
import javafx.scene.shape.Sphere;

public class RunwayView extends Group {
    public RunwayView() {
        super();
        placerLumiere();
        dessinerPiste();
    }

    private void dessinerPiste() {
        Box piste = new Box(400, 2, 2500);
        piste.setCullFace(CullFace.BACK);
        piste.setDrawMode(DrawMode.FILL);
        piste.setMaterial(new PhongMaterial(Color.BLUE));
        this.getChildren().add(piste);
    }

    private void placerLumiere() {
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(500);
        pointLight.setTranslateY(-700);
        pointLight.setTranslateZ(-600);
        this.getChildren().addAll(pointLight);
    }

    private void tracerRepere() {
        Sphere zero=new Sphere(20);
        zero.setMaterial(new PhongMaterial(Color.RED));
        this.getChildren().add(zero);
        Box axeX=new Box(500,3,3);
        axeX.setCullFace(CullFace.BACK);
        axeX.setDrawMode(DrawMode.FILL);
        axeX.setTranslateX(250);
        axeX.setMaterial(new PhongMaterial(Color.BLUE));
        this.getChildren().add(axeX);
        Box axeY=new Box(3,500,3);
        axeY.setCullFace(CullFace.BACK);
        axeY.setDrawMode(DrawMode.FILL);
        axeY.setTranslateY(250);
        axeY.setMaterial(new PhongMaterial(Color.RED));
        this.getChildren().add(axeY);
        Box axeZ=new Box(3,3,500);
        axeZ.setCullFace(CullFace.BACK);
        axeZ.setDrawMode(DrawMode.FILL);
        axeZ.setTranslateZ(250);
        axeZ.setMaterial(new PhongMaterial(Color.GREEN));
        this.getChildren().add(axeZ);
    }
}
