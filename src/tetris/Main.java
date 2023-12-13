package tetris;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Main class of the Tetris Game.
 * This class initializes the game's opening screen and sets up the necessary GUI components
 * and their respective action listeners.
 */
public class Main {
    private static JFrame openingFrame;
    private static boolean darkMode = false;
    private static JLabel titleLabel;
    private static JButton darkModeButton;
    private static TitledBorder singlePlayerBorder;
    private static TitledBorder botBorder;
    private static TitledBorder sequenceBorder;
    private static TitledBorder settingsBorder;


    /**
     * The main method that sets up the opening screen of the Tetris game.
     * It initializes all the GUI components and their action listeners.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        openingFrame = new JFrame("Opening Screen");
        openingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openingFrame.setSize(500, 400);
        openingFrame.setLayout(new GridLayout(5, 1));

        titleLabel = new JLabel("Welcome to Group 20 Tetris Game!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel singlePlayerPanel = new JPanel(new GridBagLayout());
        singlePlayerPanel.setOpaque(false);
        singlePlayerBorder = new TitledBorder("Single player game");
        singlePlayerPanel.setBorder(singlePlayerBorder);
        JButton playButton = new JButton("Play");
        JButton highScoresButton = new JButton("High Scores");
        singlePlayerPanel.add(playButton);
        singlePlayerPanel.add(highScoresButton);

        JPanel botPanel = new JPanel(new GridBagLayout());
        botPanel.setOpaque(false);
        botBorder = new TitledBorder("Bot game");
        botPanel.setBorder(botBorder);
        JButton playAsABot = new JButton("Let the bot play");

        JComboBox<String> botChoice = new JComboBox<>();
        botChoice.addItem("Random Bot");
        botChoice.addItem("Heuristic Bot");
        botChoice.addItem("Heuristic Bot with knowledge of next move");
        botPanel.add(botChoice);
        botPanel.add(playAsABot);

        JPanel sequencePanel = new JPanel(new GridBagLayout());
        sequencePanel.setOpaque(false);
        sequenceBorder = new TitledBorder("Optimal ordering");
        sequencePanel.setBorder(sequenceBorder);
        JButton showSequenceButton = new JButton("Show the best sequence");
        sequencePanel.add(showSequenceButton);

        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setOpaque(false);
        settingsBorder = new TitledBorder("Settings");
        settingsPanel.setBorder(settingsBorder);
        darkModeButton = new JButton("Change to Dark Mode");
        settingsPanel.add(darkModeButton);

        openingFrame.add(titleLabel);
        openingFrame.add(singlePlayerPanel);
        openingFrame.add(botPanel);
        openingFrame.add(sequencePanel);
        openingFrame.add(settingsPanel);

        // Action when play button is clicked
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new GameScreen(darkMode); }
        });

        // Action when high scores button is clicked
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new HighScoresScreen(); }
        });

        // Action when play against a bot button is clicked
        playAsABot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(botChoice.getSelectedItem(), "Random Bot")) {
                    new BotScreen(1, darkMode);
                } else if (Objects.equals(botChoice.getSelectedItem(), "Heuristic Bot")) {
                    new BotScreen(2, darkMode);
                } else if ((Objects.equals(botChoice.getSelectedItem(), "Heuristic Bot with knowledge of next move"))) {
                    new BotScreen(3, darkMode);
                }
            }
        });

        showSequenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceScreen(darkMode);
            }
        });

        // Action when change color button is clicked
        darkModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color darkColor = new Color(50, 50, 50);
                if (!darkMode) {
                    darkMode = true;
                    darkModeButton.setText("Change to Light Mode");
                    openingFrame.getContentPane().setBackground(darkColor);
                    titleLabel.setForeground(Color.WHITE);
                    singlePlayerBorder.setTitleColor(Color.WHITE);
                    botBorder.setTitleColor(Color.WHITE);
                    sequenceBorder.setTitleColor(Color.WHITE);
                    settingsBorder.setTitleColor(Color.WHITE);
                } else {
                    darkMode = false;
                    darkModeButton.setText("Change to Dark Mode");
                    openingFrame.getContentPane().setBackground(Color.WHITE);
                    titleLabel.setForeground(darkColor);
                    singlePlayerBorder.setTitleColor(darkColor);
                    botBorder.setTitleColor(darkColor);
                    sequenceBorder.setTitleColor(darkColor);
                    settingsBorder.setTitleColor(darkColor);
                }
            }
        });

        openingFrame.setLocationRelativeTo(null);
        openingFrame.setVisible(true);
    }
}
