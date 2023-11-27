package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class TShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation T
                {1, 1, 1},
                {0, 1, 0},
                {0, 1, 0}
        };
    }

    private static Color setColor() {
        return new Color(165, 102, 255);
    }


    public TShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

