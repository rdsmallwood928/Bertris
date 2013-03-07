package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultGameWorldForm extends Application implements IGameWorldForm{

    Scene gameSurface;
    Group sceneNodes;
    GameWorldPresenter presenter;

    public DefaultGameWorldForm(GameWorldPresenter presenter) {
        this.presenter =  presenter;
        presenter.setForm(this);
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        this.gameSurface = new Scene(root, 300, 150);
        stage.setScene(gameSurface);
        stage.setTitle("Bertris");
        stage.show();
    }

    @Override
    public void setSceneNodes(Group sceneNodes) {

    }

    @Override
    public Group getSceneNodes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Scene getGameSurface() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateLabels() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buildAndSetGameLoop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
