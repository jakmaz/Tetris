package tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents the screen for displaying a sequence of moves in the Tetris game.
 * This class sets up the game area for the sequence display and starts the sequence thread.
 */
public class SequenceScreen extends JFrame {

    /**
     * Constructs the SequenceScreen and initializes the game area for the sequence display.
     *
     * @param darkMode A boolean value to set the game in dark mode if true.
     */
    public SequenceScreen(boolean darkMode) {
        GameArea ga = new GameArea(darkMode, true);
        this.add(ga);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        // Start the game thread
        SequenceThread sequenceThread = new SequenceThread(ga);
        sequenceThread.start();
        // Set the default close operation to do nothing
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Add window listener to detect window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Interrupt the game thread
                sequenceThread.interrupt();
                // Dispose the frame
                dispose();
            }
        });
    }
}
