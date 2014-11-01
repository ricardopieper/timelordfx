/*package imagescroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class ImageScroller {

    private final List<Image> images;
    private ImageBox selectedImage;
    private final ScrollPane parentScroll;

    private List<ImageBox> currentImages;

    private HBox hbox;

    private int imageWidth;
    private int imageHeight;

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public ImageScroller(ScrollPane sp) {
        this.images = new ArrayList<>();
        this.parentScroll = sp;
        this.imageWidth = 100;
        this.imageHeight = 100;
    }

    public ImageScroller(ScrollPane sp, int imgWidth, int imgHeight) {
        this.images = new ArrayList<>();
        this.imageHeight = imgHeight;
        this.imageWidth = imgHeight;
        this.parentScroll = sp;
    }

    public ImageScroller addImage(Image i) {
        this.images.add(i);
        return this;
    }

    public List<Image> getAllImages() {
        return images;
    }

    public ImageScroller removeSelected() {
        if (selectedImage != null) {

            this.images.remove(selectedImage.imgData);
            hbox.getChildren().remove(selectedImage.getRenderedNode());
            selectedImage = null;

        }

        return this;
    }

    public Image getSelected() {
        if (selectedImage != null) {
            return selectedImage.imgData;
        } else {
            return null;
        }
    }

    public ImageScroller render() {

        hbox = new HBox(10);
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(10));

        this.parentScroll.setContent(hbox);

        List<ImageBox> vboxes = this.images.stream().map(x -> generateImageBox(x)).collect(Collectors.toList());

        currentImages = vboxes;

        hbox.getChildren().addAll(currentImages.stream().map(x -> x.getRenderedNode()).collect(Collectors.toList()));

        return this;
    }

    private ImageBox generateImageBox(Image m) {

        ImageBox b = new ImageBox(m, this);
        b.setOnClick(() -> {

            if (selectedImage != null) {
                selectedImage.setBoxCss("-fx-background-color: inherit");
                selectedImage.setLabelCss("-fx-text-fill: black");
            }
            b.setBoxCss("-fx-background-color: #2F7590");
            b.setLabelCss("-fx-text-fill: white");

            selectedImage = b;

        });
        return b;
    }

}
*/