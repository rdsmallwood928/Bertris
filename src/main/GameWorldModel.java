package main;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameWorldModel {

    private int framesPerSecond = 60;
    private String windowTitle = "GameWorld";

    public GameWorldModel(int fps, String title) {
        this.framesPerSecond = fps;
        this.windowTitle = title;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

}
