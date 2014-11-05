package views.projeto.list;

import dao.ProjetosDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Projeto;
import ui.BaseController;
import ui.DAOController;
import views.projeto.tarefas.ArvoreTarefasController;

public class ListaProjetos extends DAOController<Projeto, ProjetosDAO> {

    @FXML
    private TableColumn<Projeto, String> colNome;

    @FXML
    private TableView<Projeto> gridProjetos;

    @FXML
    private TableColumn<Projeto, Number> colId;

    public ListaProjetos() {
        super(new ProjetosDAO());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        objetos.setAll(daoObject.GetAll());
        gridProjetos.setItems(objetos);

        colNome.setCellValueFactory(x -> x.getValue().propNome);
        colId.setCellValueFactory(x -> x.getValue().propId);
        gridProjetos.getSelectionModel().select(0);
    }

    @FXML
    void openTree() {

        ArvoreTarefasController arvore
                = ArvoreTarefasController.loadView("/views/projeto/tarefas/ArvoreTarefas.fxml");

        arvore.setCadastroLayout("Tarefas", "Lista de Tarefas", null);

        arvore.setProjeto(gridProjetos.getSelectionModel().getSelectedItem());

        arvore.openAndWait("Listagem de Tarefas");

        
        
        
    }

}
