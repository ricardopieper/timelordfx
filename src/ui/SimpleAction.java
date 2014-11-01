
package ui;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import org.controlsfx.control.action.Action;


public class SimpleAction extends Action {

    public SimpleAction(String string, Consumer<ActionEvent> cnsmr) {
        super(string, cnsmr);
    }
    
}
