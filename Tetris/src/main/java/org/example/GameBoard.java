package org.example;

public class GameBoard {
    private final int rows = 40;
    private final int cols = 40;
    private int[][] board = new int[rows][cols];
    public boolean checkCollision(int[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    int newX = x + j;
                    int newY = y + i;

                    if (newX < 0 || newX >= cols || newY < 0 || newY >= rows) {
                        return true;
                    }

                    if (board[newY][newX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void placePiece(int[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    board[y + i][x + j] = shape[i][j];
                }
            }
        }
    }

    public void clearFullRows() {
        for (int row = 0; row < rows; row++) {
            boolean full = true;
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 0) {
                    full = false;
                    break;
                }
            }

            if (full) {
                for (int r = row; r > 0; r--) {
                    board[r] = board[r - 1];
                }
                board[0] = new int[cols];
                row--;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }
}
