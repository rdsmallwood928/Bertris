package AtomSmasher;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.IGameWorldForm;
import main.sprites.Sprite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/18/12
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AtomSmasherForm extends Application implements IGameWorldForm {

    AtomSmasherPresenter presenter;
    Scene gameSurface;
    Stage mainStage;
    Group sceneNodes;
    Label numSpritesLabel;

    public AtomSmasherForm() {
        presenter = new AtomSmasherPresenter(60, "Atom Smasher");
        presenter.setForm(this);
        presenter.start(mainStage);
    }

    public static void launch() {
        Application.launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        stage.setTitle(presenter.getWindowTitle());
        setSceneNodes(new Group());
        gameSurface = new Scene(sceneNodes, 640, 580);
        stage.setScene(gameSurface);
        generateManySpheres(150);
        final Timeline gameLoop = presenter.getGameLoop();
        numSpritesLabel = new Label();
        VBoxBuilder vBoxBuilder = VBoxBuilder.create();
        vBoxBuilder.spacing(5);
        vBoxBuilder.translateX(10);
        vBoxBuilder.translateY(10);

        HBoxBuilder hBoxBuilder = HBoxBuilder.create();
        hBoxBuilder.spacing(5);
        Collection<Node> hNodes = new ArrayList<Node>();
        hNodes.add(new Label("Number of Particles: "));
        hNodes.add(numSpritesLabel);
        hBoxBuilder.children(new Label("Number of Particles: "), numSpritesLabel);
        HBox hBox = hBoxBuilder.build();

        Button regenerateButton = ButtonBuilder.create().text("Regenerate").onMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                generateManySpheres(150);
            }
        }).build();

        Button startStopButton = ButtonBuilder.create().text("Start/Stop").onMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch(gameLoop.getStatus()) {
                    case RUNNING:
                        gameLoop.stop();
                        break;
                    case STOPPED:
                        gameLoop.play();
                        break;
                }
            }
        }).build();
        vBoxBuilder.children(hBox, regenerateButton, startStopButton);
        getSceneNodes().getChildren().add(vBoxBuilder.build());
        mainStage.show();
    }

    private void generateManySpheres(int numSpheres) {
        Random rnd = new Random();
        ArrayList<Sprite> atoms = new ArrayList<Sprite>();
        for (int i=0; i<numSpheres; i++) {
            Color c = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
            Atom atom = new Atom(rnd.nextInt(15) + 5, c, true);
            Circle circle = atom.getAsCircle();
            // random 0 to 2 + (.0 to 1) * random (1 or -1)
            atom.vX = (rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);
            atom.vY = (rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);

            // random x between 0 to width of scene
            double newX = rnd.nextInt((int) gameSurface.getWidth());

            // check for the right of the width newX is greater than width
            // minus radius times 2(width of sprite)
            if (newX > (gameSurface.getWidth() - (circle.getRadius() * 2))) {
                newX = gameSurface.getWidth() - (circle.getRadius()  * 2);
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
            circle.setId(atom.toString());

            // add sprite's
            atoms.add(atom);
            getSceneNodes().getChildren().add(0, atom.node);
        }
        presenter.getSpriteManager().addSprites(atoms);
    }

    @Override
    public void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }

    @Override
    public Group getSceneNodes() {
        return sceneNodes;
    }

    @Override
    public Scene getGameSurface() {
        return this.gameSurface;
    }

    @Override
    public void updateLabels() {
        numSpritesLabel.setText(String.valueOf(presenter.getSpriteManager().getAllSprites().size()));
    }


}
