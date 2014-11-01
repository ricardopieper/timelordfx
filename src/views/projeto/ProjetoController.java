package views.projeto;

import dao.ProjetosDAO;
import imagescroller.ImageCell;
import imagescroller.TaggedImage;
import java.io.File;
import java.net.URL;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import jidefx.scene.control.field.*;
import model.ArquivoCliente;
import model.ArquivoProjeto;
import model.Cliente;
import model.Projeto;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import ui.DAOController;
import utils.Estado;

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

    private final GridView<ArquivoProjeto> grid = new GridView<>();
    private ImageCell selectedCell;
    private final ObservableList<ArquivoProjeto> arquivos = FXCollections.observableArrayList();
    private List<ArquivoCliente> markDelete = FXCollections.observableArrayList();
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
            ImageCell img = new ImageCell();

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

        grid.setCellHeight(200);
        grid.setCellWidth(200);
        arquivos.addListener((ListChangeListener.Change<? extends ArquivoProjeto> c) -> {
            while (c.next());
            noImagesLabel.setVisible(c.getTo() == 0);
        });

        validationSupport.registerValidator(nome, Validator.createEmptyValidator("O campo Nome é obrigatório!"));
        validationSupport.registerValidator(usaEndCliente, Validator.createEmptyValidator("O campo Cliente é obrigatório!"));

        validationSupport.registerValidator(endereco, Validator.createEmptyValidator("O campo Endereço é obrigatório!"));
        validationSupport.registerValidator(numero, Validator.createEmptyValidator("O campo Número é obrigatório!"));

        validationSupport.registerValidator(bairro, Validator.createEmptyValidator("O campo Bairro é obrigatório!"));
        validationSupport.registerValidator(cep, Validator.createEmptyValidator("O campo CEP é obrigatório!"));

        validationSupport.registerValidator(estado, Validator.createEmptyValidator("O campo Estado é obrigatório!"));
        validationSupport.registerValidator(cidade, Validator.createEmptyValidator("O campo Cidade é obrigatório!"));

        setValidation(false);

        usaEndCliente.selectedProperty()
                .addListener(
                        (ObservableValue<? extends Boolean> observable,
                                Boolean oldValue,
                                Boolean newValue) -> {

                            setValidation(newValue);

                        });
    }

    public void setValidation(boolean status) {
        ValidationSupport.setRequired(endereco, status);
        ValidationSupport.setRequired(numero, status);
        ValidationSupport.setRequired(bairro, status);
        ValidationSupport.setRequired(cep, status);
        ValidationSupport.setRequired(estado, status);
        ValidationSupport.setRequired(cidade, status);

    }

    @FXML
    void addFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(timelord.TimeLord.MainStage);
        if (selectedFile != null) {
            String result = ui.Dialog.createInputDialog("Imagem", "Nome da Imagem:");

            ArquivoProjeto i = new ArquivoProjeto();
            i.setNome(result);
            i.setCaminho(selectedFile.getAbsolutePath());

            arquivos.add(i);

        }
    }

    @FXML
    void removeFile(ActionEvent event) {
        if (selectedCell != null) {
            ArquivoCliente img = ((TaggedImage<ArquivoCliente>) selectedCell.imageBox.imgData).getTag();

            markDelete.add(img);

            arquivos.remove(img);

        }
    }

    @FXML
    void cancel(ActionEvent event) {
        this.close();
    }

    @FXML
    void save(ActionEvent event) {

    }

    public void bind(Projeto p) {

    }

    public ProjetoController() {
        super(new ProjetosDAO());
    }

}
