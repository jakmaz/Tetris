package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static JFrame openingFrame;
    static boolean darkMode = false;
    static JLabel titleLabel;
    static JButton playButton;
    static JButton playAgainstBot;
    static JButton highScoresButton;
    static JButton darkModeButton;

    public static void main(String[] args) {

        openingFrame = new JFrame("Opening Screen");
        openingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openingFrame.setSize(400, 300);
        openingFrame.setLayout(new GridLayout(5, 1));

        titleLabel = new JLabel("Welcome to Group 20 Tetris Game!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        playButton = new JButton("Play");
        playAgainstBot = new JButton("Play against a Bot");
        highScoresButton = new JButton("High Scores");
        darkModeButton = new JButton("Change to Dark Mode");

        openingFrame.add(titleLabel);
        openingFrame.add(playButton);
        openingFrame.add(playAgainstBot);
        openingFrame.add(highScoresButton);
        openingFrame.add(darkModeButton);

        // Action when play button is clicked
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openingFrame.dispose();
                GameScreen screen = new GameScreen();
            }
        });


        // Action when play against a bot button is clicked
        playAgainstBot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(openingFrame, "You clicked Play as a Bot!");
            }
        });

        // Action when high scores button is clicked
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighScoresScreen screen = new HighScoresScreen();
            }
        });

        // Action when change color button is clicked
        darkModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!darkMode) {
                    darkMode = true;
                    darkModeButton.setText("Change to Light Mode");
                    openingFrame.getContentPane().setBackground(new Color(50, 50, 50));
                    titleLabel.setForeground(Color.WHITE);
                } else {
                    darkMode = false;
                    darkModeButton.setText("Change to Dark Mode");
                    openingFrame.getContentPane().setBackground(Color.WHITE);
                    titleLabel.setForeground(Color.BLACK);
                }
            }
        });

        openingFrame.setLocationRelativeTo(null);
        openingFrame.setVisible(true);
    }
}
