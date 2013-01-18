package main.sprites;

import javafx.scene.Node;
import main.GameWorldPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Sprite {


    final protected SpriteType type;

    private List animations = new ArrayList<>();

    public Sprite(SpriteType type) {
        this.type = type;
    }

    public SpriteType getType() {
        return type;
    }

    public Node node;

    public double vX = 0;

    public double vY = 0;

    public boolean isDead = false;

    public abstract void update();

    public boolean collide(Sprite other) {
        return false;
    }

    public abstract void implode(final GameWorldPresenter presenter);
}
