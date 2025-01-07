package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;

public class TetrisGame extends JPanel {

    private Timer timer;
    private GameBoard gameBoard;
    private TetrisPiece currentPiece;
    private BufferedImage backgroundImage;

    public TetrisGame() {
        gameBoard = new GameBoard();
        initializeGame();

        timer = new Timer(100, e -> {
            movePieceDown();
            repaint();
        });
        timer.start();

        addKeyBindings();
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background.jpg"));
            System.out.println("Immagine di sfondo caricata correttamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Impossibile caricare l'immagine di sfondo.");
        }
    }

    private void initializeGame() {
        List<int[][]> tPieceRotations = new ArrayList<>();
        tPieceRotations.add(new int[][] { {1, 1, 1}, {0, 1, 0} }); // Rotazione 1
        tPieceRotations.add(new int[][] { {0, 1}, {1, 1}, {0, 1} }); // Rotazione 2
        currentPiece = new TetrisPiece(tPieceRotations);
    }

    private void addKeyBindings() {
        this.setFocusable(true);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        this.getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameBoard.checkCollision(currentPiece.getShape(), currentPiece.getX() - 1, currentPiece.getY()) &&
                        currentPiece.getX() > 0) {
                    currentPiece.moveLeft();
                    repaint();
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameBoard.checkCollision(currentPiece.getShape(), currentPiece.getX() + 1, currentPiece.getY())) {
                    currentPiece.moveRight();
                    repaint();
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("R"), "rotatePiece");
        this.getActionMap().put("rotatePiece", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotatePiece();
                repaint();
            }
        });
    }

    private void rotatePiece() {
        currentPiece.rotate();

        if (gameBoard.checkCollision(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY())) {
            currentPiece.rotateBack();
        }
    }

    private void movePieceDown() {
        if (!gameBoard.checkCollision(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        } else {
            gameBoard.placePiece(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY());
            gameBoard.clearFullRows();
            initializeGame();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        drawGameBoard(g);
        drawCurrentPiece(g);
    }

    private void drawGameBoard(Graphics g) {
        int[][] board = gameBoard.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * 20, i * 20, 20, 20);
                }
            }
        }
    }

    private void drawCurrentPiece(Graphics g) {
        g.setColor(Color.RED);
        int[][] shape = currentPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    g.fillRect((currentPiece.getX() + j) * 20, (currentPiece.getY() + i) * 20, 20, 20);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        TetrisGame game = new TetrisGame();
        frame.add(game);
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
