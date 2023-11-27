package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class WShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation W
                {0, 0, 1},
                {0, 1, 1},
                {1, 1, 0}
        };
    }

    private static Color setColor() {
        return new Color(0, 255, 255);
    }


    public WShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

