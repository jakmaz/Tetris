package tetris;

/**
 * A thread class responsible for executing a sequence of predetermined moves in the Tetris game.
 * This class simulates gameplay by spawning blocks and performing specific moves in a set order.
 */
public class SequenceThread extends Thread {
    private final GameArea ga;

    /**
     * Constructs a SequenceThread with a reference to the game area.
     *
     * @param ga The game area where the sequence of moves will be executed.
     */
    public SequenceThread(GameArea ga) { this.ga = ga; }

    /**
     * The main execution method for the thread.
     * Runs a loop that executes a series of predefined moves in the game area.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                firstMove();
                secondMove();
                thirdMove();
                fourthMove();
                fifthMove();
                sixthMove();
                seventhMove();
                eighthMove();
                ninthMove();
                tenthMove();
                eleventhMove();
                twelfthMove();
            }
        } catch (InterruptedException e) {
            // The thread was interrupted, so stop the thread
        }
    }

    private void moveDown() throws InterruptedException {
        if (ga.moveBlockDown()) {
            Thread.sleep(50);
        }
    }

    private void firstMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        ga.rotateBlock();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void secondMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        ga.moveBlockLeft();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void thirdMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void fourthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void fifthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.moveBlockLeft();
        moveDown();
        ga.moveBlockLeft();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void sixthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void seventhMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockLeft();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void eighthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void ninthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void tenthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void eleventhMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
    private void twelfthMove() throws InterruptedException {
        ga.spawnBlock();
        moveDown();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.rotateBlock();
        moveDown();
        moveDown();
        ga.moveBlockRight();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        ga.moveBlockToBackground();
        ga.clearLine();
    }
}
