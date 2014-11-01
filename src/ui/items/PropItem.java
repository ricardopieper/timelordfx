package ui.items;

import javafx.scene.Node;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class PropItem implements Item {

    private final String category, name, key;
    private Object value;
    private Node contentNode;

    public PropItem(String key, String category, String name, Object defaultValue) {
        this.key = key;
        this.category = category;
        this.name = name;
        this.value = defaultValue;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public Class<?> getType() {
        if (value == null) {
            return Object.class;

        } else {
            return value.getClass();
        }
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    public Node getNode() {
        return this.contentNode;
    }

    public void setNode(Node n) {
        this.contentNode = n;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public ValidationSupport getValSupport() {
        return valSupport;
    }

    public void setValSupport(ValidationSupport valSupport) {
        this.valSupport = valSupport;
    }
    private Validator validator;
    private ValidationSupport valSupport;

    public PropItem setValidation(ValidationSupport validationSupport, Validator val) {
        this.valSupport = validationSupport;
        this.validator = val;
        return this;
    }

}
