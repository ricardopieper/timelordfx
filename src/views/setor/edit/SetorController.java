package views.setor.edit;

import dao.SetoresDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import model.Setor;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import ui.DAOController;
import ui.Dialog;
import ui.items.PropItem;

public class SetorController extends DAOController<Setor, SetoresDAO> {

    private final ValidationSupport validationSupport = new ValidationSupport();
    private Setor set;

    public SetorController() {
        super(new SetoresDAO());

        String category = "Identificação do Setor:";

        addField(new PropItem("nome", category, "*Nome:", "")
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo Nome é obrigatório.")));

        addField(new PropItem("descricao", category, "Descrição:", ""));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        form.getChildren().add(propertySheet);
        propertySheet.setMode(PropertySheet.Mode.NAME);

        fitToParent(propertySheet);

    }

    public void bind(Setor s) {
        if (s != null) {
            set = s;
            set("nome", s.getNome());
            set("descricao", s.getDescricao());

        }
    }

    public void updateState() {
        if (set == null) {
            set = new Setor();
        }

        set.setNome(get("nome"));
        set.setDescricao(get("descricao"));

    }

    @FXML
    public void salvar(ActionEvent event) {

        if (validate(validationSupport)) {

            try {
                updateState();

                if (set.getId() == 0) {
                    set.setStatus(true);
                    daoObject.Insert(set);
                } else {
                    daoObject.Update(set);
                }

                Dialog.createAlert("Salvando Setor", "Salvando Setor", "O setor foi salvo com sucesso.");

                this.close();

            } catch (Exception ex) {

                Dialog.createException("Ocorreu um erro desconhecido ao salvar o setor!", ex);

                Dialog.createYesNo("Salvando Setor", "Nova Tentativa",
                        "Deseja continuar a edição do setor e tentar novamente?",
                        x -> {
                            if (x == ButtonType.NO) {

                                this.close();
                            }

                        });

            }

        }

    }

    @FXML
    public AnchorPane form;

    @FXML
    void cancelar(ActionEvent event) {

        Dialog.createYesNo("Cancelando", "Cancelamento",
                "Deseja cancelar a edição do setor?",
                x -> {
                    if (x == ButtonType.YES) {

                        this.close();
                    }

                });

    }

}
