package ui;

import dao.BaseDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.controlsfx.control.PropertySheet;
import ui.items.PropItem;
import ui.items.TimeLordPropertyFactory;

public abstract class DAOController<E, T extends BaseDAO<?>>
        extends BaseController {

    protected List<PropItem> fields = new ArrayList<>();
    protected HashMap<String, PropItem> keyedFields = new HashMap<>();
    protected PropertySheet propertySheet;// = new PropertySheet();

    public ObservableList<E> objetos
            = FXCollections.observableArrayList();

    public T daoObject;

    public DAOController(T daoObject) {
        this.daoObject = daoObject;
        propertySheet = new PropertySheet();
        propertySheet.setPropertyEditorFactory(TimeLordPropertyFactory.getPropFactory());

    }

    public void addField(PropItem i) {
        addField(i, propertySheet);
    }

    public void addField(PropItem i, PropertySheet p) {
        if (propertySheet == null) {
            propertySheet = new PropertySheet();
        }

        fields.add(i);
        keyedFields.put(i.getKey(), i);
        p.getItems().add(i);
    }

    protected void setBoolean(TableColumn<?, ?> col, String trueStr, String falseStr) {
        col.setCellFactory(cell -> new BooleanCell(trueStr, falseStr));
    }

    public <A, B> void setStringCellFormatter(TableColumn<A, B> col, Function<B, String> func) {
        col.setCellFactory(cell -> new StringFormattedCell<>(func));
    }

    protected <T extends PropertySheet.Item> T field(String nome) {
        return (T) keyedFields.get(nome);
    }

    protected <T> T get(String nome) {
        return (T) field(nome).getValue();
    }

    protected <T extends PropertySheet.Item, S> void set(String nome, S obj) {
        (this.<T>field(nome)).setValue((S) obj);
    }

    protected <S> void setBinding(Function<E, ObservableValue<S>> selector, TableColumn<E, S> column) {

        column.setCellValueFactory(
                cell
                -> selector.apply(
                        cell.getValue())
        );

    }

}
