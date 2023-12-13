package tetris;

/**
 * Represents the main game thread for the Tetris game.
 * This thread controls the game loop, handling the spawning of new blocks,
 * moving them down, and checking for game over conditions.
 */
public class GameThread extends Thread {
    private final GameArea ga;
    int pause = 1000;

    /**
     * Constructs a GameThread with a reference to the game area.
     *
     * @param ga The game area where the game logic will operate.
     */
    public GameThread(GameArea ga) { this.ga = ga; }

    /**
     * The main execution method for the thread.
     * Runs the game loop that spawns new blocks, moves them down,
     * and checks for game over conditions.
     */
    @Override
    public void run() {
        try {
            while (true) {
                ga.spawnBlock();
                while (ga.moveBlockDown()) {
                    pause = ga.getPause();
                    Thread.sleep(pause);
                }
                if (ga.isBlockOnTop()) {
                    ga.gameOver(true);
                    continue;
                }
                ga.moveBlockToBackground();
                ga.clearLine();
            }
        } catch (InterruptedException e) {
            // The thread was interrupted, so stop the thread
        }
    }
}
