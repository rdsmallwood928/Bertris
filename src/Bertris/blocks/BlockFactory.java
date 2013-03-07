package Bertris.blocks;

import library.sprites.BlockType;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/7/13
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockFactory {

    public static AbstractBlock getRandomBlock(int num) {
        Random random = new Random();
        switch (random.nextInt()% BlockType.values().length) {
            case 0:
                return new TBlock(num);
            case 1:
                return new LineBlock(num);
            case 2:
                return new SquareBlock(num);
            case 3:
                return new SquiglyBlock(num);
            case 4:
                return new InverseSquiglyBlock(num);
            case 5:
                return new LBlock(num);
            case 6:
                return new InverseLBlock(num);
        }
        return getRandomBlock(num);
    }
}
