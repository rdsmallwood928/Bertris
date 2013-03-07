package Bertris.blocks;

import Bertris.Globals;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import library.sprites.BlockType;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/11/13
 * Time: 7:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SquareBlock extends AbstractBlock {

    public SquareBlock(int number) {
        super(BlockType.SQUAREBLOCK, number);
        this.color = Color.RED;
        Group tblock = new Group();

        down.getChildren().add(createBlock(0, 0, color));
        down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        down.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        down.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
        tblock.getChildren().add(down);

        right.getChildren().add(createBlock(0, 0, color));
        right.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
        tblock.getChildren().add(right);

        up.getChildren().add(createBlock(0, 0, color));
        up.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        up.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        up.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
        tblock.getChildren().add(up);

        left.getChildren().add(createBlock(0, 0, color));
        left.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        left.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        left.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
        tblock.getChildren().add(left);

        node = tblock;
        rotate();
    }
}
