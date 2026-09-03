package com.example.demo.nea_start;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Raycaster extends Line {
    private double distance;
    private boolean verticalWall = false;
    private double angle;

    public Raycaster(Color colour, double startX, double startY, double angle){
        super();

        setStroke(colour);

        updateRay(startX, startY, angle, Main.MAP);
    }

    public Raycaster getRay(){return this;}

    public void updateRay(double x, double y, double angle, int[][] map){
        this.angle = angle;

        setStartX(x);
        setStartY(y);

        DDA(x, y, angle, map);

    }

    public void DDA(double x, double y, double angle, int [][]map){

        // Figure out direction ray will travel
        double rayDirX = Math.sin(Math.toRadians(angle));
        double rayDirY = -Math.cos(Math.toRadians(angle));

        //Find whcih maptile the player is currently on
        int mapX = (int) (x / Main.TILE_SIZE);
        int mapY = (int) (y / Main.TILE_SIZE);

        // Figure out distnace ray has to travel to cross a whole grid square along both axis
        double changeDistX = Math.abs(1 / rayDirX);
        double changeDistY = Math.abs(1 / rayDirY);

        int stepX;
        int stepY;

        //Distance from polayer to first vertical/horizontal girdlines
        double sideDistX;
        double sideDistY;

        //Find out direction of travel along both axis
        if (rayDirX < 0){
            stepX = -1;
            sideDistX = (x / Main.TILE_SIZE - mapX) * changeDistX;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1 - x / Main.TILE_SIZE) * changeDistX;
        }

        if (rayDirY < 0){
            stepY = -1;
            sideDistY = (y / Main.TILE_SIZE - mapY) * changeDistY;
        } else {
            stepY = 1;
            sideDistY = (mapY + 1 - y / Main.TILE_SIZE) * changeDistY;
        }

        //check if ray has hit wall
        boolean hitWall = false;

        // loop to move through map until a wall is hit
        while (!hitWall) {
            // check if vertical grid boundary is closest, else
            if (sideDistX < sideDistY){

                sideDistX += changeDistX;
                mapX += stepX;

                verticalWall = true;

            } else {

                sideDistY += changeDistY;
                mapY += stepY;

                verticalWall = false;
            }

            //Check whether the tile is a wall
            if (mapY < 0 || mapY >= map.length || mapX < 0 || mapX >= map[0].length) {
                hitWall = true;
            } else if (map[mapY][mapX] == 1) {
                hitWall = true;
            }
        }

        // Find the distance from sprite to the wall
        if (verticalWall) {
            distance = sideDistX - changeDistX;
        } else {
            distance = sideDistY - changeDistY;
        }

        distance *= Main.TILE_SIZE;

        double endX = x + rayDirX * distance;
        double endY = y + rayDirY * distance;

        setEndX(endX);
        setEndY(endY);
    }

    public double getAngle() {
        return angle;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isVerticalWall() {
        return verticalWall;
    }
}
