package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class IShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation I
                {1},
                {1},
                {1},
                {1},
                {1}
        };
    }

    private static Color setColor() {
        return new Color(255, 179, 71);
    }


    public IShape() {
        super(setShape(), setColor()); // Static method is used here
    }
}

