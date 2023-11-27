package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class FShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                {0, 1, 1},
                {1, 1, 0},
                {0, 1, 0}
        };
    }

    private static Color setColor() {
        return new Color(255, 105, 97);
    }


    public FShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

