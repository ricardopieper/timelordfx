package views.cliente.edit;

import dao.ArquivoClienteDAO;
import dao.ClientesDAO;
import dao.EnderecosDAO;
import imagescroller.ImageCell;
import imagescroller.TaggedImage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import model.ArquivoCliente;
import model.Cliente;
import model.Endereco;
import org.controlsfx.control.GridView;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.dialog.ExceptionDialog;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import ui.DAOController;
import ui.Dialog;
import ui.items.ListChoice;
import ui.items.MaskedPropItem;
import ui.items.PropItem;
import ui.items.TimeLordPropertyFactory;
import utils.*;
import utils.formatting.*;

public class InsertClienteController extends DAOController<Cliente, ClientesDAO> {

    private final ValidationSupport validationSupport = new ValidationSupport();
    private ObservableList<ArquivoCliente> arquivos = FXCollections.observableArrayList();

    private List<ArquivoCliente> markDelete = FXCollections.observableArrayList();

    private final GridView<ArquivoCliente> grid = new GridView<>();
    private ImageCell selectedCell;
    private Cliente cli;
    private Endereco end;

    ArquivoClienteDAO arquivosDao = new ArquivoClienteDAO();
    EnderecosDAO enderecoDao = new EnderecosDAO();

    public InsertClienteController() {
        super(new ClientesDAO());

        String category = "1 - Dados do Cliente";

        addField(new PropItem("nome", category, "*Nome:", "")
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo Nome é obrigatório.")));

        addField(new MaskedPropItem("cpf", category, "*CPF", "", Formats.CPF)
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo CPF é obrigatório.")));

        addField(new PropItem("datanasc", category, "Data de Nascimento", LocalDate.of(1980, Month.JANUARY, 1)));

        category = "2 - Endereço";

        addField(new ListChoice("estado", category, "*Estado", new Estado("RS", null), Estado.getAllBrazillianStates()));

        addField(new PropItem("numero", category, "*Número", "")
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo Número é obrigatório.")));

        addField(new PropItem("bairro", category, "*Bairro", "")
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo Bairro é obrigatório.")));

        addField(new MaskedPropItem("cep", category, "*CEP", "", Formats.CEP)
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo CEP é obrigatório.")));

        addField(new PropItem("cidade", category, "*Cidade", "")
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo Cidade é obrigatório.")));

        addField(new PropItem("endereco", category, "*Endereço", "")
                .setValidation(validationSupport,
                        Validator.createEmptyValidator("O campo Endereço é obrigatório.")));

        addField(new PropItem("complemento", category, "Complemento", ""));

        grid.setCellFactory((GridView<ArquivoCliente> param) -> {

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
        arquivos.addListener((ListChangeListener.Change<? extends ArquivoCliente> c) -> {
            while (c.next());

            noImagesLabel.setVisible(c.getTo() == 0);
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        form.getChildren().add(propertySheet);
        propertySheet.setMode(PropertySheet.Mode.NAME);

        fitToParent(propertySheet);

        images.getChildren().add(grid);

        fitToParent(grid);

    }

    public void bind(Cliente c) {
        if (c != null) {
            cli = c;
            field("nome").setValue(cli.getNome());

            //  this.<MaskTextField>set("cpf", cli.getCpf());
            this.<MaskedPropItem, String>set("cpf", c.getCpf());

            field("datanasc").setValue(cli.getDatanasc());

            if (cli.getEnderecos() != null && cli.getEnderecos().size() > 0) {

                end = cli.getEnderecos().get(cli.getEnderecos().size() - 1);

                field("estado").setValue(Estado.getAllBrazillianStates()
                        .stream().filter(x -> x.sigla.equals(end.getUf()))
                        .findFirst().get());

                field("numero").setValue(end.getNumeroEndereco());
                field("bairro").setValue(end.getBairro());
                this.<MaskedPropItem, String>set("cep", end.getCep());

                field("cidade").setValue(end.getCidade());
                field("endereco").setValue(end.getEndereco());
                field("complemento").setValue(end.getComplemento());
                field("cidade").setValue(end.getCidade());

            } else {
                end = new Endereco();
            }

            if (c.getArquivos() != null && c.getArquivos().size() > 0) {

                arquivos.setAll(c.getArquivos());

                noImagesLabel.setVisible(false);

            } else {

                noImagesLabel.setVisible(true);

            }

        }
    }

    public void updateState() {
        if (cli == null) {
            cli = new Cliente();
        }
        if (end == null) {
            end = new Endereco();
        }

        cli.setNome(get("nome"));
        cli.setCpf(get("cpf"));
        cli.setDataCadastro(LocalDate.now());
        cli.setDatanasc(get("datanasc"));
        cli.setStatus(true);

        end.setBairro(get("bairro"));
        end.setCep(get("cep"));
        end.setCidade(get("cidade"));
        end.setCliente(cli);
        end.setComplemento(get("complemento"));
        end.setEndereco(get("endereco"));
        end.setNumeroEndereco(get("numero"));
        end.setStatus(Boolean.TRUE);
        try {
            end.setUf(((Estado) get("estado")).sigla);
        } catch (Exception ex) {

        }
    }

    @FXML
    public void salvar(ActionEvent event) {

        if (validate(validationSupport)) {

            try {
                updateState();

                if (cli.getId() == 0) {
                    daoObject.Insert(cli);
                } else {
                    daoObject.Update(cli);
                }

                if (end.getId() == 0) {
                    enderecoDao.Insert(end);
                } else {
                    enderecoDao.Update(end);
                }

                arquivos.forEach(x -> {
                    x.setCliente(cli);
                    if (x.getId() == 0) {
                        arquivosDao.Insert(x);
                    } else {
                        arquivosDao.Update(x);
                    }
                });

                markDelete.forEach(x -> arquivosDao.Delete(x));

                Dialog.createAlert("Salvando Cliente", "Salvando Cliente", "O cliente foi salvo com sucesso.");

                this.close();

            } catch (Exception ex) {

                Dialog.createException("Ocorreu um erro desconhecido ao salvar o cliente!", ex);

                Dialog.createYesNo("Salvando Cliente", "Nova Tentativa",
                        "Deseja continuar a edição do cliente e tentar novamente?",
                        x -> {
                            if (x == ButtonType.NO) {

                                this.close();
                            }

                        });

            }

        }

    }

    @FXML
    public void inserirImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem...");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
                new ExtensionFilter("Todos", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(timelord.TimeLord.MainStage);
        if (selectedFile != null) {
            String result = Dialog.createInputDialog("Imagem", "Nome da Imagem:");

            ArquivoCliente i = new ArquivoCliente(result, selectedFile.getAbsolutePath());

            arquivos.add(i);

        }
    }

    @FXML
    public void deletarImagem() {
        if (selectedCell == null) {
        } else {

            ArquivoCliente img = ((TaggedImage<ArquivoCliente>) selectedCell.imageBox.imgData).getTag();

            markDelete.add(img);

            arquivos.remove(img);

        }
    }

    @FXML
    public AnchorPane form;
    @FXML
    public AnchorPane images;
    @FXML
    public TextField randomText;

    @FXML
    public Label noImagesLabel;

    @FXML
    void cancelar(ActionEvent event) {
        this.close();
    }

}
