package Bertris;

import Bertris.blocks.AbstractBlock;
import Bertris.blocks.BlockFactory;
import Bertris.blocks.LineBlock;
import Bertris.blocks.TBlock;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.IGameWorldForm;
import library.sprites.Sprite;
import library.sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/21/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BertrisView extends Application implements IGameWorldForm, AbstractBlock.IBlockListener {
    Stage mainStage;
    Scene mainScene = null;
    Pane pane = null;
    Group nodes = new Group();
    Timeline gameLoop = null;
    BertrisModel model = null;
    SpriteManager spriteManager = null;
    Label gameOverLabel;
    private Rectangle blockView;
    private Double sceneHeight = 800d;
    private Double sceneWidth = 1000d;
    private boolean rotateBlock = false;
    private boolean warpBlock = false;
    private KeyCode moveBlock = null;
    private BorderPane gameOverPane = new BorderPane();
    private Button newGameButton = new Button("New Game");
    private ScoreDetails theScore = new ScoreDetails("Bert", 0l);
    private ObservableList<ScoreDetails> rows;
    private TableView scoreTable;
    private Label scoreLabel = new Label();

    public BertrisView() {
        model = new BertrisModel();

    }

    public static void main(String[] args) {
        BertrisView.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setScene(getGameSurface());
        setSceneNodes(getSceneNodes());
        spriteManager = new SpriteManager(this);
        mainStage.show();
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                        rotateCurrentBlock();
                        break;
                    case LEFT:
                    case RIGHT:
                        moveBlock(keyEvent.getCode());
                        break;
                    case DOWN:
                        warpBlock();
                        break;
                    default:
                        break;

                }
            }
        });

        gameOverLabel.setText("Game Over");
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setFont(new Font("Arial", 64));
        gameOverPane.setCenter(gameOverLabel);
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(newGameButton);
        gameOverPane.setBottom(bottom);
        gameOverPane.setVisible(true);

//        scoreTable = new TableView();
//        TableColumn name = new TableColumn("Name");
//        name.setCellValueFactory(new PropertyValueFactory<ScoreDetails, String>("Name"));
//        TableColumn score = new TableColumn("Score");
//        score.setCellValueFactory(new PropertyValueFactory<ScoreDetails, String>("Score"));
//        scoreTable.getColumns().addAll(name, score);
//        rows = FXCollections.observableArrayList();
//        rows.add(theScore);
//        scoreTable.setTranslateY(blockView.getTranslateY() + (3 * blockView.getHeight() / 4));
//        scoreTable.setTranslateX(blockView.getTranslateX() + blockView.getWidth() + Globals.BLOCK_WIDTH);
//        scoreTable.setMaxHeight(blockView.getHeight() / 4);
//        scoreTable.setPrefWidth(250);
//        scoreTable.setItems(rows);
//        scoreTable.setEditable(false);
//        name.setMinWidth(scoreTable.getPrefWidth() / 2);
//        score.setMinWidth(scoreTable.getPrefWidth()/2);
//        getSceneNodes().getChildren().add(scoreTable);


        getSceneNodes().getChildren().add(scoreLabel);
        scoreLabel.setTranslateX(blockView.getTranslateX() + blockView.getWidth() + Globals.BLOCK_WIDTH);
        scoreLabel.setTranslateY(blockView.getTranslateY());
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", 60));


        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.setGameOver(false);
                getSceneNodes().getChildren().remove(gameOverPane);
                startNewPiece();
            }
        });
        buildAndSetGameLoop();
        beginGameLoop();
        startNewPiece();
    }

    private void warpBlock() {
        warpBlock = true;
    }

    private void moveBlock(KeyCode code) {
        moveBlock = code;
    }

    private void rotateCurrentBlock() {
        rotateBlock = true;
    }

    public void beginGameLoop() {
        gameLoop.play();
    }

    int blockCtr = 0;
    public void startNewPiece() {
        if(!model.isGameOver()) {
            warpBlock = false;
            updates = 0;
            AbstractBlock block = BlockFactory.getRandomBlock(blockCtr);
            blockCtr++;
            block.addListener(this);
            block.setTranslateX((blockView.getTranslateX() + (Globals.GAME_WIDTHS_BLOCKS/2)*Globals.BLOCK_WIDTH));
            block.setTranslateY((blockView.getTranslateY() - Globals.BLOCK_WIDTH));
            spriteManager.addSprite(block);
            spriteManager.resetCollisionsToCheck();
        }
    }

    public Scene getGameSurface() {
        if(mainScene == null) {
            pane = new Pane();
            mainScene = new Scene(pane, sceneHeight, sceneWidth);
            pane.getChildren().add(getSceneNodes());
            blockView = new Rectangle();
            blockView.setHeight(Globals.BLOCK_WIDTH*Globals.GAME_HEIGHT_BLOCKS);
            blockView.setWidth(Globals.BLOCK_WIDTH*Globals.GAME_WIDTHS_BLOCKS);
            blockView.setTranslateX(50);
            blockView.setTranslateY((sceneHeight-Globals.BLOCK_WIDTH*Globals.GAME_HEIGHT_BLOCKS)/2);
            blockView.setFill(Color.BLACK);
            getSceneNodes().getChildren().add(blockView);
            gameOverLabel = new Label();
            getSceneNodes().getChildren().add(gameOverLabel);
            mainScene.setFill(Color.BLUEVIOLET);
        }
        return mainScene;
    }

    @Override
    public void updateLabels() {
        if(model.isGameOver() && !getSceneNodes().getChildren().contains(gameOverPane)) {
            getSceneNodes().getChildren().add(gameOverPane);
            gameOverLabel.setVisible(true);
            gameOverPane.setTranslateY((blockView.getTranslateY() + blockView.getHeight() / 2) - gameOverLabel.getHeight()/2);
            gameOverPane.setTranslateX((blockView.getTranslateX() + blockView.getWidth() / 2) - gameOverLabel.getWidth()/2);
        }
        scoreLabel.setText("Score: " + model.getScore().toString());
    }

    @Override
    public void buildAndSetGameLoop() {
        final Duration oneFrameAmt = Duration.millis(1000/getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateSprites();
                checkCollisions();
                cleanupSprites();
                updateLabels();
            }
        });

        setGameLoop(TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build());

    }

    private void cleanupSprites() {
        spriteManager.cleanUpSprites();
    }


    private void checkCollisions() {
        AbstractBlock stopBlock = null;
        for(Sprite sprite : spriteManager.getCollisionsToCheck()) {
            for(Sprite other : spriteManager.getAllSprites()) {
                if(!other.equals(sprite)) {
                    AbstractBlock block = (AbstractBlock) sprite;
                    AbstractBlock otherBlock = (AbstractBlock) other;
                    if(!block.isStopped()) {
                        if(block.collide(otherBlock)) {
                            stopBlock = block;
                            break;
                        }
                    }
                }
            }
            if(stopBlock != null) {
                break;
            }
        }
        if(stopBlock != null) {
            stopBlock.stop();
        }
    }

    int updates = 0;
    private void updateSprites() {
        for(Sprite sprite : spriteManager.getAllSprites()) {
            handleUpdate(sprite);
        }
        if(model.getVerticalSpeed() == updates){
            updates = 0;
        } else {
            updates++;
        }
    }


    private void handleUpdate(Sprite sprite) {
        if(sprite instanceof AbstractBlock) {
            handleKeyEventsAndCheckWall((AbstractBlock) sprite);
            if(model.getVerticalSpeed() == updates) {
                sprite.update();
            }
        }
    }

    private void handleKeyEventsAndCheckWall(AbstractBlock block) {
        if(!block.isStopped()) {
            checkBlockAgainstFloor(block);
            if(warpBlock) {
                updates = model.getVerticalSpeed();
            }
            if(moveBlock != null) {
                boolean hitsOtherBlocks = false;
                if(moveBlock.equals(KeyCode.RIGHT)) {
                    for(Sprite sprite : spriteManager.getAllSprites()) {
                        if(sprite instanceof AbstractBlock) {
                            AbstractBlock other = (AbstractBlock)sprite;
                            for(Double row : block.getRightY()) {
                                if(other.hasCoordinates(block.getRightX()+1, row)) {
                                    hitsOtherBlocks = true;
                                }
                            }
                        }
                    }
                } else if(moveBlock.equals(KeyCode.LEFT)) {
                    for(Sprite sprite : spriteManager.getAllSprites()) {
                        AbstractBlock other = (AbstractBlock)sprite;
                        for(Double row : block.getLeftY()) {
                            if(other.hasCoordinates(block.getLeftX()- 1, row)) {
                                hitsOtherBlocks = true;
                            }
                        }
                    }
                }
                if(!hitsOtherBlocks) {
                    block.move(moveBlock, blockView.getTranslateX());
                }
                moveBlock = null;
            }
            if(rotateBlock) {
                block.rotate();
                checkBlockAgainstWall(block);
                rotateBlock = false;
            }
        }
    }

    private void checkBlockAgainstWall(AbstractBlock block) {
        if(block.getRightX() > blockView.getTranslateX()+blockView.getWidth()) {
            block.node.setTranslateX((blockView.getTranslateX()+blockView.getWidth()) - (block.getRightX()-block.getLeftX()));
        }
    }

    private void checkBlockAgainstFloor(AbstractBlock block) {
        if(block.getLowestY() >= blockView.getTranslateY() + blockView.getHeight()) {
            block.stop();
        }
    }

    private int getFramesPerSecond() {
        return model.getFramesPerSecond();
    }

    private void setGameLoop(Timeline gameLoop) {
        this.gameLoop = gameLoop;
    }

    @Override
    public void setSceneNodes(Group sceneNodes) {
        this.nodes = sceneNodes;
    }

    @Override
    public Group getSceneNodes() {
        return nodes;
    }

    @Override
    public void onBlockStopped() {
        checkForLines();
        checkGameOver();
        startNewPiece();
    }

    private void checkGameOver() {
        for(Sprite sprite : spriteManager.getAllSprites()) {
            if(sprite instanceof AbstractBlock) {
                AbstractBlock block = (AbstractBlock) sprite;
                if(block.getHighestY() < blockView.getTranslateY()) {
                    model.setGameOver(true);
                }
            }
        }
        if(model.isGameOver()) {
            for(Sprite sprite : spriteManager.getAllSprites()) {
                if(sprite instanceof AbstractBlock) {
                    ((AbstractBlock)sprite).prepareToRemove();
                    ((AbstractBlock)sprite).implode();
                }
            }
            spriteManager.addSpritesToBeRemoved(spriteManager.getAllSprites());
            spriteManager.cleanUpSprites();
        }
    }

    private void checkForLines() {

        double row = blockView.getTranslateY();
        Collection<AbstractBlock> lineBlocks = new HashSet<AbstractBlock>();
        final Collection<Double> lines = new ArrayList<Double>();

        //Check each row for a line that completes it
        while(row < blockView.getTranslateY() + blockView.getHeight()) {
            if(doesRowHaveALine(row, lineBlocks)) {
                System.out.println("Found a line at row " + (row-blockView.getTranslateY())/Globals.BLOCK_WIDTH);
                for(AbstractBlock lineBlock : lineBlocks) {
                    lineBlock.updateLineRow(row);
                }
                lines.add(row);

            }
            lineBlocks.clear();
            row = row + Globals.BLOCK_WIDTH;
        }
        for(Sprite sprite : spriteManager.getAllSprites()) {
            if(sprite instanceof AbstractBlock) {
                ((AbstractBlock)sprite).prepareToImplode();
            }
        }
        Duration flashTime = Duration.millis(1000/(model.getFramesPerSecond()/10));
        KeyFrame flashFrame = new KeyFrame(flashTime, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(Sprite sprite : spriteManager.getAllSprites()) {
                    if(sprite instanceof AbstractBlock) {
                        ((AbstractBlock)sprite).flashRemoves();
                    }
                }
            }
        });
        Timeline timeline = TimelineBuilder.create().keyFrames(flashFrame).cycleCount(4).build();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(Sprite sprite : spriteManager.getAllSprites()) {
                    sprite.implode();
                    if(sprite.isDead) {
                        spriteManager.addSpritesToBeRemoved(sprite);
                    }
                }
                for(Double row : lines) {
                    for(Sprite sprite : spriteManager.getAllSprites()) {
                        if(sprite instanceof AbstractBlock) {
                            AbstractBlock block = (AbstractBlock)sprite;
                            if(block.isStopped() && (block.getLowestY() <= row)) {
                                block.setShiftDown(true);
                            }
                        }
                    }
                }
                Long score = 0l;
                Long bonus = (long)Math.pow(lines.size(), 2);
                if(bonus > 1) {
                    bonus = bonus * 15l;
                } else {
                    bonus =0l;
                }
                score = lines.size()*100l + bonus;
                for(Sprite sprite : spriteManager.getAllSprites()) {
                    sprite.implode();
                }
                model.setScore(model.getScore()+score + bonus);
            }
        });
        timeline.play();
    }

    private boolean doesRowHaveALine(Double row, Collection<AbstractBlock> lineBlocks) {
        double column = blockView.getTranslateX();
        while(column < (blockView.getTranslateX() + blockView.getWidth())) {
            boolean colFilled = false;
            for(Sprite sprite : spriteManager.getAllSprites()) {
                if(sprite instanceof AbstractBlock) {
                    AbstractBlock block = (AbstractBlock) sprite;
                    if(block.hasCoordinates(column, row)) {
                        colFilled = true;
                        lineBlocks.add(block);
                    }
                }
            }
            if(!colFilled) {
                return false;
            }
            column = column + Globals.BLOCK_WIDTH;
        }
        return true;
    }
}
