package com.example.demo.nea_start;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Raycaster extends Line {

    double length = 500;

    public Raycaster(Color colour, double startX, double startY, double angle){
        super();

        setStroke(colour);

        updateRay(startX, startY, angle);
    }

    public Raycaster getRay(){
        return this;
    }

    public void updateRay(double x, double y, double angle){
        double radians = Math.toRadians(angle);

        setStartX(x);
        setStartY(y);

        setEndX(x + Math.sin(radians) * length);
        setEndY(y - Math.cos(radians) * length);
    }

    public void DDA(double x, double y, double angle){

    }
}
