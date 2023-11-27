package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class PShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation P 9
                {1, 1, 0},
                {1, 1, 1}
        };
    }

    private static Color setColor() {
        return new Color(102, 178, 255);
    }


    public PShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

