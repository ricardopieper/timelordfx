package views;

import imagescroller.*;
//import imagescroller.Image;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Ricardo
 */
public class TesteController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button removeSelected;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

      /*  ImageScroller img = new ImageScroller(scrollPane, 150, 150);

        img.addImage(new Image(
                "C:/Temp/img1.jpeg",
                "Cozinha Planejada 1"));

        img.addImage(new Image(
                "C:/Temp/img2.jpeg",
                "Cozinha Planejada 2"));

        img.addImage(new Image(
                "C:/Temp/img3.jpeg",
                "Cozinha Planejada 3"));

        img.addImage(new Image(
                "C:/Temp/img4.jpeg",
                "Cozinha Planejada 4"));

        img.addImage(new TaggedImage<>(
                "C:/Temp/img5.jpeg",
                "Cozinha Planejada 5","iausdhiuashdu"));
        

        img.render();

        removeSelected.setOnMouseClicked(evt -> {
            img.removeSelected().render();
        });
        */
    }

}
