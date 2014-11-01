package views.setor;

import dao.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jidefx.scene.control.field.*;
import model.Setor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.dialog.ExceptionDialog;
import ui.DAOController;
import ui.Dialog;
import utils.DateUtil;
import utils.formatting.*;
import views.setor.edit.SetorController;

public class ListaSetoresController
        extends DAOController<Setor, SetoresDAO> {

    @FXML
    private TableColumn<Setor, String> colNome;

    @FXML
    private TableColumn<Setor, String> colDesc;

    @FXML
    private TableColumn<Setor, Boolean> colStatus;

    @FXML
    private TableView<Setor> grid;

    @FXML
    private TextField setorNome;

    @FXML
    private ToggleGroup status;

    @FXML
    private Button editarSetor;

    @FXML
    private Button desativarSetor;

    @FXML
    private Button ativarSetor;

    public ListaSetoresController() {
        super(new SetoresDAO());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        update();
        grid.setItems(objetos);

        setBoolean(colStatus, "Ativo", "Inativo");

        setBinding(x -> x.propNome, colNome);
        setBinding(x -> x.propStatus, colStatus);
        setBinding(x -> x.propDescricao, colDesc);

        editarSetor.setDisable(true);
        desativarSetor.setDisable(true);
        ativarSetor.setDisable(true);
    }

    private void update() {

        objetos.setAll(daoObject.GetAtivos());

    }

    @FXML
    void filter(ActionEvent event) {
        objetos.setAll(
                daoObject.GetFiltered(setorNome.getText(),
                        ((RadioButton) status.getSelectedToggle()).getText().equals("Ativo")));

    }

    @FXML
    void clearFilter(ActionEvent event) {
        update();
        setorNome.setText("");
    }

    @FXML
    void insert(ActionEvent event) {

        SetorController controller
                = SetorController
                .loadView("/views/setor/edit/EditSetor.fxml");

        controller.setCadastroLayout("Inserir Setor", "Preencha as informações e clique em salvar", null);
        controller.openAndWait("Inserir Setor");
        controller.getDialogStage().setOnHidden(x-> update());
    }

    @FXML
    void edit(ActionEvent event) {

        if (grid.getSelectionModel().getSelectedItem() != null) {

            SetorController controller
                    = SetorController
                    .loadView("/views/setor/edit/EditSetor.fxml");

            controller.bind(grid.getSelectionModel().getSelectedItem());
            controller.setCadastroLayout("Editar Setor", "Preencha ou modifique as informações", null);
            controller.openAndWait("Editar Setor");
            controller.getDialogStage().setOnHidden(x-> update());
        }
    }

    @FXML
    void gridClick(MouseEvent event) {

        if (event.getButton().equals(MouseButton.PRIMARY)) {

            if (grid.getSelectionModel().getSelectedItem() != null) {

                desativarSetor.setDisable(!grid.getSelectionModel().getSelectedItem().getStatus());
                editarSetor.setDisable(false);

                ativarSetor.setDisable(grid.getSelectionModel().getSelectedItem().getStatus());

            } else {
                editarSetor.setDisable(true);
                desativarSetor.setDisable(true);
                ativarSetor.setDisable(true);
            }

            if (event.getClickCount() == 2) {

                edit(null);
            }
        }
    }

    @FXML
    void ativar() {
        Dialog.create("Confirmar", "Ativar Setor", "Deseja ativar o setor?",
                x -> {
                    if (x.getButtonData() == ButtonData.OK_DONE) {

                        try {
                            daoObject.Ativar(grid.getSelectionModel().getSelectedItem());
                            Dialog.createAlert("Ativação", "Ativando Setor",
                                    "O setor foi ativado com sucesso.",
                                    Alert.AlertType.CONFIRMATION);
                            update();
                        } catch (Exception ex) {
                            ExceptionDialog dlg = new ExceptionDialog(ex);
                            dlg.setHeaderText("Ocorreu um erro ao ativar o setor!");
                            dlg.showAndWait();
                            update();
                        }
                    }

                });
    }

    @FXML
    void delete(ActionEvent event) {
        Dialog.create("Confirmar", "Desativar Setor", "Deseja desativar o Setor?",
                x -> {
                    if (x.getButtonData() == ButtonData.OK_DONE) {
                        try {
                            daoObject.Desativar(grid.getSelectionModel().getSelectedItem());
                            Dialog.createAlert("Desativação", "Desativando setor",
                                    "O setor foi desativado com sucesso.",
                                    Alert.AlertType.CONFIRMATION);
                            update();
                        } catch (Exception ex) {
                            ExceptionDialog dlg = new ExceptionDialog(ex);
                            dlg.setHeaderText("Ocorreu um erro ao desativar o setor!");
                            dlg.showAndWait();
                            update();
                        }
                    }

                });
    } /*
    
     @Override
     public void clearFilter() {
     List<Cliente> cls = new ClienteDAO().GetFiltered(null);
        
     JRBeanCollectionDataSource dsource = new JRBeanCollectionDataSource(cls);
        
        
     Map parametros = new HashMap();
        
     try{
            
     JasperPrint jpr =
     JasperFillManager
     .fillReport(
     "reports/report1.jasper", parametros, dsource);

     JasperViewer.viewReport(jpr);
            
            
     } catch (JRException ex){
            
     System.out.println(ex);
            
     }
        
        
        
     }
     */

}
