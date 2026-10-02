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

    // create a map, 1 represents a wall, 0 represents empty space to walk in
    public static final int[][] MAP = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
            {1,0,1,0,1,0,1,1,1,0,1,0,1,1,0,1},
            {1,0,1,0,0,0,0,0,1,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,0,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
            {1,0,0,1,1,1,0,0,0,0,1,0,1,1,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    @Override
    public void start(Stage stage) {

        Group topDownView = new Group();
        Group firstPersonView = new Group();
        Scene scene = new Scene(firstPersonView,screenWidth, screenHeight);
        Scene scene2 = new Scene(topDownView, screenWidth, screenHeight);

        // draw the map
        Map map = new Map(MAP);
        map.drawMap(topDownView);

        // Add a player
        Player player = new Player(Color.YELLOW, 92, 92, 8);
        topDownView.getChildren().add(player);
        for (int i = 0; i < player.ray.size(); i++) {
            topDownView.getChildren().add(player.ray.get(i).getRay());
        }

        //Create First Person view
        Renderer renderer = new Renderer((double) screenWidth, screenHeight);
        firstPersonView.getChildren().add(renderer);

        // Add the checks to see if the player has pressed a key
        scene.setOnKeyPressed(e -> player.keyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> player.keyReleased(e.getCode()));

        // create game loop for player movement
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.movement(MAP);
                renderer.render(player);
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