package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.validation.ValidationSupport;
import timelord.TimeLord;
import ui.items.PropItem;
import ui.items.TimeLordPropertyFactory;

public abstract class BaseController<E> implements Initializable {

    private Stage currentStage;
    private TimeLord lord;
    public Pane mainPane;
    
    protected List<PropItem> fields = new ArrayList<>();
    protected HashMap<String, PropItem> keyedFields = new HashMap<>();
    protected PropertySheet propertySheet;// = new PropertySheet();

    public void close() {
        this.getDialogStage().close();
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

    private void _openWindow(boolean wait, String title) {

        if (mainPane != null) {

            Stage dialog = new Stage();
            currentStage = dialog;
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(TimeLord.MainStage);

            Scene scene = new Scene(mainPane);

            dialog.setScene(scene);
            double w = mainPane.getWidth();
            double h = mainPane.getHeight();

            dialog.setWidth(w);
            dialog.setHeight(h);

            this.setApplication(TimeLord.ApplicationInstance); //it doesnt make any sense lol
            this.setDialogStage(dialog);
            dialog.centerOnScreen();            
            
            if (wait) {
                dialog.showAndWait();

            } else {
                dialog.show();
                dialog.centerOnScreen();
            }

        }

    }

    public void openAndWait(String title) {

        _openWindow(false, title);
    }

    public void openAsync(String title) {
        _openWindow(false, title);
    }

    public BaseController() {
        propertySheet = new PropertySheet();
        propertySheet.setPropertyEditorFactory(TimeLordPropertyFactory.getPropFactory());
    }

    public void setDialogStage(Stage stage) {
        this.currentStage = stage;
    }

    public Stage getDialogStage() {
        return this.currentStage;
    }

    public void setApplication(TimeLord lord) {
        this.lord = lord;
    }

    public TimeLord getApplication() {
        return this.lord;
    }

    public static <T extends BaseController> T loadView(String fxmlLocation) {

        try {

            FXMLLoader thisView = new FXMLLoader();
            thisView.setLocation(TimeLord.class.getResource(fxmlLocation));

            AnchorPane pane = (AnchorPane) thisView.load();

            T controller = thisView.getController();

            controller.mainPane = pane;

            return controller;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean hasHeader = false;
    
    @FXML
    private Label title;
    private String titleStr;
    
    @FXML
    private Label subtitle;
    private String subtitleStr;
   
    @FXML
    private ImageView image;
    private String imagePath;
    
    public void setCadastroLayout(String titulo, String subtitulo, String caminhoimg) {
        try {
            if (titulo == null) {
                titulo = "Manutenção";
            }
            if (subtitulo == null) {
                subtitulo = "Gerenciamento de entidades";
            }
            if (caminhoimg == null) {
                caminhoimg = "/ui/imagens/form.png";
            }

            FXMLLoader parent = new FXMLLoader();
            parent.setLocation(TimeLord.class.getResource("/views/CadastroLayout.fxml"));

            BorderPane parentPane = (BorderPane) parent.load();

            this.titleStr = titulo;
            this.subtitleStr = subtitulo;
            this.imagePath = caminhoimg;
            
            
          ((Label)parentPane.lookup("#title")).setText(titulo);
          ((Label)parentPane.lookup("#subtitle")).setText(subtitulo);
         ((ImageView)parentPane.lookup("#image")).setImage(new Image(caminhoimg));
            
            
            parentPane.setCenter(this.mainPane);
            this.hasHeader = true;
            this.mainPane = parentPane;

            
            
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    protected <S> void setBinding(Function<E, ObservableValue<S>> selector, TableColumn<E, S> column) {

        column.setCellValueFactory(
                cell
                -> selector.apply(
                        cell.getValue())
        );

    }

    protected void notificacao(String titulo, String texto, Pos posicao) {
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(texto)
                .hideAfter(Duration.seconds(3))
                .position(posicao);

        notificationBuilder.showError();

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

    protected void fitToParent(Node n, double spacing) {

        AnchorPane.setTopAnchor(n, spacing);
        AnchorPane.setBottomAnchor(n, spacing);
        AnchorPane.setLeftAnchor(n, spacing);
        AnchorPane.setRightAnchor(n, spacing);
    }

    protected void fitToParent(Node n) {
        fitToParent(n, 0.0);
    }

    protected boolean validate(ValidationSupport s) {

        if (s.isInvalid()) {

            StringBuilder mensagem = new StringBuilder();

            s.validationResultProperty()
                    .addListener((o, oldValue, newValue) -> newValue.getMessages().forEach(x -> {

                        mensagem.append(x.getText());

                    }));
            notificacao("Erro!", "Preencha as informações obrigatórias!\n" + mensagem.toString(), Pos.CENTER);
        } else {

        }

        return !s.isInvalid();

    }
}
