package ui;

import java.util.function.Function;
import javafx.scene.control.TableCell;

public class StringFormattedCell<A, B> extends TableCell<A, B> {

    private final Function<B, String> strFunc;

    public StringFormattedCell(Function<B, String> strFunc) {
        this.strFunc = strFunc;
    }

    @Override
    protected void updateItem(B item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {

            if (strFunc != null) {
                String text = strFunc.apply(item);

                setText(text);

                setGraphic(null);
            } else {
                setText(item.toString());
                setGraphic(null);
            }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}
