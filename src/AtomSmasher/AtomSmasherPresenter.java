package AtomSmasher;

import javafx.stage.Stage;
import main.GameWorldPresenter;
import library.sprites.Sprite;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class AtomSmasherPresenter extends GameWorldPresenter {

    private AtomSmasherForm atomSmasherForm;

    public AtomSmasherPresenter(int fps, String title) {
        super(fps, title);
    }

    @Override
    public void start(Stage primaryStage) {
        buildAndSetGameLoop();
    }

    protected void handleUpdate(Sprite sprite) {
        if(sprite instanceof Atom) {
            Atom sphere = (Atom) sprite;
            sphere.update();
            if((sphere.node.getTranslateX() > (getForm().getGameSurface().getWidth() - sphere.node.getBoundsInParent().getWidth())) ||
                    sphere.node.getTranslateX() < 0) {
                sphere.vX = sphere.vX * -1;
            }
            if((sphere.node.getTranslateY() > (getForm().getGameSurface().getHeight() - sphere.node.getBoundsInParent().getHeight())) ||
                    sphere.node.getTranslateY() < 0) {
                sphere.vY = sphere.vY * -1;
            }
        }
    }

    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        if(spriteA.collide(spriteB)) {
            ((Atom)spriteA).implode();
            ((Atom)spriteB).implode();
            ArrayList<Sprite> wrapper = new ArrayList<Sprite>();
            wrapper.add(spriteA);
            wrapper.add(spriteB);
            getSpriteManager().addSpritesToBeRemoved(wrapper);
            return true;
        }
        return false;
    }

    protected void cleanupSprites() {
        getSpriteManager().cleanUpSprites();
        getForm().updateLabels();
    }
}
