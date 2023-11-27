package tetris;

public class GameThread extends Thread {
    private GameArea ga;
    int pause = 1000;
    public GameThread(GameArea ga) {
        this.ga = ga;
    }

    @Override
    public void run() {
        while (true) {
            ga.spawnBlock();
            while (ga.moveBlockDown()) {
                try {
                    pause = ga.getPause();
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ga.isBlockOutOfBounds()) {
                ga.gameOver();
                break;
            }
            ga.moveBlockToBackground();
            ga.clearLine();
        }
    }
}
