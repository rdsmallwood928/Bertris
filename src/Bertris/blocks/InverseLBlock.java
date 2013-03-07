package Bertris.blocks;

import Bertris.Globals;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import library.sprites.BlockType;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/14/13
 * Time: 6:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class InverseLBlock extends AbstractBlock {
    public InverseLBlock(int number){
        super(BlockType.INVERSELBLOCK, number);
        this.color = Color.FUCHSIA;
        Group tblock = new Group();

        down.getChildren().add(createBlock(0, 0, color));
        down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        down.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        down.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, 0, color));
        tblock.getChildren().add(down);

        right.getChildren().add(createBlock(0, 0, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
        right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH*2, color));
        tblock.getChildren().add(right);

        up.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        up.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
        up.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, Globals.BLOCK_WIDTH, color));
        up.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, 0, color));
        tblock.getChildren().add(up);

        left.getChildren().add(createBlock(0, 0, color));
        left.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
        left.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH*2, color));
        left.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH*2, color));
        tblock.getChildren().add(left);

        node = tblock;
        rotate();
    }
}
