import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BrickBreaker extends JPanel implements ActionListener, KeyListener {

    // Variabili per la finestra principale
    private final int SCREEN_WIDTH = 1200;
    private final int SCREEN_HEIGHT = 900;
    private final Timer timer;

    // Variabili della piattaforma
    private int paddleX = 350;
    private final int paddleWidth = 150;
    private final int paddleHeight = 15;

    // Variabili della palla
    private int ballX = 400, ballY = 300;
    private int ballDiameter = 15;
    private int ballDX = 3, ballDY = -3;

    // Variabili dei mattoni
    private final int brickWidth = 60, brickHeight = 20;
    private final int rows = 8, cols = 30;
    private final boolean[][] bricks = new boolean[rows][cols];
    private int score = 0;

    public BrickBreaker() {
        // Impostazioni iniziali
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Inizializza i mattoni casualmente
        initializeBricks();

        // Imposta il timer
        timer = new Timer(10, this);
        timer.start();
    }

    // Metodo per disporre casualmente i mattoni
    private void initializeBricks() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks[i][j] = rand.nextBoolean(); // Decide casualmente se un mattone deve essere presente
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Muove la palla
        ballX += ballDX;
        ballY += ballDY;

        // Collisione con le pareti
        if (ballX <= 0 || ballX >= SCREEN_WIDTH - ballDiameter) {
            ballDX = -ballDX;
        }
        if (ballY <= 0) {
            ballDY = -ballDY;
        }

        // Collisione con la piattaforma
        if (ballY + ballDiameter >= SCREEN_HEIGHT - paddleHeight) {
            if (ballX + ballDiameter >= paddleX && ballX <= paddleX + paddleWidth) {
                ballDY = -ballDY;
            } else {
                // Game Over
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            }
        }

        // Collisione con i mattoni
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (bricks[i][j]) {
                    int brickX = j * brickWidth;
                    int brickY = i * brickHeight;
                    if (ballX + ballDiameter >= brickX && ballX <= brickX + brickWidth &&
                            ballY + ballDiameter >= brickY && ballY <= brickY + brickHeight) {
                        ballDY = -ballDY;
                        bricks[i][j] = false;
                        score += 10;
                    }
                }
            }
        }

        // Aggiorna la finestra
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna la piattaforma
        g.setColor(Color.BLUE);
        g.fillRect(paddleX, SCREEN_HEIGHT - paddleHeight, paddleWidth, paddleHeight);

        // Disegna la palla
        g.setColor(Color.GREEN);
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);

        // Disegna i mattoni
        g.setColor(Color.RED);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (bricks[i][j]) {
                    g.fillRect(j * brickWidth, i * brickHeight, brickWidth, brickHeight);
                }
            }
        }

        // Disegna il punteggio
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= 15;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX + paddleWidth < SCREEN_WIDTH) {
            paddleX += 15;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        BrickBreaker gamePanel = new BrickBreaker();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setVisible(true);
    }
}
