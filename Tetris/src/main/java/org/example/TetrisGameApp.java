package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisGameApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private TetrisGame gameBoard;

    public TetrisGameApp() {
        setTitle("Tetris Game");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createStartScreen(), "StartScreen");
        gameBoard = new TetrisGame();
        mainPanel.add(gameBoard, "GameBoard");
        add(mainPanel);
        cardLayout.show(mainPanel, "StartScreen");
    }

    private JPanel createStartScreen() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Uelcom to Tetris", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        JButton startButton = new JButton("Start Plei e Sciat ap");
        startButton.setFont(new Font("Arial", Font.PLAIN, 60));
        startButton.setBackground(Color.ORANGE);
        startButton.setForeground(Color.BLUE);
        startButton.setOpaque(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "GameBoard");
            }
        });

        startPanel.add(titleLabel, BorderLayout.NORTH);
        startPanel.add(startButton, BorderLayout.CENTER);

        return startPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TetrisGameApp app = new TetrisGameApp();
                app.setVisible(true);
            }
        });
    }
}
