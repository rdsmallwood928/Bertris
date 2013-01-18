package main.sprites;

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

    private final List<Sprite> gameActors = new CopyOnWriteArrayList<>();
    private final List<Sprite> checkCollisionList = new ArrayList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();

    public SpriteManager() {

    }

    public List<Sprite> getAllSprites() {
        return gameActors;
    }

    public void addSprites(List sprites) {
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
}
