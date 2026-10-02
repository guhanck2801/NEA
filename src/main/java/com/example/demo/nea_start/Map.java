package com.example.demo.nea_start;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.example.demo.nea_start.Main.MAP;

public class Map {

    public static int[][] MAP;
    public static final int TILE_SIZE = 64;

    public Map(int [][] MAP) {
        this.MAP = MAP;
    }

    static void drawMap(Group topDownView) {
        for (int row = 0; row < MAP.length; row++) {
            for (int col = 0; col < MAP[row].length; col++) {

                final int GAP = 1;

                Rectangle wall = new Rectangle(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE - GAP,
                        TILE_SIZE - GAP
                );
                if (MAP[row][col] == 1) {
                    wall.setFill(Color.WHITE);
                } else {
                    wall.setFill((Color.BLACK));
                }
                topDownView.getChildren().add(wall);
            }
        }
    }
}
