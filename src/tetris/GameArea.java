package tetris;

import blocks.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Represents the main game area of the Tetris game.
 * This class is responsible for managing the game grid, block operations, scoring, and rendering.
 */
public class GameArea extends JPanel {
    private final int CELL_SIZE = 40;
    private Color FILL_COLOR = Color.WHITE;
    private Color TEXT_COLOR = Color.BLACK;
    private Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private Color[][] background;
    int score = 0;
    public final TetrisBlock[] allBlocks = new TetrisBlock[] {
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
    private final ArrayList<TetrisBlock> bag = new ArrayList<>();
    private TetrisBlock block;
    private TetrisBlock nextBlock;
    private final boolean optimalSequence;

    /**
     * Constructs a new GameArea with specified settings.
     * Initializes the game area with a background grid, sets up dark mode if specified,
     * and fills the bag with TetrisBlocks for block spawning. The constructor also sets the preferred size,
     * layout, and focusability of the game area panel.
     *
     * @param darkMode         Specifies whether the game should be displayed in dark mode.
     * @param optimalSequence  If true, the blocks will spawn in an optimal sequence; otherwise, the sequence is random.
     */
    public GameArea(boolean darkMode, boolean optimalSequence) {
        if (darkMode) {
            FILL_COLOR = Color.DARK_GRAY;
            TEXT_COLOR = Color.LIGHT_GRAY;
            BACKGROUND_COLOR = Color.DARK_GRAY;
        }
        this.optimalSequence = optimalSequence;
        this.setBackground(FILL_COLOR);
        this.setPreferredSize(new Dimension(600, 800));
        this.setLayout(null);
        this.setFocusable(true);

        background = new Color[15][5];
        fillBag();
    }

    /**
     * Fills the bag of TetrisBlocks with either a random sequence or an optimal one, based on game settings.
     */
    private void fillBag() {
        if (!optimalSequence) {
            // Convert array to a list for shuffling
            ArrayList<TetrisBlock> blocksList = new ArrayList<>(Arrays.asList(allBlocks));
            // Shuffle the list to get a random order
            Collections.shuffle(blocksList);
            // Add the shuffled blocks to the bag
            bag.addAll(blocksList);
        } else {
            fillBagWithOptimalSequence();
        }
    }

    /**
     * Fills the bag with TetrisBlocks in an optimal sequence.
     */
    private void fillBagWithOptimalSequence() {
        bag.add(new VShape());
        bag.add(new WShape());
        bag.add(new NShape());
        bag.add(new PShape());
        bag.add(new IShape());
        bag.add(new XShape());
        bag.add(new FShape());
        bag.add(new TShape());
        bag.add(new ZShape());
        bag.add(new UShape());
        bag.add(new YShape());
        bag.add(new LShape());
    }

    /**
     * Spawns a new block in the game area.
     */
    public void spawnBlock() {
        if (!bag.isEmpty()) {
        } else {
            fillBag();
        }
        block = bag.get(0);
        bag.remove(0);
        if (!bag.isEmpty()) {
        } else {
            fillBag();
        }
        nextBlock = bag.get(0);
        block.getShape();
        block.spawn();
    }

    /**
     * Checks if the current block is positioned at the top of the game area.
     *
     * @return true if the block is at the top, otherwise false.
     */
    public boolean isBlockOnTop() {
        if (block.getY() < 0) {
            block = null;
            return true;
        }
        return false;
    }

    /**
     * Checks if the current block is positioned at the top of the game area.
     *
     * @return true if the block is at the top, otherwise false.
     */
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

    /**
     * Checks if the current block has other block to its left
     *
     * @return true if the block has other block to its left, otherwise false.
     */
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

    /**
     * Checks if the current block has other block to its right
     *
     * @return true if the block has other block to its right, otherwise false.
     */
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

    /**
     * Clears any filled lines in the game area and updates the score accordingly.
     */
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
                SoundPlayer.playSound("line.wav");
                repaint();
            }
        }
    }

    /**
     * Shifts all blocks in the game area down starting from a specified row.
     *
     * @param r The row from which to start shifting down.
     */
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

    /**
     * Moves the current block to the background array.
     */
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

    /**
     * Attempts to move the current block one cell down.
     *
     * @return true if the block was successfully moved down, false otherwise.
     */
    public boolean moveBlockDown() {
        if (!checkBottom()) {
            SoundPlayer.playSound("softdrop.wav");
            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }

    /**
     * Moves the current block one cell to the left if possible.
     * Plays an appropriate sound based on whether the move is successful or not.
     */
    public void moveBlockLeft() {
        if (block==null) return;
        if (checkLeft()) {
            block.moveLeft();
            SoundPlayer.playSound("move.wav");
        } else {
            SoundPlayer.playSound("alert.wav");
        }
        repaint();
    }

    /**
     * Moves the current block one cell to the right if possible.
     * Plays an appropriate sound based on whether the move is successful or not.
     */
    public void moveBlockRight() {
        if (block==null) return;
        if (checkRight()) {
            block.moveRight();
            SoundPlayer.playSound("move.wav");
        } else {
            SoundPlayer.playSound("alert.wav");
        }
        repaint();
    }

    /**
     * Drops the current block straight down to the lowest possible position.
     * Plays a landing sound once the block drops.
     */
    public void dropBlock() {
        while (checkBottom()) {
            block.moveDown();
        }
        SoundPlayer.playSound("land.wav");
        repaint();
    }

    /**
     * Rotates the current block, adjusting its position if necessary to fit within the game area.
     * Reverts to the previous state if the rotation results in a collision.
     */
    public void rotateBlock() {
        if (block == null) return;
        TetrisBlock old = new TetrisBlock(block);
        block.rotate();
        if (block.getLeftEdge() < 0) block.setX(0);
        if (block.getRightEdge() >= 5) block.setX(5 - block.getWidth());
        if (block.getBottomEdge() >= 15) block.setY(15 - block.getHeight());
        // check rotate collision
        if (!rotatedCorrectly()) {
            block = old;
        } else {
            SoundPlayer.playSound("rotate.wav");
        }
        repaint();
    }

    /**
     * Checks if the rotated position of the current block is valid without any collisions.
     *
     * @return true if the rotation does not result in a collision, false otherwise.
     */
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

    /**
     * Calculates the current pause duration based on the score.
     * This duration determines the speed at which blocks fall.
     *
     * @return The calculated pause duration in milliseconds.
     */
    public int getPause() {
        // math function controlling speed with points (0,1000) -> (30, 200) -> (infinite, 200)
        return (int) (200 + 800 / Math.exp(0.05 * score));
    }

    /**
     * Ends the game and handles the aftermath, such as storing the player's score.
     * Displays different messages based on whether the player or a bot was playing.
     *
     * @param inputName If true, prompts the player to input their name for the leaderboard.
     */
    public void gameOver(boolean inputName) {
        if (inputName) {
            String playerName = JOptionPane.showInputDialog("Game over!\nInsert your name");
            if (playerName != null) addPlayerToLeaderboard(playerName, score);
            // restart the game
            score = 0;
            background = new Color[15][5];
            fillBag();
        } else {
            JOptionPane.showMessageDialog(null, "The bot has lost!");
            score = 0;
            background = new Color[15][5];
            fillBag();
        }
    }

    // BOT PART //

    /**
     * Executes a series of automated moves for the bot. The method calculates the best move based on a scoring algorithm,
     * carries out the move, and updates the game state accordingly.
     */
    public void automatedBotMove() throws InterruptedException {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Color[][] result = new Color[15][5];
        int bestScore = -1000;
        int bestScoreIndex = 0;
        // save initial coordinates
        int initialX = block.getX();
        int initialY = block.getY();
        int initialRotation = block.getCurrentRotation();
        // generate possible moves coded like [move left, move right, rotate]
        possibleMoves = generatePossibleMoves();
        // go through every move and
        for (int moveIndex = 0; moveIndex < possibleMoves.size(); moveIndex++) {
            carryCodedMove(possibleMoves.get(moveIndex), false);
            result = dropAndGetResult();
            printColorArray(result);
            int score = rateField(result);
            System.out.println("Score: " + score + "\n");
            if (score > bestScore) {
                bestScore = score;
                bestScoreIndex = moveIndex;
            }

            // revert original location
            block.setY(initialY);
            block.setX(initialX);
            block.setCurrentRotation(initialRotation);
        }
        System.out.println("Best score: " + bestScore + ". Index: " + bestScoreIndex);
        // do the move
        carryCodedMove(possibleMoves.get(bestScoreIndex), true);
        repaint();
    }

    /**
     * Executes a series of automated moves for the bot taking into account the next piece.
     * The method calculates the best move based on a scoring algorithm,
     * carries out the move, and updates the game state accordingly.
     */
    public void automatedBotMove2() throws InterruptedException {
        Thread.sleep(10);
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Color[][] result = new Color[15][5];
        int bestScore = -1000;
        int bestScoreIndex = 0;
        // save initial coordinates
        int initialX = block.getX();
        int initialY = block.getY();
        int initialRotation = block.getCurrentRotation();
        // generate possible moves coded like [move left, move right, rotate]
        possibleMoves = generatePossibleMoves();
        // go through every move and
        for (int moveIndex = 0; moveIndex < possibleMoves.size(); moveIndex++) {
            carryCodedMove(possibleMoves.get(moveIndex), false);
            result = dropAndGetResult();
            Color[][] initialBackground = copyColorArray(background);
            background = copyColorArray(result);
            printColorArray(result);

            for (int secondMoveIndex = 0; secondMoveIndex < possibleMoves.size(); secondMoveIndex++) {
                TetrisBlock initialBlock = new TetrisBlock(block);
                block = nextBlock;
                block.spawn();
                carryCodedMove(possibleMoves.get(secondMoveIndex), false);
                result = dropAndGetResult();
                printColorArray(result);
                System.out.println("Score: " + score + "\n");
                int score = rateField(result);
                if (score > bestScore) {
                    bestScore = score;
                    bestScoreIndex = moveIndex;
                }
                block = new TetrisBlock(initialBlock);
                block.setY(initialY);
                block.setX(initialX);
                block.setCurrentRotation(initialRotation);
            }
            background = copyColorArray(initialBackground);
            // revert original location
            block.setY(initialY);
            block.setX(initialX);
            block.setCurrentRotation(initialRotation);
        }
        System.out.println("Best score: " + bestScore + ". Index: " + bestScoreIndex);
        // do the move
        carryCodedMove(possibleMoves.get(bestScoreIndex), true);
        repaint();
    }

    private static Color[][] copyColorArray(Color[][] original) {
        Color[][] copy = new Color[original.length][];

        // Loop through the original array and copy each subarray (row)
        for (int i = 0; i < original.length; i++) {
            if (original[i] != null) {
                copy[i] = new Color[original[i].length];
                for (int j = 0; j < original[i].length; j++) {
                    copy[i][j] = original[i][j];
                }
            } else {
                copy[i] = null;
            }
        }
        return copy;
    }

    /**
     * Generates a list of possible moves for the bot. Each move is represented as an array of integers,
     * indicating the number of left moves, right moves, and rotations to perform.
     *
     * @return A list of possible moves.
     */
    private ArrayList<int[]> generatePossibleMoves() {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        for (int left = 2; left >= 0; left--) {
            for (int rotation = 0; rotation < 4; rotation++) {
                possibleMoves.add(new int[]{left, 0, rotation});
            }
        }
        for (int right = 2; right >= 0; right--) {
            for (int rotation = 0; rotation < 4; rotation++) {
                possibleMoves.add(new int[]{0, right, rotation});
            }
        }
        return possibleMoves;
    }

    /**
     * Performs a move encoded as an array of integers. The move includes a specified number of left moves,
     * right moves, and rotations.
     *
     * @param move  The move to be performed, encoded as [leftMoves, rightMoves, rotations].
     * @param sleep Specifies whether to sleep between actions for visual effect.
     */
    private void carryCodedMove(int[] move, boolean sleep) throws InterruptedException {
        int leftMoves = move[0];
        int rightMoves = move[1];
        int rotations = move[2];
        System.out.println("Carrying out moves: Left: " + leftMoves + " Right: " + rightMoves + " Rotation: " + rotations);
        // Carry out the moves
        for (int rotaton = 0; rotaton < rotations; rotaton++) {
            if (sleep) Thread.sleep(200);
            if (block == null) return;
            TetrisBlock old = new TetrisBlock(block);
            block.rotate();
            if (block.getLeftEdge() < 0) block.setX(0);
            if (block.getRightEdge() >= 5) block.setX(5 - block.getWidth());
            if (block.getBottomEdge() >= 15) block.setY(15 - block.getHeight());
            // check rotate collision
            if (!rotatedCorrectly()) {
                block = old;
            }
            if (sleep) Thread.sleep(200);

        }
        for (int leftMove = 0; leftMove < leftMoves; leftMove++) {
            if (checkLeft()) {
                block.moveLeft();
                if (sleep) Thread.sleep(200);

            }
        }
        for (int rightMove = 0; rightMove < rightMoves; rightMove++) {
            if (checkRight()) {
                block.moveRight();
                if (sleep) Thread.sleep(200);

            }
        }
    }

    /**
     * Simulates dropping the current block and returns the resulting game area state.
     * This method is used to evaluate the outcome of a move without altering the actual game state.
     *
     * @return A 2D array representing the background.
     */
    private Color[][] dropAndGetResult() {
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();
        int initialY = block.getY();
        Color color = block.getColor();
        // Copy background to result array
        Color[][] result = new Color[background.length][background[0].length];
        for (int i = 0; i < background.length; i++) {
            System.arraycopy(background[i], 0, result[i], 0, background[i].length);
        }
        // drop
        while (checkBottom()) {
            block.moveDown();
        }
        int x = block.getX();
        int y = block.getY();
        // Add to current block to the result
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {
                    if (y>=0) {
                        result[row + y][col + x] = color;
                    }
                }
            }
        }
        // revert original state
        block.setY(initialY);
        return result;
    }

    /**
     * Rates a given field configuration based on the number of lines cleared, the height of the stack,
     * and the number of holes. Used to evaluate the best move for the bot.
     *
     * @param field The field configuration to be rated.
     * @return The score of the field configuration.
     */
    public static int rateField(Color[][] field) {
        // score weights
        int linesWeight = 100;
        int heightWeight = 10;
        int holesWeight = 2;
        // rate lines
        int linesAmount = 0;
        for (Color[] row : field) {
            boolean lineFilled = true;
            for (Color color : row) {
                if (color == null) {
                    lineFilled = false;
                    break;
                }
            }
            if (lineFilled) {
                linesAmount++;
            }
        }
        // rate height
        int totalHeight = 0;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 15; row++) {
                if (field[row][col] != null) {
                    if ((15-row)>=totalHeight) {
                        totalHeight = (15 - row);
                    }
                }
            }
        }
        // rate empty holes
        int holesAmount = 0;
        for (int col = 0; col < 5; col++) {
            boolean topFound = false;
            for (int row = 0; row < 15; row++) {
                if (field[row][col] != null) {
                    topFound = true;
                }
                if (field[row][col] == null && topFound) {
                    holesAmount++;
                }
            }
        }
        // return total score
        return (linesWeight * linesAmount) - (heightWeight * totalHeight) - (holesWeight * holesAmount);
    }

    /**
     * Prints a representation of the given field to the console. Used for debugging.
     * Each cell is represented by the sum of its color components,
     * or 0 if the cell is empty.
     *
     * @param field The field to be printed.
     */
    private void printColorArray(Color[][] field) {
        if (field == null || field.length == 0) {
            System.out.println("Empty or null field.");
            return;
        }

        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[row].length; col++) {
                Color color = field[row][col];
                if (color != null) {
                    int sum = color.getRed() + color.getGreen() + color.getBlue();
                    System.out.print(sum + "\t"); // Print the sum followed by a tab for neat formatting
                } else {
                    System.out.print("0\t"); // If color is null, print 0
                }
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    /**
     * Paints the various components of the game area.
     *
     * @param g The Graphics object to be used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));  // Set the line thickness
        drawBackground(g2);
        drawBlockPreview(g2);
        drawBlock(g2);
        drawNextBlock(g2);
        drawTopBlockHide(g2);
        drawScore(g2);
        drawTitle(g2);
    }

    // DRAW PART //
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
                    g2.setColor(FILL_COLOR);
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
        g2.fillRect(350 + 2, 175 + 2, 150 - 4, 150 - 4);
        g2.setColor(TEXT_COLOR);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.drawString("Next block:", 390, 195);
        // Determine the size of the pentomino
        int pWidth = nextBlock.getWidth();
        int pHeight = nextBlock.getHeight();
        int pCellSize;
        if (pWidth > pHeight) {
            pCellSize = 100 / pWidth;
        } else {
            pCellSize = 100 / pHeight;
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
                    Color shineColor = new Color(255, 255, 255, 50);
                    Color darkColor = new Color(0, 0, 0, 50);
                    // Adjust drawing lines around the block
                    g2.setColor(shineColor);
                    g2.fillRect(startX + col * pCellSize, startY + row * pCellSize, pCellSize, 5);
                    g2.fillRect(startX + col * pCellSize, startY + row * pCellSize, 5, pCellSize);
                    g2.setColor(darkColor);
                    g2.fillRect(startX + col * pCellSize, startY + row * pCellSize + pCellSize - 5, pCellSize, 5);
                    g2.fillRect(startX + col * pCellSize + pCellSize - 5, startY + row * pCellSize, 5, pCellSize);
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
    private void drawTopBlockHide(Graphics2D g2){
        // Create a score box
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0,0,500,99);
    }
    private void drawTitle(Graphics2D g2){
        g2.setColor(TEXT_COLOR);
        g2.drawString("Group 20's Tetris", 245, 60);
    }
}
