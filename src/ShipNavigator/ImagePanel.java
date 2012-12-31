package ShipNavigator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/27/12
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
        try {
            image = ImageIO.read(new File("./Resources/ship.png"));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }

}