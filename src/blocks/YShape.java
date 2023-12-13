package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class YShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation Y
                {1, 1, 1, 1},
                {0, 1, 0, 0}
        };
    }

    private static Color setColor() {
        return new Color(153, 255, 153);
    }


    public YShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

