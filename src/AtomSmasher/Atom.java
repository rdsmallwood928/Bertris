package AtomSmasher;

import ShipNavigator.Missile;
import ShipNavigator.Ship;
import javafx.animation.FadeTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.util.Duration;
import library.sprites.BlockType;
import library.sprites.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Atom extends Sprite {

    public Atom(double radius, Color fill, boolean gradiantFill) {
        super(BlockType.TBLOCK);
        Circle sphere = CircleBuilder.create()
                .centerX(radius)
                .centerY(radius)
                .fill(fill)
                .radius(radius)
                .cache(true)
                .build();

        if(gradiantFill) {
            RadialGradient rgrad = RadialGradientBuilder.create()
                    .centerX(sphere.getCenterX() - sphere.getRadius() / 3)
                    .centerY(sphere.getCenterY() - sphere.getRadius() / 3)
                    .radius(sphere.getRadius())
                    .proportional(false)
                    .stops(new Stop(0.0, fill), new Stop(1.0, Color.BLACK))
                    .build();

            sphere.setFill(rgrad);
        }

        // set javafx node to a circle
        node = sphere;

    }

    /**
     * Change the velocity of the atom particle.
     */
    @Override
    public void update() {
        node.setTranslateX(node.getTranslateX() + vX);
        node.setTranslateY(node.getTranslateY() + vY);
    }

    @Override
    public boolean collide(Sprite other) {
        if(other instanceof Missile) {
            return doCirclesCollide(((Missile)other).getAsCircle(), getAsCircle());
        }
        if (other instanceof Ship) {
            return doCirclesCollide(((Ship)other).getAsCircle(), getAsCircle());
        }
        if (other instanceof Atom) {
            return collide((Atom)other);
        }
        return false;
    }

    private boolean doCirclesCollide(Circle one, Circle two) {
        double dx = two.getTranslateX() - one.getTranslateX();
        double dy = two.getTranslateY() - one.getTranslateY();
        double distance = Math.sqrt( dx * dx + dy * dy );
        double minDist  = two.getRadius() + one.getRadius() + 3;
        return (distance < minDist);
    }

    /**
     * When encountering another Atom to determine if they collided.
     * @param other Another atom
     * @return boolean true if this atom and other atom has collided,
     * otherwise false.
     */
    private boolean collide(Atom other) {

        // if an object is hidden they didn't collide.
        if (!node.isVisible() ||
                !other.node.isVisible() ||
                this == other) {
            return false;
        }

        // determine it's size
        Circle otherSphere = other.getAsCircle();
        Circle thisSphere =  getAsCircle();
        double dx = otherSphere.getTranslateX() - thisSphere.getTranslateX();
        double dy = otherSphere.getTranslateY() - thisSphere.getTranslateY();
        double distance = Math.sqrt( dx * dx + dy * dy );
        double minDist  = otherSphere.getRadius() + thisSphere.getRadius() + 3;

        return (distance < minDist);
    }

    /**
     * Returns a node casted as a JavaFX Circle shape.
     * @return Circle shape representing JavaFX node for convenience.
     */
    public Circle getAsCircle() {
        return (Circle) node;
    }

    public void implode() {
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
                        //gameWorld.getSceneNodes().getChildren().remove(node);
                    }
                })
                .build()
                .play();
    }
}
