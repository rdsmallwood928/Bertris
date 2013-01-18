package ShipNavigator;

import AtomSmasher.Atom;
import javafx.animation.FadeTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.GameWorldPresenter;
import main.sprites.Sprite;
import main.sprites.SpriteType;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/20/12
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Missile extends Atom {
    public Missile(Color fill) {
        super(5, fill, false);
    }

    public Missile(int radius, Color fill) {
        super(radius, fill, true);
    }

    @Override
    public boolean collide(Sprite other) {
        // if an object is hidden they didn't collide.
        if (!node.isVisible() || !other.node.isVisible() || this == other) {
            return false;
        }
        if(other instanceof Atom) {
            if(!(other instanceof Missile)){
                return ((Atom)other).collide(this);
            }
        }
        return false;
    }

    public void implode(final GameWorldPresenter presenter) {
        vX = vY = 0;
        FadeTransitionBuilder.create()
                .node(node)
                .duration(Duration.millis(300))
                .fromValue(node.getOpacity())
                .toValue(0)
                .onFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        isDead = true;
                        presenter.getSceneNodes().getChildren().remove(node);
                    }
                })
                .build()
                .play();
    }

    public Circle getAsCircle() {
        return (Circle) node;
    }

}
