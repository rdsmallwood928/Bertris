package ShipNavigator;

import javafx.animation.FadeTransitionBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import library.Vec;
import library.sprites.BlockType;
import library.sprites.Sprite;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/19/12
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ship extends Sprite {

    private final static int TWO_PI_DEGREES = 360;
    private final static int NUM_DIRECTIONS = 32;
    private final static float UNIT_ANGLE_PER_FRAME = ((float) TWO_PI_DEGREES/NUM_DIRECTIONS);
    private final static int MILLIS_TURN_SHIP_180_DEGREES = 300;
    private final static float MILLIS_PER_FRAME = (float) MILLIS_TURN_SHIP_180_DEGREES/(NUM_DIRECTIONS/2);
    private double shipHealth = 1;

    private enum Direction{
        CLOCKWISE, COUNTER_CLOCKWISE, NEITHER
    }
    private final static float THRUST_AMOUNT = 3.3f;
    private final static float MISSLE_THRUST_AMOUNT = 6.3f;
    private Direction turnDirection = Direction.NEITHER;
    private Vec shipVector;
    private final List<RotatedShipImage> directionalShips = new ArrayList<>(NUM_DIRECTIONS);
    private Timeline rotatedShipTimeLine;
    private int startShipAnimationIndex = 0;
    private int endShipAnimationIndex = 0;
    private final Circle stopArea = new Circle();
    private final Group flipBook = new Group();
    private KeyCode keyCode;

    public Ship() {
        super(BlockType.TBLOCK);
        WritableImage shipImage = null;
        try {
            shipImage = SwingFXUtils.toFXImage(ImageIO.read(new File("./Resources/ship.png")), new WritableImage(10, 100));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        stopArea.setRadius(40);
        RotatedShipImage prev = null;
        for(int i = 0; i < NUM_DIRECTIONS; i++) {
            RotatedShipImage imageView = new RotatedShipImage();
            imageView.setImage(shipImage);
            imageView.setRotate(-1*i*UNIT_ANGLE_PER_FRAME);
            imageView.setCache(true);
            imageView.setCacheHint(CacheHint.SPEED);
            imageView.setManaged(false);
            imageView.prev = prev;
            imageView.setVisible(false);
            directionalShips.add(imageView);
            if(prev != null) {
                prev.next = imageView;
            }
            prev = imageView;
            flipBook.getChildren().add(imageView);
        }
        RotatedShipImage firstShip = directionalShips.get(0);
        firstShip.prev = prev;
        prev.next = firstShip;
        firstShip.setVisible(true);
        node = flipBook;
        flipBook.setTranslateX(0);
        flipBook.setTranslateY(0);
    }

    private RotatedShipImage getCurrentShipImage() {
        return directionalShips.get(startShipAnimationIndex);
    }


    @Override
    public void update() {
        flipBook.setTranslateX(flipBook.getTranslateX() + vX);
        flipBook.setTranslateY(flipBook.getTranslateY() + vY);
        if(stopArea.contains(getCenterX(), getCenterY())){
            vX = 0;
            vY = 0;
        }
    }

    @Override
    public void implode() {
        shipHealth = shipHealth-.2;
        if(shipHealth < 0) {
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
                            //presenter.getSceneNodes().getChildren().remove(node);
                        }
                    })
                    .build()
                    .play();
        }
        //((NavigateShipPresenter)presenter).hurtShip();
    }

    public Double getCenterX() {
        RotatedShipImage shipImage = getCurrentShipImage();
        return node.getTranslateX() + (shipImage.getBoundsInLocal().getWidth()/2);
    }

    public Circle getAsCircle() {
        Circle boundsAsCircle = new Circle();
        boundsAsCircle.setRadius(getCurrentShipImage().getBoundsInLocal().getWidth()/2);
        boundsAsCircle.setTranslateX(node.getTranslateX());
        boundsAsCircle.setTranslateY(node.getTranslateY());
        return boundsAsCircle;
    }

    public Double getCenterY() {
        RotatedShipImage shipImage = getCurrentShipImage();
        return node.getTranslateY() + (shipImage.getBoundsInLocal().getHeight()/2);
    }

    public void plotCourse(double screenX, double screenY, boolean thrust) {
        Vec newVector = new Vec(screenX, screenY, getCenterX(), getCenterY());
        if(shipVector == null) {
            shipVector = new Vec(Float.parseFloat(getCenterX().toString()),Float.parseFloat(getCenterY().toString()));
        }

        System.out.println("Vector is: " + shipVector);

        double radiansShip = Math.atan2(shipVector.y, shipVector.x);
        double thetaInDegreesShip = Math.toDegrees(radiansShip);

        double thetaNew = Math.atan2(newVector.y, newVector.x);
        double thetaInDeg = Math.toDegrees(thetaNew);

        double angleBetweenShipAndNew = thetaInDeg - thetaInDegreesShip;
        double absAngelBetweenShipAndNew = Math.abs(angleBetweenShipAndNew);

        boolean goOtherWay = false;

        if(absAngelBetweenShipAndNew > 180) {
            if(angleBetweenShipAndNew < 0) {
                turnDirection = Direction.COUNTER_CLOCKWISE;
                goOtherWay = true;
            } else if(angleBetweenShipAndNew > 0) {
                turnDirection = Direction.CLOCKWISE;
                goOtherWay = true;
            } else {
                turnDirection = Direction.NEITHER;
            }
        } else {
            if(angleBetweenShipAndNew < 0) {
                turnDirection = Direction.CLOCKWISE;
            } else if(angleBetweenShipAndNew > 0) {
                turnDirection = Direction.COUNTER_CLOCKWISE;
            } else {
                turnDirection = Direction.NEITHER;
            }
        }

        double degreesToMove = absAngelBetweenShipAndNew;
        if(goOtherWay) {
            degreesToMove = TWO_PI_DEGREES - absAngelBetweenShipAndNew;
        }


        startShipAnimationIndex = Math.round((float)(thetaInDegreesShip/UNIT_ANGLE_PER_FRAME));
        if(startShipAnimationIndex < 0) {
            startShipAnimationIndex = NUM_DIRECTIONS + startShipAnimationIndex;
        }
        endShipAnimationIndex = Math.round((float)(thetaInDeg/UNIT_ANGLE_PER_FRAME));
        if(endShipAnimationIndex < 0) {
            endShipAnimationIndex = NUM_DIRECTIONS + endShipAnimationIndex;
        }

        if(thrust) {
            vX = Math.cos(thetaNew) * THRUST_AMOUNT;
            vY = -Math.sin(thetaNew) * THRUST_AMOUNT;
        }
        turnShip();
    }

    private void turnShip() {
        final Duration oneFrameAmt = Duration.millis(MILLIS_PER_FRAME);
        RotatedShipImage startImage = directionalShips.get(startShipAnimationIndex);
        RotatedShipImage endImage = directionalShips.get(endShipAnimationIndex);

        List<KeyFrame> frames = new ArrayList<KeyFrame>();

        RotatedShipImage curImage = startImage;
        int i = 1;
        while(true) {
            final Node displayNode = curImage;
            KeyFrame oneFrame = new KeyFrame(oneFrameAmt.multiply(i), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for(RotatedShipImage shipImage : directionalShips) {
                        shipImage.setVisible(false);
                    }
                    displayNode.setVisible(true);
                }
            });

            frames.add(oneFrame);
            if(curImage == endImage) {
                break;
            }
            if(turnDirection == Direction.CLOCKWISE) {
                curImage = curImage.prev;
            }
            if(turnDirection == Direction.COUNTER_CLOCKWISE) {
                curImage = curImage.next;
            }
            i++;
        }
        if(rotatedShipTimeLine != null) {
            rotatedShipTimeLine.stop();
            rotatedShipTimeLine.getKeyFrames().clear();
            rotatedShipTimeLine.getKeyFrames().addAll(frames);
        } else {
            rotatedShipTimeLine = TimelineBuilder.create().keyFrames(frames).build();
        }
        rotatedShipTimeLine.playFromStart();
        //So the missiles fire the right way...
        startShipAnimationIndex = endShipAnimationIndex;
    }

    public void applyTheBrakes(double screenX, double screenY) {
        stopArea.setCenterX(screenX);
        stopArea.setCenterY(screenY);
    }

    public Missile fire() {
        Missile missile = new Missile(Color.RED);
        missile.vX = Math.cos(Math.toRadians(startShipAnimationIndex * UNIT_ANGLE_PER_FRAME)) *(MISSLE_THRUST_AMOUNT);
        missile.vY = -Math.sin(Math.toRadians(startShipAnimationIndex * UNIT_ANGLE_PER_FRAME)) *(MISSLE_THRUST_AMOUNT);

        RotatedShipImage shipImage = directionalShips.get(startShipAnimationIndex);

        double offsetX = (shipImage.getBoundsInLocal().getWidth() - missile.node.getBoundsInLocal().getWidth()) / 2;
        double offsetY = (shipImage.getBoundsInLocal().getHeight() - missile.node.getBoundsInLocal().getHeight()) /2;

        missile.node.setTranslateX(node.getTranslateX() + offsetX + missile.vX);
        missile.node.setTranslateY(node.getTranslateY() + offsetY + missile.vY);
        return missile;
    }

    public void changeWeapon(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public double getShipHealth() {
        return shipHealth;
    }
}

