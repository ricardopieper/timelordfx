package ui;

import java.util.Optional;
import java.util.function.Consumer;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.ExceptionDialog;

public class Dialog {

    public static void create(String title, String masthead, String texto, 
            Consumer<ButtonType> listener) {
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
        dlg.initModality(Modality.WINDOW_MODAL);
        //dlg.initOwner(getDialogStage());
        dlg.setTitle(title);
        dlg.getDialogPane().setContentText(texto);
        dlg.getDialogPane().setHeaderText(masthead);
        dlg.initStyle(StageStyle.UTILITY);
        dlg.show();
        dlg.resultProperty().addListener((ObservableValue<? extends ButtonType> 
                observable, ButtonType oldValue, ButtonType newValue) -> 
                listener.accept(newValue));
    }

    public static void createException(String desc, Exception ex) {
        ExceptionDialog dlg = new ExceptionDialog(ex);
        dlg.setHeaderText(desc);
        dlg.showAndWait();
    }

    public static void createAlert(String title, String masthead, String texto, 
            Alert.AlertType alertType) {
        Alert dlg = new Alert(alertType);
        dlg.initModality(Modality.WINDOW_MODAL);
        //dlg.initOwner(getDialogStage());
        dlg.setTitle(title);
        dlg.getDialogPane().setContentText(texto);
        dlg.getDialogPane().setHeaderText(masthead);
        dlg.initStyle(StageStyle.UTILITY);
        dlg.show();
    }

    public static void createYesNo(String title, String masthead, String texto,
            Consumer<ButtonType> listener) {
        Alert dlg = new Alert(Alert.AlertType.INFORMATION);
        dlg.initModality(Modality.WINDOW_MODAL);
        //dlg.initOwner(getDialogStage());
        dlg.setTitle(title);
        dlg.getDialogPane().setContentText(texto);
        dlg.getDialogPane().setHeaderText(masthead);
        dlg.initStyle(StageStyle.UTILITY);
        dlg.getButtonTypes().clear();

        dlg.getButtonTypes().add(ButtonType.YES);
        dlg.getButtonTypes().add(ButtonType.NO);

        dlg.resultProperty().addListener((ObservableValue<? extends ButtonType> 
                observable, ButtonType oldValue, ButtonType newValue) 
                -> listener.accept(newValue));

        dlg.show();
    }

    public static void createAlert(String title, String masthead, String texto) 
    {
        createAlert(title, masthead, texto, Alert.AlertType.INFORMATION);
    }

    public static String createInputDialog(String title, String texto) {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setTitle(title);

        dlg.getDialogPane().setHeaderText(texto);
        dlg.initStyle(StageStyle.UTILITY);
        Optional<String> result = dlg.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return "";
        }
    }

    public static Alert createDialog(String title, String texto, 
            Alert.AlertType type) {
        Alert dlg = new Alert(type);
        dlg.initModality(Modality.WINDOW_MODAL);
        //dlg.initOwner(getDialogStage());
        dlg.setTitle(title);
        dlg.getDialogPane().setHeaderText(texto);
        dlg.initStyle(StageStyle.UTILITY);
        return dlg;
    }
}
