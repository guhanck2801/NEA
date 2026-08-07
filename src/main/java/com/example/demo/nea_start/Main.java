package com.example.demo.nea_start;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    // assign the size of the walls
    public static final int screenWidth = 1024;
    public static final int screenHeight = 512;

    public static final int TILE_SIZE = 64;

    // create a map, 1 represents a wall, 0 represents empty space to walk in
    public static final int[][] MAP = {
            {1,1,1,1,1,1,1,1},
            {1,0,1,0,0,0,0,1},
            {1,0,1,0,0,0,0,1},
            {1,0,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,0,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1}
    };

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root,screenWidth, screenHeight);

        // Draw the walls
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
                root.getChildren().add(wall);
            }
        }

        // Add a player
        Player player = new Player(Color.YELLOW, 300, 300, 8);
        root.getChildren().add(player);
        for (int i = 0; i < player.ray.size(); i++) {
            root.getChildren().add(player.ray.get(i).getRay());
        }

        // Add the checks to see if the player has pressed a key
        scene.setOnKeyPressed(e -> player.keyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> player.keyReleased(e.getCode()));

        // create game loop for player movement
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.movement(MAP);
            }
        }.start();

        scene.setFill(Color.GRAY);

        // Build the scene
        stage.setScene(scene);
        stage.setTitle("Demo");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}