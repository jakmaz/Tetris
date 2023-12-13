package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Represents the main game screen for the Tetris game.
 * This class sets up the game area, initializes control bindings for gameplay,
 * and starts the game thread.
 */
public class GameScreen extends JFrame {
    private final GameArea ga;

    /**
     * Constructs the GameScreen and initializes the game area.
     *
     * @param darkMode A boolean value to set the game in dark mode if true.
     */
    public GameScreen(boolean darkMode) {
        this.getContentPane().setBackground(Color.BLACK);
        ga = new GameArea(darkMode, false);
        this.add(ga);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        initControls();
        // Start the game thread
        GameThread gameThread = new GameThread(ga);
        gameThread.start();
        // Set the default close operation to do nothing
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Add window listener to detect window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Interrupt the game thread
                gameThread.interrupt();
                // Dispose the frame
                dispose();
            }
        });
    }

    /**
     * Initializes the control bindings for the game.
     * Sets up the key bindings for moving the Tetris blocks and links them to the corresponding actions.
     */
    private void initControls() {
        // sets key bindings and the actions assigned to them
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("D"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("A"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("W"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("S"), "down");
        im.put(KeyStroke.getKeyStroke("SPACE"), "drop");
        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockRight();
            }
        });
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockLeft();
            }
        });
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.rotateBlock();
            }
        });
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockDown();
            }
        });
        am.put("drop", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.dropBlock();
            }
        });
    }
}
