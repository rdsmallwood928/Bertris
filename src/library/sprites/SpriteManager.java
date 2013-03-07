package library.sprites;

import main.IGameWorldForm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteManager {

    private final List<Sprite> gameActors = new CopyOnWriteArrayList<Sprite>();
    private final List<Sprite> checkCollisionList = new ArrayList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private IGameWorldForm view = null;

    public SpriteManager(IGameWorldForm view) {
        this.view = view;
    }

    public List<Sprite> getAllSprites() {
        return new ArrayList<Sprite>(gameActors);
    }

    public void addSprites(List<Sprite> sprites) {
        for(Sprite sprite : sprites) {
            view.getSceneNodes().getChildren().add(sprite.node);
        }
        gameActors.addAll(sprites);
    }

    public void removeSprites(List sprites) {
        gameActors.removeAll(sprites);
    }

    public Set getSpritesToBeRemoved() {
        return cleanUpSprites;
    }

    public void addSpritesToBeRemoved(List<Sprite> sprites) {
        cleanUpSprites.addAll(sprites);
    }

    public void addSpritesToBeRemoved(Sprite sprite) {
        cleanUpSprites.add(sprite);
    }

    public List<Sprite> getCollisionsToCheck() {
        return checkCollisionList;
    }

    public void resetCollisionsToCheck() {
        checkCollisionList.clear();
        checkCollisionList.addAll(gameActors);
    }

    public void cleanUpSprites() {
        gameActors.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    public void addSprite(Sprite sprite) {
        view.getSceneNodes().getChildren().add(sprite.node);
        gameActors.add(sprite);
    }
}
