package views.projeto.escolher;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Projeto;
import ui.BaseController;
import views.projeto.setores.SetoresController;

/**
 * FXML Controller class
 *
 * @author Ricardo
 */
public class EscolherController extends BaseController {

    @FXML
    void fromProject(ActionEvent event) {

    }

    @FXML
    void createNew(ActionEvent event) {
        SetoresController controller
                = SetoresController.loadView("/views/projeto/setores/Setores.fxml");

        controller.setProjeto(getProjeto());
       
        controller.setCadastroLayout("Setores", "Escolha os setores envolvidos", "ui/imagens/project.png");
       
        controller.openAsync("Setores");
        
        this.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private Projeto projeto;

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Projeto getProjeto() {
        return this.projeto;
    }

}
