package ShipNavigator;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 12/20/12
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassLoaderTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Stage stage1 = new Stage();

        WritableImage image = SwingFXUtils.toFXImage(ImageIO.read(new File("./Resources/ship.png")), new WritableImage(10, 100));
        Pane root = new Pane();
        Ship ship = new Ship();
        root.getChildren().add(ship.node);
        stage1.setScene(new Scene(root, 800, 600));
        stage1.show();
    }

    public static void main(String[] args) {
/*        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                ImagePanel imagePanel = new ImagePanel();
                JPanel panel = new JPanel(new BorderLayout());
                panel.setPreferredSize(new Dimension(600, 600));
                panel.add(imagePanel, BorderLayout.CENTER);
                frame.add(panel);
                frame.setVisible(true);

            }
        });*/
        Application.launch(args);
    }
}
