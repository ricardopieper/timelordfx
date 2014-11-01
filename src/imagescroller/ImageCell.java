package imagescroller;

import model.ArquivoCliente;
import org.controlsfx.control.GridCell;

public class ImageCell<T extends IArquivo> extends GridCell<T> {

    public ImageBox imageBox;

    public ImageCell() {

        getStyleClass().add("image-grid-cell");
        imageBox = new ImageBox(170, 170);

    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        TaggedImage<T> g
                = new TaggedImage<>(item.getCaminho(), item.getNome(), item);

        imageBox.setImageData(g);
        imageBox.renderNode();

        //this.setWidth(imgBox.getWidth());
        // this.setHeight(imgBox.getHeight());
        setGraphic(imageBox);

    }

}
