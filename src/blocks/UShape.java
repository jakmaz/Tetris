package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class UShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation U
                {1, 1},
                {1, 0},
                {1, 1}
        };
    }

    private static Color setColor() {
        return new Color(255, 153, 255);
    }


    public UShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

