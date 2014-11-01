package ui.items;

import java.util.List;
import javafx.scene.control.ComboBox;
public class ListChoice<T> extends PropItem  {


    private final List<T> items;

    ComboBox<T> comboBoxNode;
    
    public ListChoice(String key, String category, String name, T defaultValue, List<T> ls) {
        super(key,category,name,defaultValue);
     
        this.items = ls;
  
        this.comboBoxNode = new ComboBox<>();
        this.comboBoxNode.getItems().addAll(items);
        this.comboBoxNode.setValue(defaultValue);
        this.setNode(comboBoxNode);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Object getValue() {
        return this.comboBoxNode.getValue();
    }

    @Override
    public void setValue(Object value) {
        this.comboBoxNode.setValue((T) value);
    }
    
    public List<T> getItems(){
        return items;
    }
    
    @Override
    public ComboBox getNode(){
        return comboBoxNode;
    }
}
