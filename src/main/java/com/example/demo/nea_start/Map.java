package com.example.demo.nea_start;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Map {

    public static int[][] MAP;
    public static final int TILE_SIZE = 64;

    boolean openMap;

    // Get Map input
    public Map(int [][] MAP) {
        this.MAP = MAP;
    }

    // loop through map and draw depending on current number
    static void drawMap(Group topDownView) {
        for (int row = 0; row < MAP.length; row++) {
            for (int col = 0; col < MAP[row].length; col++) {

                // add grid lines between tiles
                final int GAP = 1;

                // create tile
                Rectangle wall = new Rectangle(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE - GAP,
                        TILE_SIZE - GAP
                );

                if (MAP[row][col] == 1) {
                    // wall
                    wall.setFill(Color.WHITE);
                } else {
                    // Empty space
                    wall.setFill((Color.BLACK));
                }
                topDownView.getChildren().add(wall);
            }
        }
    }
    public void keyPressed(KeyCode key) {
        switch (key) {
            case TAB -> openMap = true;
        }
    }

    // say player stop moving if key has been released
    public void keyReleased(KeyCode key) {
        switch (key) {
            case TAB -> openMap = false;
        }
    }
}
