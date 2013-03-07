package Bertris;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/21/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BertrisModel {

    private int framesPerSecond = 60;
    private int level = 1;
    private boolean gameOver = false;
    private Long score = 0l;

    public BertrisModel() {

    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    public int getVerticalSpeed() {
        return 60 * level;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
