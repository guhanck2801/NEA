package com.example.demo.nea_start;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Renderer extends Group {

    private final double width;
    private final double height;

    private final double FOV = 60;

    public Renderer(double width, double height) {

        this.width = width;
        this.height = height;

        drawBackground();
    }

    private void drawBackground() {

        // Sky
        Rectangle sky = new Rectangle(
                0,
                0,
                width,
                height / 2
        );

        sky.setFill(Color.LIGHTBLUE);

        // Floor
        Rectangle floor = new Rectangle(
                0,
                height / 2,
                width,
                height / 2
        );

        floor.setFill(Color.DARKGRAY);

        getChildren().addAll(sky, floor);
    }

    public void render(Player player) {

        getChildren().clear();

        drawBackground();

        double projectionPlane =
                (width / 2) /
                        Math.tan(Math.toRadians(FOV / 2));

        double columnWidth =
                width / player.ray.size();

        for (int i = 0; i < player.ray.size(); i++) {

            Raycaster ray = player.ray.get(i);

            double distance = ray.getDistance();

            // Correct fish-eye effect
            double angleDifference =
                    Math.toRadians(
                            ray.getAngle() - player.getRotate()
                    );

            double correctedDistance =
                    distance * Math.cos(angleDifference);

            // Prevent division by zero
            if (correctedDistance < 0.1) {
                correctedDistance = 0.1;
            }

            // Calculate projected wall height
            double wallHeight =
                    (Main.TILE_SIZE / correctedDistance)
                            * projectionPlane;

            // Horizontal position of this ray
            double x = i * columnWidth;

            // Centre wall around horizon
            double y =
                    (height - wallHeight) / 2;

            Rectangle wallSlice = new Rectangle(
                    x,
                    y,
                    columnWidth + 1,
                    wallHeight
            );

            // Make vertical and horizontal walls look different
            if (ray.isVerticalWall()) {
                wallSlice.setFill(Color.WHITE);
            } else {
                wallSlice.setFill(Color.GRAY);
            }

            getChildren().add(wallSlice);
        }
    }
}