package org.example;

import java.util.List;

public class TetrisPiece {
    private int[][] shape;
    private int x, y;
    private List<int[][]> rotations;
    private int currentRotation;

    public TetrisPiece(List<int[][]> rotations) {
        this.rotations = rotations;
        this.shape = rotations.get(0);
        this.x = 4;
        this.y = 0;
        this.currentRotation = 0;
    }

    public void rotate() {
        currentRotation = (currentRotation + 1) % rotations.size();
        shape = rotations.get(currentRotation);
    }

    public void rotateBack() {
        currentRotation = (currentRotation - 1 + rotations.size()) % rotations.size();
        shape = rotations.get(currentRotation);
    }

    public int[][] getShape() {
        return shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }
}
