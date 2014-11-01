package views.cliente;

import dao.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jidefx.scene.control.field.*;
import model.Cliente;
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
import views.cliente.edit.InsertClienteController;
//import views.cliente.edit.InsertClienteController;

public class ListaClientesController
        extends DAOController<Cliente, ClientesDAO> {

    @FXML
    private MaskTextField clienteCpf;

    @FXML
    private DateField campoData;

    @FXML
    private TableColumn<Cliente, String> colCpf;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, LocalDate> colDataCadastro;

    @FXML
    private TableColumn<Cliente, Boolean> colStatus;

    @FXML
    private TableView<Cliente> grid;

    @FXML
    private TextField clienteNome;

    @FXML
    private ToggleGroup status;

    @FXML
    private Button editarCliente;

    @FXML
    private Button desativarCliente;

    @FXML
    private Button ativarCliente;

    public ListaClientesController() {
        super(new ClientesDAO());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        update();
        grid.setItems(objetos);
        setBinding(x -> x.propNome, colNome);

        setStringCellFormatter(colCpf, x -> Formats.CPF.putMask(x));
        setBoolean(colStatus, "Ativo", "Inativo");
        setStringCellFormatter(colDataCadastro, x -> DateUtil.localDateToString(x));

        setBinding(x -> x.propCPF, colCpf);
        setBinding(x -> x.propStatus, colStatus);
        setBinding(x -> x.propDataCadastro, colDataCadastro);

        clienteNome.setText("");
        clienteCpf.setText(Formats.CPF.putMask(""));
        campoData.setValue(null);

        editarCliente.setDisable(true);
        desativarCliente.setDisable(true);
        ativarCliente.setDisable(true);
    }

    private void update() {

        objetos.clear();
        objetos.addAll(daoObject.GetAtivos());

    }

    @FXML
    void filter(ActionEvent event) {
        objetos.clear();
        objetos.addAll(
                daoObject.GetFiltered(clienteNome.getText(),
                        Formats.CPF.sanitize(clienteCpf.getText()).trim(),
                        DateUtil.localDateFromDate(campoData.getValue()),
                        ((RadioButton) status.getSelectedToggle()).getText().equals("Ativo")));

    }

    @FXML
    void clearFilter(ActionEvent event) {
        update();
        clienteNome.setText("");
        clienteCpf.setText(Formats.CPF.putMask(""));
        campoData.setValue(null);
    }

    @FXML
    void insert(ActionEvent event) {

        InsertClienteController controller
                = InsertClienteController
                .loadView("/views/cliente/edit/EditCliente.fxml");

        controller.setCadastroLayout("Inserir Cliente", "Preencha as informações e clique em salvar", null);
        controller.openAndWait("Inserir Cliente");
        controller.getDialogStage().setOnHidden(x -> update());

    }

    @FXML
    void edit(ActionEvent event) {

        if (grid.getSelectionModel().getSelectedItem() != null) {

            InsertClienteController controller
                    = InsertClienteController
                    .loadView("/views/cliente/edit/EditCliente.fxml");

            controller.bind(grid.getSelectionModel().getSelectedItem());
            controller.setCadastroLayout("Editar Cliente", "Preencha ou modifique as informações", null);
            controller.openAndWait("Editar Cliente");
            controller.getDialogStage().setOnHidden(x -> update());
        }
    }

    @FXML
    void gridClick(MouseEvent event) {

        if (event.getButton().equals(MouseButton.PRIMARY)) {

            if (grid.getSelectionModel().getSelectedItem() != null) {

                desativarCliente.setDisable(!grid.getSelectionModel().getSelectedItem().getStatus());
                editarCliente.setDisable(false);

                ativarCliente.setDisable(grid.getSelectionModel().getSelectedItem().getStatus());

            } else {
                editarCliente.setDisable(true);
                desativarCliente.setDisable(true);
                ativarCliente.setDisable(true);
            }

            if (event.getClickCount() == 2) {

                edit(null);
            }
        }
    }

    @FXML
    void ativar() {
        Dialog.create("Confirmar", "Ativar Cliente", "Deseja ativar o cliente?",
                x -> {
                    if (x.getButtonData() == ButtonData.OK_DONE) {

                        try {
                            daoObject.Ativar(grid.getSelectionModel().getSelectedItem());
                            Dialog.createAlert("Ativação", "Ativando cliente",
                                    "O cliente foi ativado com sucesso.",
                                    Alert.AlertType.CONFIRMATION);
                        } catch (Exception ex) {
                            ExceptionDialog dlg = new ExceptionDialog(ex);
                            dlg.setHeaderText("Ocorreu um erro ao ativar o cliente!");
                            dlg.showAndWait();
                        }
                    }

                });
    }

    @FXML
    void delete(ActionEvent event) {
        Dialog.create("Confirmar", "Desativar Cliente", "Deseja desativar o cliente?",
                x -> {
                    if (x.getButtonData() == ButtonData.OK_DONE) {
                        try {
                            daoObject.Desativar(grid.getSelectionModel().getSelectedItem());
                            Dialog.createAlert("Desativação", "Desativando cliente",
                                    "O cliente foi desativado com sucesso.",
                                    Alert.AlertType.CONFIRMATION);
                        } catch (Exception ex) {
                            ExceptionDialog dlg = new ExceptionDialog(ex);
                            dlg.setHeaderText("Ocorreu um erro ao desativar o cliente!");
                            dlg.showAndWait();
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
