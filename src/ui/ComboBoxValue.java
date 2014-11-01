
package ui;

import java.util.function.Function;


public class ComboBoxValue<T> {

    T item;
    
    Function<T,String> toStringFunc;
    
    public ComboBoxValue(T item, Function<T,String> func){this.item = item; this.toStringFunc = func;}

    
    @Override
    public String toString(){
       return toStringFunc.apply(item);
    };
}
