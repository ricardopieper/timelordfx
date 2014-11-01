package ui.items;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import jidefx.scene.control.field.MaskTextField;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.PropertyEditor;
import org.controlsfx.tools.ValueExtractor;

public class TimeLordPropertyFactory {

    static Callback<PropertySheet.Item, PropertyEditor<?>> cachePropFact;

    public static Callback<Item, PropertyEditor<?>> getPropFactory() {

        if (cachePropFact == null) {

            cachePropFact = (Item x) -> {
                if (x instanceof ListChoice) {

                    ListChoice choice = (ListChoice) x;

                    return new AbstractPropertyEditor<Object, ComboBox>(choice, choice.getNode()) {

                        {
                            getEditor().setItems(FXCollections.observableArrayList(choice.getItems()));

                            if (choice.getValSupport() != null && choice.getValidator() != null) {

                                choice.getValSupport().registerValidator(getEditor(), choice.getValidator());

                            }
                        }

                        @Override
                        protected ObservableValue<Object> getObservableValue() {
                            return getEditor().getSelectionModel().selectedItemProperty();
                        }

                        @Override
                        public void setValue(Object t) {
                            // choice.setValue(t); //. //.setSelectedItem(choice.getValue());
                            getEditor().getSelectionModel().select(t);
                        }
                    };
                } else if (x instanceof MaskedPropItem) {

                    MaskedPropItem mask = (MaskedPropItem) x;

                    return new AbstractPropertyEditor<String, MaskTextField>(mask, mask.getNode()) {

                        //private Class<? extends Number> sourceClass = (Class<? extends Number>) mask.getType(); //Double.class;
                        {

                            ((MaskTextField) getEditor()).setInputMask(mask.getFormat().getMask());

                            if (mask.getValSupport() != null && mask.getValidator() != null) {

                                mask.getValSupport().registerValidator(getEditor(), mask.getValidator());

                            }
                        }

                        @Override
                        protected ObservableValue<String> getObservableValue() {
                            return getEditor().textProperty();
                        }

                        @Override
                        public String getValue() {
                            try {
                                if (getEditor().getText().isEmpty()){
                                    return mask.getFormat().getEmpty();
                                }else{
                                   return mask.getFormat().putMask(getEditor().getText()); 
                                }
                                
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        }

                        @Override
                        public void setValue(String value) {
                            if (value != null && value.isEmpty() == false) {
                                getEditor().setText(mask.getFormat().putMask(value));
                            }else{
                                getEditor().setText(mask.getFormat().getEmpty());
                                
                            }
                        }

                    };

                } else if (x instanceof PropItem) {

                    PropertyEditor propEditor = new DefaultPropertyEditorFactory().call(x);

                    PropItem prop = (PropItem) x;
                    
                    prop.setNode(propEditor.getEditor());

                    if (prop.getValSupport() != null && prop.getValidator() != null) {

                        prop.getValSupport().registerValidator((Control) prop.getNode(), prop.getValidator());

                    }

                    return propEditor;
                }

                //Em últimos casos:
                return new DefaultPropertyEditorFactory().call(x);
            };

        }

        return cachePropFact;
    }
}
