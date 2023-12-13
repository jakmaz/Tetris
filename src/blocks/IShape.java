package blocks;
import tetris.TetrisBlock;

import java.awt.*;

public class IShape extends TetrisBlock {
    private static int[][] setShape() {
        return new int[][]{
                // pentomino representation I
                {1, 1, 1, 1, 1}
        };
    }

    private static Color setColor() {
        return new Color(255, 179, 71);
    }

    public IShape() {
        super(setShape(), setColor()); // Static method is used here
    }

    @Override
    public void rotate() {
        super.rotate();
        if (this.getWidth() == 1) {
            this.setX(this.getX() + 2);
            this.setY(this.getY() - 2);
        } else {
            this.setX(this.getX() - 2);
            this.setY(this.getY() + 2);
        }
    }
}

