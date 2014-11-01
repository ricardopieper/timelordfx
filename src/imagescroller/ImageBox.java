package imagescroller;

import java.io.File;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ImageBox extends VBox {

    public Label label;
    public ImageView imgView;
    public imagescroller.ImageData imgData;
    private int width, height;
    //private final ImageScroller parent;

    public ImageBox() {
    }

    public ImageBox(imagescroller.ImageData imgData, int width, int height) {
        this(width, height);
        setImageData(imgData);
    }

    public ImageBox(int width, int height) {
        getStyleClass().add("image-grid-cell"); //$NON-NLS-1$
        this.width = width;
        this.height = height;

        this.setWidth(width);
        this.setHeight(height);
    }

    public void setImageData(imagescroller.ImageData imgData) {
        this.imgData = imgData;
    }

    public void renderNode() {

        String fileUri = new File(imgData.getPath()).toURI().toString();

        javafx.scene.image.Image img = new javafx.scene.image.Image(fileUri);

        imgView = new ImageView(img);
        imgView.setFitHeight(height);
        imgView.setFitWidth(width);
        imgView.fitWidthProperty().bind(widthProperty());
        
        
        label = new Label(imgData.getName());
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(
                imgView,
                label
        );

        this.setOnMouseClicked(e -> {

            if (onClickEvent != null) {
                
                onClickEvent.accept(this);

            }

        });

        
    }

    private Consumer<ImageBox> onClickEvent;

    public void setOnClick(Consumer<ImageBox> func) {
        this.onClickEvent = func;
    }

    public void setBoxCss(String color) {
        this.setStyle(color);
    }

    public void setLabelCss(String color) {
        this.label.setStyle(color);
    }

}
