package tetris;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HighScoresScreen extends JFrame {
    List<Player> topPlayers;
    public HighScoresScreen() {
        // Set up the JFrame
        setTitle("Tetris High Scores");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        HighScoresPanel panel = new HighScoresPanel();
        add(panel);
        setVisible(true);
    }
    private static List<Player> getTopScores() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                players.add(new Player(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the list and return the top N scores
        return players.stream()
                .sorted(Comparator.comparingInt(Player::score).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public record Player(String name, int score) {
    }
    public static class HighScoresPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponents(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            // draw leaderboard text
            g2.setFont(new Font("SansSerif", Font.BOLD, 40));
            g2.drawString("LEADERBOARDS", 135,100);
            // draw table
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(100, 150, 400, 550, 25, 25);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(100,150,400,550, 25, 25);
            for (int i = 0; i < 9; i++) {
                g2.drawLine(100, 205+(550/10)*i, 500, 205+(550/10)*i);
            }
            g2.drawLine(300,150,300,700);
            // add data
            List<Player> topPlayers = HighScoresScreen.getTopScores();
            g2.setFont(new Font("SansSerif", Font.BOLD, 30));
            for (int i = 0; i < topPlayers.size(); i++) {
                g2.drawString(topPlayers.get(i).name, 100+20, 150+40+(55*i));
                g2.drawString(String.valueOf(topPlayers.get(i).score), 100+20+200, 150+40+(55*i));
            }

        }
    }
}
