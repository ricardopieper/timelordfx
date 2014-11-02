package ui;


import java.util.function.BooleanSupplier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

public class CustomValidators {

    public static <T> Validator<T> notEmpty(final String message, 
            BooleanSupplier boolPred) {
        return (c, value) -> {
            
            boolean cond = value instanceof String
                    ? value.toString().trim().isEmpty()
                    : value == null;

            return ValidationResult.fromMessageIf(c,
                    message,
                    Severity.ERROR,
                    boolPred.getAsBoolean() ? cond : false);

        };
    }

   
}
