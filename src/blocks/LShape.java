package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class LShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation L
                {0, 0, 0, 1},
                {1, 1, 1, 1}
        };
    }

    private static Color setColor() {
        return new Color(255, 255, 128);
    }


    public LShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

