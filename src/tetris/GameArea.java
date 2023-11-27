package tetris;

import blocks.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameArea extends JPanel {
    private final int CELL_SIZE = 40;
    private Color FILL_COLOR = Color.WHITE;
    private Color TEXT_COLOR = Color.BLACK;
    private final Color[][] background;
    int score = 0;
    private final TetrisBlock[] allBlocks;
    private TetrisBlock block;
    private TetrisBlock nextBlock = new TetrisBlock(new int[][]{{1,0},{1,0},{1,1}}, Color.BLUE);

    public GameArea(boolean darkMode) {
        if (darkMode) {
            FILL_COLOR = Color.DARK_GRAY;
            TEXT_COLOR = Color.LIGHT_GRAY;
        }
        this.setPreferredSize(new Dimension(600, 800));
        this.setLayout(null);
        this.setFocusable(true);

        background = new Color[15][5];
        allBlocks = new TetrisBlock[] {
                new XShape(),
                new IShape(),
                new ZShape(),
                new TShape(),
                new UShape(),
                new VShape(),
                new WShape(),
                new YShape(),
                new LShape(),
                new PShape(),
                new NShape(),
                new FShape()
        };
    }
    public void spawnBlock() {
        block = nextBlock;
        Random random = new Random();
        nextBlock = allBlocks[random.nextInt(allBlocks.length)];
        block.spawn();
    }
    public boolean isBlockOutOfBounds() {
        if (block.getY() < 0) {
            block = null;
            return true;
        }
        return false;
    }
    private boolean checkBottom() {
        // check bounds
        if (block.getBottomEdge() == 15) {
            return false;
        }
        // check collision with existing blocks
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();
        for (int col = 0; col < w; col++) {
            for (int row = h - 1; row >= 0; row--) {
                if (shape[row][col] != 0) {
                    int x = col+block.getX();
                    int y = row+block.getY() + 1;
                    if (y<0) break;
                    if (background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkLeft() {
        // check bounds
        if (block.getLeftEdge() == 0) {
            return false;
        }
        // check collision with existing blocks
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < h; col++) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if (y<0) break;
                    if (background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkRight() {
        // check bounds
        if (block.getRightEdge() == 5) {
            return false;
        }
        // check collision with existing blocks
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();
        for (int row = 0; row < h; row++) {
            for (int col = w-1; col >= 0; col--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() + 1;
                    int y = row + block.getY();
                    if (y<0) break;
                    if (background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }
    public void clearLine() {
        boolean lineFilled;
        // try to find a full line
        for (int row = 14; row >= 0; row--) {
            lineFilled = true;
            for (int col = 0; col < 5; col++) {
                if (background[row][col] == null) {
                    lineFilled = false;
                    break;
                }
            }
            // if the filled line is found
            if (lineFilled) {
                // clear it
                for (int i = 0; i < 5; i++) {
                    background[row][i] = null;
                }
                // and shift the whole thing
                shiftDown(row);
                // adds to row to the checker to avoid skipping one just shifted down
                row++;
                score++;
                SoundPlayer.playSound("src/Assets/line.wav");
                repaint();
            }
        }
    }
    private void shiftDown(int r) {
        // shift all elements one row down
        for (int row = r; row > 0; row--) {
            for (int col = 0; col < 5; col++) {
                background[row][col] = background[row-1][col];
            }
        }
        // clear the upper row
        for (int i = 0; i < 5; i++) {
            background[0][i] = null;
        }
    }

    public void moveBlockToBackground() {
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();
        int x = block.getX();
        int y = block.getY();
        Color color = block.getColor();
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {
                    background[row+y][col+x]=color;
                }
                
            }
        }
    }
    public boolean moveBlockDown() {
        if (!checkBottom()) {
            SoundPlayer.playSound("src/Assets/softdrop.wav");
            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }
    public void moveBlockLeft() {
        if (block==null) return;
        if (checkLeft()) {
            block.moveLeft();
            SoundPlayer.playSound("src/Assets/move.wav");
        } else {
            SoundPlayer.playSound("src/Assets/alert.wav");
        }
        repaint();
    }
    public void moveBlockRight() {
        if (block==null) return;
        if (checkRight()) {
            block.moveRight();
            SoundPlayer.playSound("src/Assets/move.wav");
        } else {
            SoundPlayer.playSound("src/Assets/alert.wav");
        }
        repaint();
    }
    public void dropBlock() {
        while (checkBottom()) {
            block.moveDown();
        }
        SoundPlayer.playSound("src/Assets/land.wav");
        repaint();
    }
    public void rotateBlock() {
        if (block == null) return;
        block.rotate();
        if (block.getLeftEdge() < 0) block.setX(0);
        if (block.getRightEdge() >= 5) block.setX(5 - block.getWidth());
        if (block.getBottomEdge() >= 15) block.setY(15 - block.getHeight());
        // check rotate collision
        if (!rotatedCorrectly()) {
            block.rotateBack();
        } else {
            SoundPlayer.playSound("src/Assets/rotate.wav");
        }
        repaint();
    }
    private boolean rotatedCorrectly() {
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();
        int x = block.getX();
        int y = block.getY();
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (y < 0) break;
                if (shape[row][col] != 0 && background[row+y][col+x] != null) return false;
            }
        }
        return true;
    }
    public int getPause() {
        // math function controlling speed with points (0,1000) -> (30, 200) -> (infinite, 200)
        return (int) (200 + 800 / Math.exp(0.05 * score));
    }
    public void gameOver() {
        String playerName = JOptionPane.showInputDialog("Game over!\nInsert your name");
        if (playerName != null) addPlayerToLeaderboard(playerName, score);
        System.exit(0);
    }
    private void addPlayerToLeaderboard(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt", true))) {
            writer.write(playerName + " - " + score);
            writer.newLine();
        } catch (IOException ignored) {
        }
    }
    private void drawBlock(Graphics2D g2) {
        //draw block
        int w = block.getWidth();
        int h = block.getHeight();
        Color c = block.getColor();
        int[][] shape = block.getShape();
        for (int row = 0; row<h; row++){
            for (int col = 0; col<w; col++) {
                if (shape[row][col] == 1) {
                    int x = (block.getX() + col) * CELL_SIZE;
                    int y = (block.getY() + row) * CELL_SIZE;
                    g2.setColor(c);
                    g2.fillRect(x+100, y+100, CELL_SIZE, CELL_SIZE);
                    // add a shine
                    Color shineColor = new Color(255, 255, 255, 50);
                    Color darkColor = new Color(0,0,0, 50);
                    g2.setColor(shineColor);
                    g2.fillRect(x+100, y+100, 5, CELL_SIZE);
                    g2.fillRect(x+100, y+100, CELL_SIZE, 5);
                    g2.setColor(darkColor);
                    g2.fillRect(x+100, y+100+CELL_SIZE-5, CELL_SIZE, 5);
                    g2.fillRect(x+100+CELL_SIZE-5, y+100, 5, CELL_SIZE);
                    // Draw thicker grid lines
                    g2.setColor(TEXT_COLOR);
                    g2.drawRect(x+100, y+100, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
    private void drawBlockPreview(Graphics2D g2) {
        int initialY = block.getY();
        while (checkBottom()) {
            block.moveDown();
        }
        //draw block
        int w = block.getWidth();
        int h = block.getHeight();
        int[][] shape = block.getShape();
        for (int row = 0; row<h; row++){
            for (int col = 0; col<w; col++) {
                if (shape[row][col] == 1) {
                    int x = (block.getX() + col) * CELL_SIZE;
                    int y = (block.getY() + row) * CELL_SIZE;
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(x+100, y+100, CELL_SIZE, CELL_SIZE);
                    // Draw thicker grid lines
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x+100, y+100, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        // revert original position
        block.setY(initialY);
    }
    private void drawBackground(Graphics2D g2) {
        // draw background
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 5; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                Color color = background[row][col];
                if (color!=null) {
                    g2.setColor(background[row][col]);
                    g2.fillRect(100+x, 100+y, CELL_SIZE, CELL_SIZE);
                    // add a shine
                    Color shineColor = new Color(255, 255, 255, 50);
                    Color darkColor = new Color(0,0,0, 50);
                    g2.setColor(shineColor);
                    g2.fillRect(x+100, y+100, 5, CELL_SIZE);
                    g2.fillRect(x+100, y+100, CELL_SIZE, 5);
                    g2.setColor(darkColor);
                    g2.fillRect(x+100, y+100+CELL_SIZE-5, CELL_SIZE, 5);
                    g2.fillRect(x+100+CELL_SIZE-5, y+100, 5, CELL_SIZE);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillRect(100+x, 100+y, CELL_SIZE, CELL_SIZE);
                }
                // Draw thicker grid lines
                g2.setColor(TEXT_COLOR);
                g2.drawRect(100+x, 100+y, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    private void drawNextBlock(Graphics2D g2) {
        // Create a next pentomino box
        g2.setColor(TEXT_COLOR);
        g2.drawRect(350, 175, 150, 150);
        g2.setColor(FILL_COLOR);
        g2.fillRect(350+2, 175+2, 150-4, 150-4);
        g2.setColor(TEXT_COLOR);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.drawString("Next block:", 390, 195);
        // Determine the size of the pentomino
        int pWidth = nextBlock.getWidth();
        int pHeight = nextBlock.getHeight();
        int pCellSize;
        if (pWidth > pHeight) {
            pCellSize = 100/pWidth;
        } else {
            pCellSize = 100/pHeight;
        }
        // Calculate the starting position to draw the pent centered in the box
        int boxCenterX = (375 + 475) / 2;
        int boxCenterY = (210 + 310) / 2;
        int startX = boxCenterX - (pWidth * pCellSize) / 2;
        int startY = boxCenterY - (pHeight * pCellSize) / 2;
        // Draw the pentomino
        for (int row = 0; row < pHeight; row++) {
            for (int col = 0; col < pWidth; col++) {
                if (nextBlock.getShape()[row][col] == 1) {
                    // Draw each block of the pentomino
                    g2.setColor(nextBlock.getColor());
                    g2.fillRect(startX + col * pCellSize, startY + row * pCellSize, pCellSize, pCellSize);
                    // Draw thicker grid lines
                    g2.setColor(TEXT_COLOR);
                    g2.drawRect(startX + col * pCellSize, startY + row * pCellSize, pCellSize, pCellSize);
                }
            }
        }
    }
    private void drawScore(Graphics2D g2){
        // Create a score box
        g2.setColor(TEXT_COLOR);
        g2.drawRect(350, 100, 150, 50);
        g2.setColor(FILL_COLOR);
        g2.fillRect(350+2, 100+2, 150-4, 50-4);
        g2.setColor(TEXT_COLOR);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.drawString("Current score:", 380, 120);
        g2.drawString(""+ score, 415, 140);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));  // Set the line thickness
        drawBackground(g2);
        drawBlockPreview(g2);
        drawBlock(g2);
        drawNextBlock(g2);
        drawScore(g2);
    }
}
