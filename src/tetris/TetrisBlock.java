package tetris;

import java.awt.*;

/**
 * Represents a block in the Tetris game.
 * This class encapsulates the properties and behaviors of a Tetris block, such as its shape, color,
 * position, and rotation.
 */
public class TetrisBlock {
    private int[][] shape;
    private final Color color;
    private int x, y;
    private int[][][] shapes;
    private int currentRotation;

    /**
     * Constructs a new TetrisBlock with a specified shape and color.
     *
     * @param shape The 2D array representing the shape of the block.
     * @param color The color of the block.
     */
    public TetrisBlock(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        initShapes();
    }

    /**
     * Constructs a new TetrisBlock as a copy of another TetrisBlock.
     *
     * @param other The TetrisBlock to copy.
     */
    public TetrisBlock(TetrisBlock other) {
        this.shape = deepCopyMatrix(other.shape);
        this.color = other.color;
        this.x = other.x;
        this.y = other.y;
        this.shapes = deepCopy3DMatrix(other.shapes);
        this.currentRotation = other.currentRotation;
    }

    /**
     * Initializes the rotated versions of the original shape.
     */
    private void initShapes() {
        shapes = new int[4][][];
        for (int rotation = 0; rotation < 4; rotation++) {
            int r = shape[0].length;
            int c = shape.length;
            shapes[rotation] = new int[r][c];
            for (int y = 0; y < r; y++) {
                for (int x = 0; x < c; x++) {
                    shapes[rotation][y][x] = shape[c-x-1][y];
                }
            }
            shape = shapes[rotation];
        }
    }

    /**
     * Spawns the block at the top center of the game board.
     */
    public void spawn() {
        currentRotation = 3;
        shape = shapes[currentRotation];
        // spawn block above the board
        y = -1;
        // calculate center of the board
        x = (5-2)/getWidth();
    }

    // Movement methods, getters and setters

    public void moveDown(){ y++; }
    public void moveLeft(){ x--; }
    public void moveRight(){ x++; }
    public void rotate() {
        currentRotation++;
        if (currentRotation>3) currentRotation=0;
        shape = shapes[currentRotation];
    }
    public int[][] getShape() { return shape; }
    public Color getColor() { return color; }
    public int getHeight() { return shape.length; };
    public int getWidth() { return shape[0].length; };
    public int getBottomEdge() { return y + getHeight(); }
    public int getLeftEdge() { return x; }
    public int getRightEdge() { return x + getWidth(); }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getCurrentRotation() { return currentRotation; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setCurrentRotation(int currentRotation) {
        this.currentRotation = currentRotation;
        this.shape = shapes[currentRotation];
    }

    // Helper methods

    /**
     * Helper method. Creates a deep copy of a 2D integer matrix.
     *
     * @param original The original 2D integer matrix to copy.
     * @return A deep copy of the original matrix.
     */
    private int[][] deepCopyMatrix(int[][] original) {
        if (original == null) {
            return null;
        }
        int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = original[i].clone();
        }
        return result;
    }

    /**
     * Helper method. Creates a deep copy of a 3D integer matrix.
     *
     * @param original The original 3D integer matrix to copy.
     * @return A deep copy of the original matrix.
     */
    private int[][][] deepCopy3DMatrix(int[][][] original) {
        if (original == null) {
            return null;
        }
        int[][][] result = new int[original.length][][];
        for (int i = 0; i < original.length; i++) {
            result[i] = deepCopyMatrix(original[i]);
        }
        return result;
    }
}
