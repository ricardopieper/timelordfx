package ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import timelord.TimeLord;

public abstract class BaseController implements Initializable {

    private Stage currentStage;
    private TimeLord lord;
    public Pane mainPane;

    public void close() {
        this.getDialogStage().close();
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

            this.setApplication(TimeLord.ApplicationInstance); //it doesnt make any sense lol
            this.setDialogStage(dialog);
            dialog.centerOnScreen();

            dialog.show();

            double w = 0;
            double h = 0;

            if (mainPane instanceof AnchorPane) {
                w += mainPane.getWidth() + 40;
                h += mainPane.getHeight() + 40;
            }

            if (mainPane instanceof BorderPane) {
                BorderPane b = (BorderPane) mainPane;

                w += b.getWidth() + 40;
                h += ((AnchorPane) b.getTop()).getHeight();

                h += ((AnchorPane) b.getCenter()).getHeight();
                h += 40;
            }

            dialog.setWidth(w);
            dialog.setHeight(h);
            dialog.centerOnScreen();
        }

    }

    public void openAndWait(String title) {

        _openWindow(false, title);
    }

    public void openAsync(String title) {
        _openWindow(false, title);
    }

    public BaseController() {
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

            ((Label) parentPane.lookup("#title")).setText(titulo);
            ((Label) parentPane.lookup("#subtitle")).setText(subtitulo);
            ((ImageView) parentPane.lookup("#image")).setImage(new Image(caminhoimg));

            parentPane.setCenter(this.mainPane);
            this.hasHeader = true;
            this.mainPane = parentPane;

        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

  
    protected void notificacao(String titulo, String texto, Pos posicao) {
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(texto)
                .hideAfter(Duration.seconds(3))
                .position(posicao);

        notificationBuilder.showError();

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

            s.getValidationResult().getErrors().forEach(x -> mensagem.append(x.getText()).append("\n"));

            notificacao("Erro!", "Preencha as informações obrigatórias!\n" + mensagem.toString(), Pos.CENTER);
        } else {

        }

        return !s.isInvalid();

    }
}
