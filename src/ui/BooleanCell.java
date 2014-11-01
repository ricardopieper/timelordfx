package ui;

import javafx.scene.control.TableCell;

public class BooleanCell<S> extends TableCell<S, Object> {

    private final String trueText;
    private final String falseText;
    
    public BooleanCell(String trueStr, String falseStr){
        this.trueText = trueStr;
        this.falseText = falseStr;
    }
    
    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
           if (item instanceof Boolean) {
                
                String text = ((Boolean)item)? trueText:falseText;
                
                setText(text);
                
                setGraphic(null);
                
            } 
           
           else{
               setText(item.toString());
               setGraphic(null);
           }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}
