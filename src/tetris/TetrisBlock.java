package tetris;

import java.awt.*;

public class TetrisBlock {
    private int[][] shape;
    private final Color color;
    private int x, y;
    private int[][][] shapes;
    private int currentRotation;

    public TetrisBlock(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        initShapes();
    }
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
    public void spawn() {
        currentRotation = 0;
        shape = shapes[currentRotation];
        // spawn block above the board
        y = -getHeight();
        // calculate center of the board
        x = (5-2)/getWidth();
    }
    public void moveDown(){ y++; }
    public void moveLeft(){ x--; }
    public void moveRight(){ x++; }
    public void rotate() {
        currentRotation++;
        if (currentRotation>3) currentRotation=0;
        shape = shapes[currentRotation];
    }
    public void rotateBack() {
        currentRotation--;
        if (currentRotation<0) currentRotation=3;
        shape = shapes[currentRotation];
    }
    public int[][] getShape() { return shape; }
    public Color getColor() { return color; }
    public int getHeight() { return shape.length; };
    public int getWidth() { return shape[0].length; };
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getBottomEdge() { return y + getHeight(); }
    public int getLeftEdge() { return x; }
    public int getRightEdge() { return x + getWidth(); }
}
