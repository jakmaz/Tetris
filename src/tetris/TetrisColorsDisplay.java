package tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisColorsDisplay extends JFrame {
    public TetrisColorsDisplay() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tetris Block Colors");
        this.setLayout(new GridLayout(3, 4)); // Grid layout for 12 colors

        // Define the bright colors
        Color[] colors = {
                new Color(255, 105, 97), // Bright red
                new Color(255, 179, 71), // Bright orange
                new Color(255, 255, 128), // Light yellow
                new Color(128, 255, 0), // Bright green
                new Color(102, 178, 255), // Light blue
                new Color(165, 102, 255), // Light indigo
                new Color(255, 153, 255), // Light violet
                new Color(255, 102, 178), // Bright pink
                new Color(0, 255, 255), // Bright cyan
                new Color(255, 102, 255), // Bright magenta
                new Color(153, 255, 153), // Light lime
                new Color(0, 153, 252) // Sky blue
        };

        // Create panels for each color
        for (Color color : colors) {
            JPanel panel = new JPanel();
            panel.setBackground(color);
            this.add(panel);
        }

        this.pack(); // Size the frame
        this.setSize(800, 600); // Set the window size
        this.setVisible(true); // Show the frame
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisColorsDisplay();
            }
        });
    }
}

