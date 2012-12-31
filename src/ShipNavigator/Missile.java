package ShipNavigator;

import AtomSmasher.Atom;
import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/20/12
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Missile extends Atom {
    public Missile(Color fill) {
        super(5, fill, false);
    }

    public Missile(int radius, Color fill) {
        super(radius, fill, true);
    }
}
