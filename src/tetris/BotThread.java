package tetris;

import java.util.Random;

/**
 * This class represents an automated bot thread for playing Tetris.
 * It simulates a player by spawning blocks and deciding moves based on the mode.
 */
public class BotThread extends Thread {
    private final GameArea ga;
    private final int mode;
    final int pause = 500;

    /**
     * Creates a bot thread to automate moves in the Tetris game.
     *
     * @param ga   The game area where the bot will operate.
     * @param mode The mode of operation which decides the bot's behavior.
     *             Mode 1: Random moves.
     *             Mode 2: Automated moves using heuristic algorithm.
     *             Mode 3: Automated moves using heuristic algorithm with knowledge of the next move.
     */

    public BotThread(GameArea ga, int mode) {
        this.ga = ga;
        this.mode = mode;
    }

    /**
     * The main execution method for the bot thread.
     * It runs continuously until the thread is interrupted,
     */
    @Override
    public void run() {
        try {
            System.out.println(mode);
            while (!Thread.currentThread().isInterrupted()) {
                ga.spawnBlock();
                while (ga.moveBlockDown()) {
                    if (mode == 1) {
                        randomMove();
                    }
                    if (mode == 2) {
                        ga.automatedBotMove();
                    } else if (mode == 3) {
                        ga.automatedBotMove2();
                    }
                    Thread.sleep(pause);
                    ga.dropBlock();
                }
                if (ga.isBlockOnTop()) {
                    ga.gameOver(false);
                    continue;
                }
                ga.moveBlockToBackground();
                ga.clearLine();
            }
        } catch (InterruptedException e) {
            // The thread was interrupted, so stop the thread
        }
    }

    /**
     * Executes a random move for the Tetris game.
     * The move could be either a left move, a right move, or a rotation.
     */
    private void randomMove() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int move = random.nextInt(3);
            Thread.sleep(200);
            if (move == 0) {
                ga.moveBlockLeft();
            } else if (move == 1) {
                ga.moveBlockRight();
            } else {
                ga.rotateBlock();
            }
        }
    }
}
