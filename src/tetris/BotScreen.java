package tetris;

import javax.swing.*;

/**
 * Represents the screen for the bot gameplay in the Tetris game.
 * This class sets up the game area for the bot and starts the bot's gameplay thread.
 */
public class BotScreen extends JFrame {
    /**
     * Constructs and initializes the bot gameplay screen.
     * Sets up the game area and starts the bot thread to play the game.
     *
     * @param mode A code of the bot algorithm to be used.
     * @param darkMode A boolean value to set the game in dark mode if true.
     */
    public BotScreen(int mode, boolean darkMode) {
        GameArea ga = new GameArea(darkMode, false);
        this.add(ga);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        // Start the bot thread
        BotThread botThread = new BotThread(ga, mode);
        botThread.start();
        // Set the default close operation to do nothing
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Add window listener to detect window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Interrupt the game thread
                botThread.interrupt();
                // Dispose the frame
                dispose();
            }
        });
    }
}
