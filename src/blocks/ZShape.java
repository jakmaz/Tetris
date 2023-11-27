package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class ZShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation Z
                {0, 1, 1},
                {0, 1, 0},
                {1, 1, 0}
        };
    }

    private static Color setColor() {
        return new Color(0, 153, 252);
    }


    public ZShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}
