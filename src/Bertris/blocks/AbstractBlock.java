package Bertris.blocks;

import Bertris.Globals;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import library.sprites.BlockType;
import library.sprites.Sprite;

import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/22/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBlock extends Sprite {

    protected final Group right = new Group();
    protected final Group down = new Group();
    protected final Group up = new Group();
    protected final Group left = new Group();
    protected Group visibleGroup = null;
    protected boolean stopped = false;
    protected List<IBlockListener> listenerList = new ArrayList<>();
    int number = -99999;
    Set<Double> lineRows = new HashSet<Double>();
    private ArrayList<Node> removes = new ArrayList<Node>();
    protected Color color = Color.BLACK;
    private int shiftDown = 0;

    public AbstractBlock(BlockType type, int number) {
        super(type);
        this.number  = number;
    }

    @Override
    public void update() {
        if(!stopped){
            node.setTranslateY(node.getTranslateY() + Globals.BLOCK_WIDTH);
        }
    }

    public void addListener(IBlockListener blockListener) {
        listenerList.add(blockListener);
    }

    public void removeListener(IBlockListener blockListener) {
        listenerList.remove(blockListener);
    }

    public void stop() {
        stopped = true;
        fireOnBlockStopped();
    }

    protected void fireOnBlockStopped() {
        for(IBlockListener listener : listenerList) {
            listener.onBlockStopped();
        }
    }

    public void setTranslateX(double translateX) {
        node.setTranslateX(translateX);
    }

    public void setTranslateY(double translateY) {
        node.setTranslateY(translateY);
    }

    @Override
    public void implode() {
        for(Node node : removes){
            node.setVisible(false);
            visibleGroup.getChildren().remove(node);
        }
        for(Node node : visibleGroup.getChildren()){
            boolean shiftNodeDown = false;
            for(Node remove : removes) {
                if(node.getLayoutY() < remove.getLayoutY()) {
                    shiftNodeDown = true;
                }
            }
            if(shiftNodeDown){
                node.setLayoutY(node.getLayoutY()+Globals.BLOCK_WIDTH);
            }
        }
        removes.clear();
        if(visibleGroup.getChildren().size() == 0) {
            isDead = true;
        }
        if(shiftDown!=0) {
            if(isStopped() && !isDead) {
            node.setTranslateY(node.getTranslateY() + (Globals.BLOCK_WIDTH*shiftDown));
            shiftDown = 0;
            }
        }
    }

    public void prepareToImplode() {
        if(isStopped() && !lineRows.isEmpty()) {
            for(Double row : lineRows) {
                for(Node blockNode : visibleGroup.getChildren()) {
                    if(this.node.getTranslateY() + blockNode.getLayoutY() == row) {
                        removes.add(blockNode);
                    }
                }
            }
            lineRows.clear();
        }
    }

    protected Orientation orientation = Orientation.DOWN;

    public void move(KeyCode moveBlock, double viewOffset) {
        if(!stopped) {
            if(moveBlock.equals(KeyCode.RIGHT)) {
                if((viewOffset+Globals.GAME_WIDTHS_BLOCKS*Globals.BLOCK_WIDTH)-getRightX() > 0) {
                    setTranslateX(node.getTranslateX()+Globals.BLOCK_WIDTH);
                }
            } else if (moveBlock.equals(KeyCode.LEFT)){
                if(getLeftX()-viewOffset > 0){
                    setTranslateX(node.getTranslateX()-Globals.BLOCK_WIDTH);
                }
            }
        }
    }


    public boolean isStopped() {
        return stopped;
    }

    public boolean hasCoordinates(double col, double row) {
        for(Node blockNode : visibleGroup.getChildren()) {
            Integer startX = (int)(this.node.getTranslateX() + blockNode.getLayoutX());
            Integer startY = (int)(this.node.getTranslateY() + blockNode.getLayoutY());
            Integer endY = (int)(this.node.getTranslateY() + blockNode.getLayoutY() + blockNode.getBoundsInLocal().getHeight());
            Integer endX = (int)(this.node.getTranslateX() + blockNode.getLayoutX() + blockNode.getBoundsInLocal().getWidth());
            if((col < endX && col >= startX) && (row < endY && row >= startY)) {
                return true;
            }
        }
        return false;
    }

    public void flashRemoves() {
        for(Node node : removes) {
            if(node instanceof Rectangle) {
                Rectangle rect = (Rectangle)node;
                if(rect.getFill().equals(color)) {
                    rect.setFill(Color.WHITE);
                } else {
                    rect.setFill(color);
                }
            }
        }
    }

    public void setShiftDown(boolean shiftDown) {
        if(isStopped()) {
            this.shiftDown++;
        }
    }

    public void prepareToRemove() {
        for(Node node : visibleGroup.getChildren()) {
            removes.add(node);
        }
    }


    public enum Orientation{
        DOWN, UP, RIGHT, LEFT
    }

    public void rotate() {
        if(!stopped)  {
            switch(orientation) {
                case DOWN:
                    down.setVisible(true);
                    right.setVisible(false);
                    up.setVisible(false);
                    left.setVisible(false);
                    visibleGroup = down;
                    orientation = Orientation.RIGHT;
                    break;
                case RIGHT:
                    right.setVisible(true);
                    down.setVisible(false);
                    up.setVisible(false);
                    left.setVisible(false);
                    visibleGroup = right;
                    orientation = Orientation.UP;
                    break;
                case UP:
                    right.setVisible(false);
                    down.setVisible(false);
                    up.setVisible(true);
                    left.setVisible(false);
                    visibleGroup = up;
                    orientation = Orientation.LEFT;
                    break;
                case LEFT:
                    right.setVisible(false);
                    down.setVisible(false);
                    up.setVisible(false);
                    left.setVisible(true);
                    visibleGroup = left;
                    orientation = Orientation.DOWN;
                    break;
            }
        }
    }

    public Rectangle createBlock(int layoutY, int layoutX, Color fill) {
        Rectangle piece = new Rectangle();
        piece.setHeight(Globals.BLOCK_WIDTH);
        piece.setWidth(Globals.BLOCK_WIDTH);
        piece.setLayoutX(layoutX);
        piece.setLayoutY(layoutY);
        piece.setFill(fill);
        return piece;
    }

    @Override
    public boolean collide(Sprite other) {
        if(this.equals(other)) return false;
        if(other instanceof AbstractBlock) {
            AbstractBlock otherBlock = (AbstractBlock) other;
            Map<Integer, Integer> otherHighCoords = otherBlock.getUpperCoordinates();
            Map<Integer, Integer> otherLowCoords = otherBlock.getLowerCoordinates();
            Map<Integer, Integer> coords = getLowerCoordinates();
            Map<Integer, Integer> highCoords = getUpperCoordinates();
            for(Integer xCoord : coords.keySet()) {
                Integer otherHighY = otherHighCoords.get(xCoord);
                Integer otherLowY = otherLowCoords.get(xCoord);
                if(otherHighY != null && otherLowY != null && (coords.get(xCoord) >= otherHighY) && (highCoords.get(xCoord) < otherLowY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<Integer, Integer> getUpperCoordinates() {
        Map<Integer, Integer> coordinates = new HashMap<Integer, Integer>();
        for(Node node : visibleGroup.getChildren()) {
            Integer startX = (int)(this.node.getTranslateX() + node.getLayoutX());
            Integer startY = (int)(this.node.getTranslateY() + node.getLayoutY());
            Integer numBlocksWide =  (int) (node.getBoundsInLocal().getMaxX()/Globals.BLOCK_WIDTH);
            for(int i=0; i<numBlocksWide; i++) {
                Integer xCoord = startX+(Globals.BLOCK_WIDTH * i);
                Integer yCoord = coordinates.get(xCoord);
                if(yCoord==null) {
                    coordinates.put(xCoord, startY);
                } else if(startY < yCoord) {
                    coordinates.put(xCoord, startY);
                }
            }
        }
        return coordinates;
    }

    public Map<Integer, Integer> getLowerCoordinates() {
        Map<Integer, Integer> coordinates = new HashMap<Integer, Integer>();
        for(Node node : visibleGroup.getChildren()) {
            Integer startX = (int)(this.node.getTranslateX() + node.getLayoutX());
            Integer startY = (int)(this.node.getTranslateY() + node.getLayoutY() + node.getBoundsInLocal().getMaxY());
            Integer numBlocksWide =  (int) (node.getBoundsInLocal().getMaxX()/Globals.BLOCK_WIDTH);
            for(int i=0; i<numBlocksWide; i++) {
                Integer xCoord = startX+(Globals.BLOCK_WIDTH * i);
                Integer yCoord = coordinates.get(xCoord);
                if(yCoord==null) {
                    coordinates.put(xCoord, startY);
                } else if(startY > yCoord) {
                    coordinates.put(xCoord, startY);
                }
            }
        }
        return coordinates;
    }

    public interface IBlockListener {
        public void onBlockStopped();
    }

    public void updateLineRow(Double row) {
        lineRows.add(row);
    }

    public double getLowestY() {
        double lowestY = 0;
        for(Node node : visibleGroup.getChildren()) {
            double lowest =  this.node.getTranslateY() + node.getLayoutY();
            if(lowest > lowestY) {
                lowestY = lowest;
            }
        }
        return lowestY+Globals.BLOCK_WIDTH;
    }

    public double getRightX() {
        double rightX = 0;
        for(Node node : visibleGroup.getChildren()) {
            double right = (this.node.getTranslateX() + node.getLayoutX())+Globals.BLOCK_WIDTH;
            if( right > rightX) {
                rightX = right;
            }
        }
        return rightX;
    }

    public double getLeftX() {
        double leftX = Double.MAX_VALUE;
        for(Node node : visibleGroup.getChildren()) {
            double left = this.node.getTranslateX() + node.getTranslateX();
            if(left < leftX) {
                leftX = left;
            }
        }
        return leftX;
    }

    public double getHighestY() {
        double highestY = Double.MAX_VALUE;
        for(Node node : visibleGroup.getChildren()) {
            double highest = this.node.getTranslateY() + node.getLayoutY();
            if(highest < highestY) {
                highestY = highest;
            }
        }
        return highestY;
    }

    public Collection<Double> getRightY() {
        Double rightX = getRightX();
        //Adjust for the offset
        rightX -= Globals.BLOCK_WIDTH;
        Collection<Double> rightY = new ArrayList<Double>();
        for(Node node : visibleGroup.getChildren()) {
            if((this.node.getTranslateX() + node.getLayoutX()) == rightX.doubleValue()) {
                rightY.add(this.node.getTranslateY() + node.getLayoutY());
            }
        }
        return rightY;
    }

    public Collection<Double> getLeftY() {
        Double leftX = getLeftX();
        Collection<Double> leftY = new ArrayList<Double>();
        for(Node node : visibleGroup.getChildren()) {
            if((this.node.getTranslateX() + node.getLayoutX()) == leftX.doubleValue()) {
                leftY.add(this.node.getTranslateY() + node.getLayoutY());
            }
        }
        return leftY;
    }
}
