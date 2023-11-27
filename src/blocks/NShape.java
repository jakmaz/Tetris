package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class NShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation N 10
                {1, 1, 0, 0},
                {0, 1, 1, 1}
        };
    }

    private static Color setColor() {
        return new Color(128, 255, 0);
    }


    public NShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

