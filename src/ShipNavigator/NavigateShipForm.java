package ShipNavigator;

import AtomSmasher.Atom;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.IGameWorldForm;
import main.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/19/12
 * Time: 7:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigateShipForm extends Application implements IGameWorldForm {

    NavigateShipPresenter presenter;
    Stage mainStage;
    Label mousePtLabel = new Label();
    TextField xCoordinate = new TextField("234");
    TextField yCoordinate = new TextField("200");
    Button moveShipButton = new Button("Rotate ship");
    Ship myShip = new Ship();
    Scene mainScene = null;
    Group sceneNodes = null;

    public NavigateShipForm() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                presenter = new NavigateShipPresenter(60, "Navigate Ship");
                presenter.setForm(NavigateShipForm.this);
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        stage.setTitle(presenter.getWindowTitle());
        mainStage.setScene(getGameSurface());
        mainStage.show();
        setupInput(mainStage);
        presenter.start(mainStage);
        List<Sprite> wrapper = new ArrayList<>();
        wrapper.add(myShip);
        presenter.getSpriteManager().addSprites(wrapper);
        getSceneNodes().getChildren().add(myShip.node);
    }

    private void setupInput(Stage mainStage) {
        System.out.println("Ship's center is (" + myShip.getCenterX() + "," + myShip.getCenterY() + ")");
        EventHandler fireOrMove = new EventHandler() {

            @Override
            public void handle(Event event) {
                if(event instanceof MouseEvent) {
                    MouseEvent mouseEvent = (MouseEvent) event;
                    System.out.println("Mouse Press PT = (" + mouseEvent.getX() + ", " + mouseEvent.getY() + ")");
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        myShip.plotCourse(mouseEvent.getX(), mouseEvent.getY(), false);
                        Missile missile = myShip.fire();
                        List wrapper = new ArrayList();
                        wrapper.add(missile);
                        presenter.getSpriteManager().addSprites(wrapper);
                        getSceneNodes().getChildren().add(0, missile.node);
                    } else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        if(presenter.getSpriteManager().getAllSprites().size() <= 1) {
                            generateManySpheres(30);
                        }
                        myShip.applyTheBrakes(mouseEvent.getX(), mouseEvent.getY());
                        myShip.plotCourse(mouseEvent.getX(), mouseEvent.getY(), true);
                    }

                }
                if(event instanceof KeyEvent) {
                    KeyEvent keyEvent = (KeyEvent) event;
                    System.out.println(keyEvent.getCharacter());
                }
            }
        };

        mainStage.getScene().setOnMousePressed(fireOrMove);
        mainStage.getScene().setOnKeyPressed(fireOrMove);

/*        EventHandler changeWeapons = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(KeyCode.SPACE == event.getCode()) {
                    myShip.s
                }
            }
        }*/

/*        EventHandler showMouseMove = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePtLabel.setText("Mouse PT = (" + event.getX() + ", " + event.getY() + ")");
            }
        };

        mainStage.getScene().setOnMouseMoved(showMouseMove);*/
    }

    private void generateManySpheres(int numSpheres) {
        Random rnd = new Random();
        Scene gameSurface = getGameSurface();
        for (int i = 0; i < numSpheres; i++) {
            Color c = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
            Atom b = new Atom(rnd.nextInt(15) + 5, c, true);
            Circle circle = b.getAsCircle();
            // random 0 to 2 + (.0 to 1) * random (1 or -1)
            b.vX = (rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);
            b.vY = (rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);

            // random x between 0 to width of scene
            double newX = rnd.nextInt((int) gameSurface.getWidth());

            // check for the right of the width newX is greater than width
            // minus radius times 2(width of sprite)
            if (newX > (gameSurface.getWidth() - (circle.getRadius() * 2))) {
                newX = gameSurface.getWidth() - (circle.getRadius() * 2);
            }

            // check for the bottom of screen the height newY is greater than height
            // minus radius times 2(height of sprite)
            double newY = rnd.nextInt((int) gameSurface.getHeight());
            if (newY > (gameSurface.getHeight() - (circle.getRadius() * 2))) {
                newY = gameSurface.getHeight() - (circle.getRadius() * 2);
            }

            circle.setTranslateX(newX);
            circle.setTranslateY(newY);
            circle.setVisible(true);
            circle.setId(b.toString());
            circle.setCache(true);
            circle.setCacheHint(CacheHint.SPEED);
            circle.setManaged(false);
            // add to actors in play (sprite objects)
            List wrapper = new ArrayList();
            wrapper.add(b);
            presenter.getSpriteManager().addSprites(wrapper);

            // add sprite's
            getSceneNodes().getChildren().add(b.node);

        }
    }

    public static void launch() {
        Application.launch();
    }

    @Override
    public void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }

    @Override
    public Group getSceneNodes() {
        if(sceneNodes == null) {
            setSceneNodes(new Group());
        }
        return sceneNodes;
    }

    @Override
    public Scene getGameSurface() {
        if(mainScene == null) {
            Pane pane = new Pane();
            mainScene = new Scene(pane, 800, 600);
            pane.getChildren().add(getSceneNodes());
            mainScene.setFill(Color.BLACK);
        }
        return mainScene;
    }

    @Override
    public void updateLabels() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
