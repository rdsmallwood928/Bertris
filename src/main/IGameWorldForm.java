package main;

import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IGameWorldForm {
    public void setSceneNodes(Group sceneNodes);
    public Group getSceneNodes();
    public Scene getGameSurface();
    public void updateLabels();
    public void buildAndSetGameLoop();
}
