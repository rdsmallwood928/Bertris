package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.sprites.Sprite;
import main.sprites.SpriteManager;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameWorldPresenter {

    private GameWorldModel model;
    private IGameWorldForm form;
    private Timeline gameLoop;
    private SpriteManager spriteManager;

    public abstract void start(final Stage primaryStage);

    public GameWorldPresenter(int fps, String title) {
        this.model = new GameWorldModel(fps, title);
        this.spriteManager = new SpriteManager();
    }

    public void setForm(IGameWorldForm form) {
        this.form = form;
    }

    public void buildAndSetGameLoop() {
        final Duration oneFrameAmt = Duration.millis(1000/getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateSprites();
                checkCollisions();
                cleanupSprites();
            }
        });
        
        setGameLoop(TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build());
    }

    public void beginGameLoop() {
        gameLoop.play();
    }

    private void setGameLoop(Timeline gameLoop) {
        this.gameLoop = gameLoop;
    }

    protected void cleanupSprites() {
        spriteManager.cleanUpSprites();
    }

    private void updateSprites() {
        for(Sprite sprite : spriteManager.getAllSprites()) {
            handleUpdate(sprite);
        }
    }

    protected void checkCollisions() {
        spriteManager.resetCollisionsToCheck();
        for(Sprite sprite : spriteManager.getCollisionsToCheck()) {
            for(Sprite spriteB : spriteManager.getAllSprites()) {
                if(handleCollision(sprite, spriteB)) {

                }
            }
        }
    }

    protected boolean handleCollision(Sprite sprite, Sprite spriteB) {
        return false;
    }

    protected void handleUpdate(Sprite sprite) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private int getFramesPerSecond() {
        return model.getFramesPerSecond();
    }

    public Timeline getGameLoop() {
        return gameLoop;
    }

    public String getWindowTitle() {
        return model.getWindowTitle();
    }

    public Group getSceneNodes() {
        return form.getSceneNodes();
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public IGameWorldForm getForm() {
        return form;
    }
}
