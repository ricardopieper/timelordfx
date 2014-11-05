package views.projeto.setores;

import dao.ProjetosDAO;
import dao.SetoresDAO;
import dao.TarefasDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.ArquivoProjeto;
import model.Projeto;
import model.Setor;
import model.Tarefa;
import ui.BaseController;

/**
 * FXML Controller class
 *
 * @author Ricardo
 */
public class SetoresController extends BaseController {

    @FXML
    private TableView<SetorSelecionado> gridSetores;

    @FXML
    private TableColumn<SetorSelecionado, String> colNomeSetor;

    @FXML
    private TableColumn<SetorSelecionado, Boolean> colCheckbox;

    @FXML
    private CheckBox ckbSelectAll;

    class SetorSelecionado {

        public BooleanProperty selecionado = new SimpleBooleanProperty(false);
        public ObjectProperty<Setor> setor = new SimpleObjectProperty<>();

        public SetorSelecionado(Setor s) {
            setor.set(s);
        }

    }

    ObservableList<SetorSelecionado> setores = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SetoresDAO setoresDao = new SetoresDAO();

        setores.addAll(
                setoresDao.GetAtivos()
                .stream()
                .map(x -> new SetorSelecionado(x))
                .collect(Collectors.toList())
        );

        gridSetores.setItems(setores);

        colNomeSetor
                .setCellValueFactory(
                        (x) -> x.getValue().setor.get().propNome);

        colCheckbox.setCellValueFactory((x) -> x.getValue().selecionado);

        colCheckbox.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckbox));

        ckbSelectAll.setOnAction(x -> {

            if (ckbSelectAll.isSelected()) {

                setores.forEach(y -> y.selecionado.set(true));

            } else {
                setores.forEach(y -> y.selecionado.set(false));
            }

        });

    }

    class CheckboxCell extends TableCell<SetorSelecionado, Boolean> {

        private final CheckBox checkBox;

        public CheckboxCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (isEditing()) {
                    commitEdit(newValue == null ? false : newValue);
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            checkBox.setDisable(false);
            checkBox.requestFocus();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }

        @Override
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }

        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                checkBox.setSelected(item);
            }
        }
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto prj) {
        this.projeto = projetoDao.GetById(prj.getId());
    }
    private Projeto projeto;

    TarefasDAO tarefasDao = new TarefasDAO();
    ProjetosDAO projetoDao = new ProjetosDAO();

    @FXML
    void salvar(ActionEvent event) {
        //Cria a tarefa principal do projeto

        Tarefa tp = new Tarefa();
        tp.setNome(projeto.getNome());
        tp.setDataHoraInicio(projeto.getDataPrevInicio().atStartOfDay());
        tp.setDataHoraFim(projeto.getDataPrevFim().atStartOfDay());
        tp.setDescricao(projeto.getDescricao());
        tp.setStatus(1);
        tp.setProjeto(projeto);

        tarefasDao.Insert(tp);

        projeto.setTarefaPrincipal(tp);
        
        projetoDao.Update(projeto);

        setores.forEach(x -> {

            if (x.selecionado.get()) {

                Setor s = x.setor.get();

                Tarefa t = new Tarefa();
                t.setSetor(s);
                t.setNome(s.getNome());
                t.setDataHoraInicio(projeto.getDataPrevInicio().atStartOfDay());
                t.setDataHoraFim(projeto.getDataPrevFim().atStartOfDay());
                t.setDescricao(s.getNome());
                t.setTarefaPai(tp);
                t.setStatus(1);
                t.setProjeto(projeto);
                tarefasDao.Insert(t);
            }

        });

    }

    @FXML
    void cancelar(ActionEvent event) {
        this.close();
    }

}
