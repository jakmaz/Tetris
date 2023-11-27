package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class VShape  extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][] {
                // pentomino representation V 5.1
                {1, 1, 1},
                {1, 0, 0},
                {1, 0, 0}
        };
    }

    private static Color setColor() {
        return new Color(255, 102, 178);
    }


    public VShape () {
        super(setShape(), setColor()); // Static method is used here
    }
}

