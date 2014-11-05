package views.projeto;

import dao.ArquivoProjetoDAO;
import dao.ClientesDAO;
import dao.EnderecosDAO;
import dao.ProjetosDAO;
import imagescroller.ImageCell;
import imagescroller.TaggedImage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import jidefx.scene.control.field.*;
import model.ArquivoProjeto;
import model.Cliente;
import model.Endereco;
import model.Projeto;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import ui.DAOController;
import utils.DateUtil;
import utils.Estado;
import utils.Lists;
import utils.formatting.Formats;

/**
 * FXML Controller class
 *
 * @author Ricardo
 */
public class ProjetoController extends DAOController<Projeto, ProjetosDAO> {

    @FXML
    private CheckBox usaEndCliente;

    @FXML
    private ComboBox<Estado> estado;

    @FXML
    private TextField cidade;

    @FXML
    private TextField endereco;

    @FXML
    private TextField numero;

    @FXML
    private TextField bairro;

    @FXML
    private TextField nome;

    @FXML
    private Button btnRemoveFile;

    @FXML
    private MaskTextField cep;

    @FXML
    private ComboBox<Cliente> cliente;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField complemento;

    @FXML
    private Button btnSave;

    @FXML
    private AnchorPane files;

    @FXML
    private Button btnAddFile;

    @FXML
    private TextArea desc;

    @FXML
    public Label noImagesLabel;

    @FXML
    public GridPane gridEndereco;

    @FXML
    public DateField dataInicio;

    @FXML
    public DateField dataFim;

    private final GridView<ArquivoProjeto> grid = new GridView<>();
    private ImageCell selectedCell;
    private final ObservableList<ArquivoProjeto> arquivos = FXCollections.observableArrayList();
    private final List<ArquivoProjeto> markDelete = FXCollections.observableArrayList();
    private final ValidationSupport validationSupport = new ValidationSupport();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        grid.setCellFactory((GridView<ArquivoProjeto> param) -> {
            ImageCell img = new ImageCell(64, 64);

            img.imageBox.setOnClick(null);

            img.imageBox.setOnClick(x -> {
                if (selectedCell != null && selectedCell.imageBox != null) {
                    selectedCell.imageBox.setBoxCss("-fx-background-color: inherit");
                    selectedCell.imageBox.setLabelCss("-fx-text-fill: black");
                }
                x.setBoxCss("-fx-background-color: #2F7590");
                x.setLabelCss("-fx-text-fill: white");

                selectedCell = img;
            });

            return img;
        });

        grid.setItems(arquivos);

        grid.setCellHeight(64);
        grid.setCellWidth(64);
        arquivos.addListener((ListChangeListener.Change<? extends ArquivoProjeto> c) -> {
            while (c.next());
            noImagesLabel.setVisible(c.getTo() == 0);
        });
        validationSupport.registerValidator(nome, Validator.createEmptyValidator("O campo Nome é obrigatório!"));
        validationSupport.registerValidator(cliente, Validator.createEmptyValidator("O campo Cliente é obrigatório!"));
        validationSupport.registerValidator(usaEndCliente, Validator.createEmptyValidator("O campo Usar endereço do Cliente é obrigatório!"));

        setValidation(true);

        usaEndCliente.selectedProperty()
                .addListener(
                        (ObservableValue<? extends Boolean> observable,
                                Boolean oldValue,
                                Boolean newValue) -> {

                            setValidation(!newValue);
                            gridEndereco.setDisable(newValue);

                        });

        files.getChildren().add(grid);

        fitToParent(grid);

        cliente.setItems(FXCollections.observableArrayList(new ClientesDAO().GetAtivos()));
        estado.setItems(FXCollections.observableArrayList(Estado.getAllBrazillianStates()));

    }

    public void setValidation(boolean status) {

        if (status) {

            validationSupport.registerValidator(endereco, Validator.createEmptyValidator("O campo Endereço é obrigatório!"));
            validationSupport.registerValidator(numero, Validator.createEmptyValidator("O campo Número é obrigatório!"));

            validationSupport.registerValidator(bairro, Validator.createEmptyValidator("O campo Bairro é obrigatório!"));
            validationSupport.registerValidator(cep, Validator.createEmptyValidator("O campo CEP é obrigatório!"));

            validationSupport.registerValidator(estado, Validator.createEmptyValidator("O campo Estado é obrigatório!"));
            validationSupport.registerValidator(cidade, Validator.createEmptyValidator("O campo Cidade é obrigatório!"));
        } else {
            validationSupport.registerValidator(endereco, false, (Control c, String newValue) -> ValidationResult.fromErrorIf(c, "", false));
            validationSupport.registerValidator(numero, false, (Control c, String newValue) -> ValidationResult.fromErrorIf(c, "", false));
            validationSupport.registerValidator(bairro, false, (Control c, String newValue) -> ValidationResult.fromErrorIf(c, "", false));
            validationSupport.registerValidator(cep, false, (Control c, String newValue) -> ValidationResult.fromErrorIf(c, "", false));
            validationSupport.registerValidator(estado, false, (Control c, String newValue) -> ValidationResult.fromErrorIf(c, "", false));
            validationSupport.registerValidator(cidade, false, (Control c, String newValue) -> ValidationResult.fromErrorIf(c, "", false));

        }
    }

    @FXML
    void addFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Selecionar Arquivo...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos", "*.*"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(timelord.TimeLord.MainStage);
        if (selectedFiles != null) {

            if (selectedFiles.size() == 1) {
                String result = ui.Dialog.createInputDialog("Arquivo", "Nome do arquivo:");

                ArquivoProjeto i = new ArquivoProjeto();

                if (result == null || result.equals("")) {
                    result = selectedFiles.get(0).getName();
                }

                i.setNome(result);
                i.setCaminho(selectedFiles.get(0).getAbsolutePath());

                arquivos.add(i);
            } else {
                selectedFiles.forEach(x -> {

                    ArquivoProjeto i = new ArquivoProjeto();

                    i.setNome(x.getName());
                    i.setCaminho(x.getAbsolutePath());

                    arquivos.add(i);
                });

            }

        }
    }

    @FXML
    void removeFile(ActionEvent event) {
        if (selectedCell != null) {
            ArquivoProjeto img = ((TaggedImage<ArquivoProjeto>) selectedCell.imageBox.imgData).getTag();

            markDelete.add(img);

            arquivos.remove(img);

        }
    }

    @FXML
    void cancel(ActionEvent event) {
        this.close();
    }
    private final EnderecosDAO enderecoDao = new EnderecosDAO();
    private final ArquivoProjetoDAO arquivosDao = new ArquivoProjetoDAO();

    @FXML
    void save(ActionEvent event) {
        if (validate(validationSupport)) {

            try {
                updateState();

                if (end != null) { //se vai usar o do cliente
                    if (end.getId() == 0) {
                        enderecoDao.Insert(end);
                    } else {
                        enderecoDao.Update(end);
                    }
                }

                if (proj.getId() == 0) {

                    proj.setDataCadastro(LocalDate.now());
                    proj.setStatus(1);

                    daoObject.Insert(proj);
                } else {
                    daoObject.Update(proj);
                }

                arquivos.forEach(x -> {

                    if (x.getId() == 0) {
                        arquivosDao.Insert(x);
                    } else {
                        arquivosDao.Update(x);
                    }
                });

                markDelete.forEach(x -> arquivosDao.Delete(x));

                ui.Dialog.createAlert("Salvando Projeto", "Salvando Projeto", "O Projeto foi salvo com sucesso.");

                this.close();

            } catch (Exception ex) {

                ui.Dialog.createException("Ocorreu um erro desconhecido ao salvar o projeto!", ex);

                ui.Dialog.createYesNo("Salvando Projeto", "Nova Tentativa",
                        "Deseja continuar a edição do projeto e tentar novamente?",
                        x -> {
                            if (x == ButtonType.NO) {

                                this.close();
                            }

                        });

            }

        }
    }

    public Projeto proj;
    public Endereco end;

    public void bind(Projeto p) {
        this.proj = p;

        this.nome.setText(p.getNome());
        this.desc.setText(p.getDescricao());
        this.dataInicio.setValue(DateUtil.dateFromLocalDate(p.getDataPrevInicio()));
        this.dataFim.setValue(DateUtil.dateFromLocalDate(p.getDataPrevFim()));

        if (p.getCliente() != null) {
            this.cliente.getSelectionModel().select(p.getCliente());
            if (Lists.getLast(p.getCliente().getEnderecos()) != null) {

                Endereco e = Lists.getLast(p.getCliente().getEnderecos());

                if (p.getEndereco().equals(p.getEndereco())) {
                    usaEndCliente.setSelected(true);
                    gridEndereco.setDisable(true);
                    setValidation(false);
                } else {
                    usaEndCliente.setSelected(false);
                    gridEndereco.setDisable(false);
                    setValidation(true);
                }

            }
        }

        if (p.getEndereco() != null) {
            Endereco e = p.getEndereco();

            endereco.setText(e.getEndereco());
            numero.setText(e.getNumeroEndereco());
            bairro.setText(e.getBairro());
            cep.setText(Formats.CEP.putMask(e.getCep()));

            estado.getSelectionModel()
                    .select(Estado.getEstadoByUf(e.getUf()));

            cidade.setText(e.getCidade());
            complemento.setText(e.getComplemento());

        }
        arquivos.setAll(p.getArquivos());

    }

    public void updateState() {

        if (proj == null) {
            proj = new Projeto();
        }

        proj.setNome(nome.getText());
        proj.setDescricao(desc.getText());
        proj.setCliente(cliente.getValue());
        proj.setDataPrevInicio(DateUtil.localDateFromDate(dataInicio.getValue()));
        proj.setDataPrevFim(DateUtil.localDateFromDate(dataFim.getValue()));

        if (usaEndCliente.isSelected()) {

            proj.setEndereco(
                    Lists.getLast(cliente.getValue().getEnderecos()));

        } else {
            if (proj.getEndereco() == null) {
                proj.setEndereco(new Endereco());
            }
            end = proj.getEndereco();

            end.setEndereco(endereco.getText());
            end.setNumeroEndereco(numero.getText());
            end.setBairro(bairro.getText());
            end.setCep(Formats.CEP.sanitize(cep.getText()));
            end.setUf(estado.getValue().sigla);
            end.setCidade(cidade.getText());
            end.setComplemento(complemento.getText());

        }

        arquivos.forEach(x -> x.setProjeto(proj));

    }

    public ProjetoController() {
        super(new ProjetosDAO());
    }

    public Projeto getProjeto() {
        return this.proj;
    }

}
