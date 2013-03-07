package Bertris.blocks;

import Bertris.Globals;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import library.sprites.BlockType;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/7/13
 * Time: 7:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class LineBlock extends AbstractBlock {

    public LineBlock(int number) {
        super(BlockType.LINEBLOCK, number);
        this.color = Color.GREEN;
        Group tblock = new Group();

        down.getChildren().add(createBlock(0, 0, color));
        down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH*2, color));
        down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH*3, color));
        tblock.getChildren().add(down);

        right.getChildren().add(createBlock(0, 0, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, 0, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH*3, 0, color));
        tblock.getChildren().add(right);

        up.getChildren().add(createBlock(0, 0, color));
        up.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        up.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH*2, color));
        up.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH*3, color));
        tblock.getChildren().add(up);

        left.getChildren().add(createBlock(0, 0, color));
        left.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        left.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, 0, color));
        left.getChildren().add(createBlock(Globals.BLOCK_WIDTH*3, 0, color));
        tblock.getChildren().add(left);

        node = tblock;
        rotate();
    }
//    @Override
//    public double getLowestY() {
//        switch (orientation){
//            case UP:
//            case DOWN:
//                return node.getTranslateY() + Globals.BLOCK_WIDTH*4;
//            case RIGHT:
//            case LEFT:
//                return node.getTranslateY() + Globals.BLOCK_WIDTH;
//        }
//        return node.getTranslateY();
//    }

//    @Override
//    public double getRightX() {
//        switch (orientation){
//            case UP:
//            case DOWN:
//                return node.getTranslateX()+Globals.BLOCK_WIDTH;
//            case RIGHT:
//            case LEFT:
//                return node.getTranslateX()+Globals.BLOCK_WIDTH*4;
//        }
//        return node.getTranslateX();
//    }

//    @Override
//    public double getLeftX() {
//            return node.getTranslateX();
//    }
}
