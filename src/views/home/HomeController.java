package views.home;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionGroup;
import org.controlsfx.control.action.ActionUtils;
import views.cliente.ListaClientesController;
import views.setor.ListaSetoresController;

/**
 * FXML Controller class
 *
 * @author Ricardo
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Collection<? extends Action> actions = Arrays.asList(
                new ActionGroup("Cadastros",
                        new Action("Cadastro de Clientes", this::openCadCliente),
                        new Action("Cadastro de Setores", this::openCadSetores)),
                new ActionGroup("Projetos", 
                        new Action("Novo Projeto..."))
                );

        MenuBar menubar = ActionUtils.createMenuBar(actions);

        topMenu.getChildren().add(menubar);

        AnchorPane.setBottomAnchor(menubar, 0.0);
        AnchorPane.setTopAnchor(menubar, 0.0);
        AnchorPane.setLeftAnchor(menubar, 0.0);
        AnchorPane.setRightAnchor(menubar, 0.0);

    }
    @FXML
    private AnchorPane topMenu;

    public void openCadCliente(ActionEvent e) {

        ListaClientesController controller
                = ListaClientesController.loadView("/views/cliente/ListaClientes.fxml");

        controller.setCadastroLayout("Listagem de Clientes", "Listagem dos clientes cadastrados", "/ui/imagens/customer.png");
        controller.openAsync("Editar Cliente");

    }

    public void openCadSetores(ActionEvent e) {

        ListaSetoresController controller
                = ListaSetoresController.loadView("/views/setor/ListaSetores.fxml");

        controller.setCadastroLayout("Listagem de Setores","Listagem dos setores cadastrados", "ui/imagens/sector.png");
        controller.openAsync("Lista de Setores");

    }

}
