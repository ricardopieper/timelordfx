package ui.items;

import jidefx.scene.control.field.MaskTextField;

public class MaskedPropItem extends PropItem {

    private final utils.formatting.IMask formatter;
    private final MaskTextField textField;

    public MaskedPropItem(String key, String category, String name, String defaultValue, utils.formatting.IMask mask) {
        super(key, category, name, defaultValue);
        this.formatter = mask;

        this.textField = new MaskTextField();
        this.textField.setAutoAdvance(true);
        this.textField.setText(mask.getEmpty());
        setValue((String) super.getValue());
        
    }

    @Override
    public Object getValue() {
        return formatter.sanitize((String)super.getValue());
    }

    @Override
    public void setValue(Object value) {
       // if (value instanceof String) {
       //     textField.setText();
       // }
       // else 
        //textField.setText(formatter.putMask((String)value));
        
        if (value == null || !(value instanceof String)){
            super.setValue(formatter.getEmpty());
        }
        
        super.setValue(formatter.putMask((String)value));
          
    }

    public utils.formatting.IMask getFormat() {
        return this.formatter;
    }

    @Override
    public MaskTextField getNode() {
        return textField;
    }
}
