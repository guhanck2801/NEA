package com.example.demo.nea_start;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player extends Rectangle {

    // create variables to check if a key has been pressed
    private boolean forward, backward, left, right, rotateLeft, rotateRight;

    // Create array containing all the rays. Typial FOV in a raycaster is 60 degrees.
    public ArrayList<Raycaster> ray = new ArrayList<>();
    private final double FOV  = 60;
    private final int numRays = Main.screenWidth;

    // assign values for max speed and rotation speed
    private final double maxSpeed = 2.5;
    private final double rotationSpeed = 2.5;


    // Create the player
    public Player(Color colour, int x, int y, int size) {

        super(size, size, colour);

        setX(x);
        setY(y);

        // Makes player start facing up
        setRotate(0);

        //create rays that cast of player from the center
        double startAngle = getRotate() - FOV/2;
        double angleIncrememt = FOV / numRays;
        for (int i = 0; i < numRays; i++) {
            ray.add(new Raycaster(Color.RED, getX() + getWidth() / 2, getY() + getHeight() / 2, startAngle + (i*angleIncrememt)));
        }
    }

    // say player should move if key has been pressed
    public void keyPressed(KeyCode key) {
        switch (key) {
            case W -> forward = true;
            case S -> backward = true;
            case A -> left = true;
            case D -> right = true;
            case LEFT -> rotateLeft = true;
            case RIGHT -> rotateRight = true;
        }
    }

    // say player stop moving if key has been released
    public void keyReleased(KeyCode key) {
        switch (key) {
            case W -> forward = false;
            case S -> backward = false;
            case A -> left = false;
            case D -> right = false;
            case LEFT -> rotateLeft = false;
            case RIGHT -> rotateRight = false;
        }
    }

    // move the player
    public void movement(int[][] map) {

        double angle = Math.toRadians(getRotate());

        // calculate the angle of displacement of the player to find out speed along each axis
        double frontBackVerticalDisplacement = Math.sin(angle) * maxSpeed;
        double frontBackHorizontalDisplacement = Math.cos(angle) * maxSpeed;

        double leftRightVerticalDisplacement = Math.cos(angle) * maxSpeed;
        double leftRightHorizontalDisplacement = Math.sin(angle) * maxSpeed;

        // move the player according to the displacement calculated above
        if (forward)
            move(frontBackVerticalDisplacement, -frontBackHorizontalDisplacement, map);
        if (backward)
            move(-frontBackVerticalDisplacement, frontBackHorizontalDisplacement, map);
        if (left)
            move(-leftRightVerticalDisplacement, -leftRightHorizontalDisplacement, map);
        if (right)
            move(leftRightVerticalDisplacement, leftRightHorizontalDisplacement, map);
        if (rotateLeft)
            setRotate(getRotate() - rotationSpeed);
        if (rotateRight)
            setRotate(getRotate() + rotationSpeed);

        // move the rays based on the players movement#
        double startAngle = getRotate() - FOV / 2;
        double angleIncrement = FOV / ray.size();

        for (int i = 0; i < ray.size(); i++) {
           ray.get(i).updateRay(getX() + getWidth() / 2, getY() + getHeight() / 2, startAngle + i * angleIncrement, map);
        }

    }

    // set the new location the player should move to
    private void move(double dx, double dy, int[][] map) {

        double newX = getX() + dx;
        double newY = getY() + dy;

        // move player if a wall hasn't been detected, each if statement allows player to slide along wall in the corresponding axis
        if (!isWall(newX, getY(), map)) {
            setX(newX);
        }

        if (!isWall(getX(), newY, map)) {
            setY(newY);
        }
    }

    // collision detection
    private boolean isWall(double x, double y, int[][] map) {

        // Get corners of the players
        double left = x;
        double right = x + getWidth();
        double top = y;
        double bottom = y +getHeight();

        // Convert the corners into tiles on the map
        int leftTile = (int) (left / Main.TILE_SIZE);
        int rightTile = (int) (right / Main.TILE_SIZE);
        int topTile = (int) (top / Main.TILE_SIZE);
        int bottomTile = (int) (bottom / Main.TILE_SIZE);

        // Check all tiles occupied by the player
        for (int tileY = topTile; tileY <= bottomTile; tileY++) {
            for (int tileX = leftTile; tileX <= rightTile; tileX++) {

                if (map[tileY][tileX] == 1) {
                    return true;
                }
            }
        }

        return false;
    }
}