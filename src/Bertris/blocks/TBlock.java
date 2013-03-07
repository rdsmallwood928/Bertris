    package Bertris.blocks;

    import Bertris.Globals;
    import javafx.scene.Group;
    import javafx.scene.paint.Color;
    import library.sprites.BlockType;

    /**
     * Created with IntelliJ IDEA.
     * User: bigwood928
     * Date: 1/21/13
     * Time: 5:35 PM
     * To change this template use File | Settings | File Templates.
     */
    public class TBlock extends AbstractBlock {

        public TBlock(int number) {
            super(BlockType.TBLOCK, number);
            this.color = Color.YELLOW;
            Group tblock = new Group();

            down.getChildren().add(createBlock(0, 0, color));
            down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
            down.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH*2, color));
            down.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
            tblock.getChildren().add(down);

            right.getChildren().add(createBlock(0, 0, color));
            right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
            right.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, 0, color));
            right.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
            tblock.getChildren().add(right);

            up.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
            up.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
            up.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH*2, color));
            up.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
            tblock.getChildren().add(up);

            left.getChildren().add(createBlock(0, Globals.BLOCK_WIDTH, color));
            left.getChildren().add(createBlock(Globals.BLOCK_WIDTH, Globals.BLOCK_WIDTH, color));
            left.getChildren().add(createBlock(Globals.BLOCK_WIDTH*2, Globals.BLOCK_WIDTH, color));
            left.getChildren().add(createBlock(Globals.BLOCK_WIDTH, 0, color));
            tblock.getChildren().add(left);

            node = tblock;
            rotate();
        }
/*
        //Return as if the orientation was "one behind"
        public double getLowestY() {
            switch (orientation) {
                case UP:
                case DOWN:
                    return node.getTranslateY() + Globals.BLOCK_WIDTH*3;
                case RIGHT:
                case LEFT:
                    return node.getTranslateY() + Globals.BLOCK_WIDTH*2;
            }
            return Globals.BLOCK_WIDTH*3;
        }*/

//      @Override
//        public double getRightX() {
//            switch (orientation){
//                case UP:
//                case DOWN:
//                    return node.getTranslateX() + Globals.BLOCK_WIDTH*2;
//                case RIGHT:
//                case LEFT:
//                    return node.getTranslateX() + Globals.BLOCK_WIDTH*3;
//            }
//            return Globals.BLOCK_WIDTH*3;
//        }

//        @Override
//        public double getLeftX() {
//            return node.getTranslateX();
//        }
    }
