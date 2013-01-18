package ShipNavigator;

import AtomSmasher.Atom;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.GameWorldPresenter;
import main.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/19/12
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigateShipPresenter extends GameWorldPresenter {

    public NavigateShipPresenter(int fps, String title) {
        super(fps, title);
    }

    @Override
    public void start(Stage primaryStage) {
        buildAndSetGameLoop();
        beginGameLoop();
    }

    protected void handleUpdate(Sprite sprite) {
        sprite.update();
        if (sprite instanceof Missile) {
            removeMissiles((Missile) sprite);
        } else {
            bounceOffWalls(sprite);
        }
    }

    private void bounceOffWalls(Sprite sprite) {
        Node displayNode;
        displayNode = sprite.node;

        // Get the group node's X and Y but use the ImageView to obtain the width.
        if (sprite.node.getTranslateX() > (getForm().getGameSurface().getWidth() - displayNode.getBoundsInParent().getWidth()) ||
                displayNode.getTranslateX() < 0) {

            // bounce the opposite direction
            sprite.vX = sprite.vX * -1;

        }
        // Get the group node's X and Y but use the ImageView to obtain the height.
        if (sprite.node.getTranslateY() > getForm().getGameSurface().getHeight() - displayNode.getBoundsInParent().getHeight() ||
                sprite.node.getTranslateY() < 0) {
            sprite.vY = sprite.vY * -1;
        }
    }

    private void removeMissiles(Missile missile) {
        // bounce off the walls when outside of boundaries
        List wrapper = new ArrayList();
        wrapper.add(missile);
        if (missile.node.getTranslateX() > (getForm().getGameSurface().getWidth() - missile.node.getBoundsInParent().getWidth()) || missile.node.getTranslateX() < 0) {
            getSpriteManager().addSpritesToBeRemoved(wrapper);
            getSceneNodes().getChildren().remove(missile.node);
        }
        if (missile.node.getTranslateY() > getForm().getGameSurface().getHeight() - missile.node.getBoundsInParent().getHeight() || missile.node.getTranslateY() < 0) {
            getSpriteManager().addSpritesToBeRemoved(wrapper);
            getSceneNodes().getChildren().remove(missile.node);
        }
    }

    @Override
    protected boolean handleCollision(Sprite sprite, Sprite otherSprite) {
        if(sprite.collide(otherSprite)) {
            sprite.implode(this);
            otherSprite.implode(this);
            ArrayList<Sprite> wrapper = new ArrayList<Sprite>();
            handleSpriteType(sprite,wrapper);
            handleSpriteType(otherSprite, wrapper);
            getSpriteManager().addSpritesToBeRemoved(wrapper);
            return true;
        }
        return false;
    }

    private void handleSpriteType(Sprite sprite, ArrayList<Sprite> wrapper) {
        switch (sprite.getType()) {
            case SHIP:
                break;
            default:
                wrapper.add(sprite);
                break;
        }
    }

    public void hurtShip() {
        getForm().updateLabels();
    }
}
