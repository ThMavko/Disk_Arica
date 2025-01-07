import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipale extends JPanel {

    private final int SCREEN_WIDTH = 1000;
    private final int SCREEN_HEIGHT = 900;

    public MenuPrincipale() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLUE);

        JButton startButton = new JButton("Avvia Gioco");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setFocusPainted(false);

        // Impostare la posizione del pulsante
        startButton.setBounds(250, 240, 200, 140);

        setLayout(null);
        add(startButton);

        // Aggiungere azione al pulsante
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avviaGioco();
            }
        });

        JFrame frame = new JFrame("Menu Principale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setVisible(true);
    }

    public void avviaGioco() {
        // Chiudi la finestra del menu principale
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();

        // Avvia il gioco principale
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("Brick Breaker");
            BrickBreaker gamePanel = new BrickBreaker();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setContentPane(gamePanel);
            gameFrame.pack();
            gameFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        new MenuPrincipale();
    }
}
