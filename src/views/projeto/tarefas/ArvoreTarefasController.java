package views.projeto.tarefas;

import dao.SetoresDAO;
import dao.TarefasDAO;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.event.DocumentEvent;
import jidefx.scene.control.field.DateField;
import jidefx.scene.control.field.IntegerField;
import jidefx.scene.control.field.LocalDateField;
import jidefx.scene.control.field.LocalDateTimeField;
import jidefx.scene.control.field.NumberField;
import model.Projeto;
import model.Setor;
import model.Tarefa;
import ui.DAOController;
import ui.Dialog;
import utils.DateUtil;

/**
 * FXML Controller class
 *
 * @author Ricardo
 */
public class ArvoreTarefasController
        extends DAOController<Tarefa, TarefasDAO> {

    public ArvoreTarefasController() {
        super(new TarefasDAO());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Setor> setores = FXCollections.observableArrayList(new SetoresDAO().GetAtivos());
        setores.add(null);
        setor.setItems(setores);

    }

    @FXML
    private TextField nomeTarefa;

    @FXML
    private DatePicker dataInicio;

    @FXML
    private DatePicker dataFim;

    @FXML
    private NumberField horasEstimadas;

    @FXML
    private ComboBox<Setor> setor;

    @FXML
    private TreeView<Tarefa> tree;

    private HashMap<Integer, Tarefa> hashMap = new HashMap<>();

    private List<Tarefa> allTarefas;// = daoObject.GetAllFromProjeto(projeto);

    private Tarefa tarefaSelecionada = null;

    public void buildTree() {

        if (projeto == null) {
            Dialog.createAlert("Erro",
                    "Projeto não encontrado",
                    "O projeto não foi encontrado!");
            return;
        }
        allTarefas = daoObject.GetAllFromProjeto(projeto);
        allTarefas.forEach(x -> hashMap.put(x.getId(), x));

        Tarefa tarefaPrincipal = projeto.getTarefaPrincipal();

        TreeItem<Tarefa> root = new TreeItem<>(tarefaPrincipal);
        root.setExpanded(true);

        build(tarefaPrincipal, null);

        render(((TreeView) tree).getRoot());

        tree.getRoot().addEventHandler(TreeItem.treeNotificationEvent(), x -> {

            render(x.getSource());
            render(x.getTreeItem());

        });

        nomeTarefa.focusedProperty().addListener((obs, old, nv) -> render((TreeItem) tree.getSelectionModel().selectedItemProperty().get()));

        tree.getSelectionModel().selectedItemProperty().addListener((obs, old, nv) -> {

            Tarefa t = nv.getValue();

            System.out.println(t.getNome());

            if (tarefaSelecionada != null) {

                Bindings.unbindBidirectional(nomeTarefa.textProperty(), tarefaSelecionada.propNome);
                Bindings.unbindBidirectional(dataInicio.valueProperty(), tarefaSelecionada.propDataInicio);
                Bindings.unbindBidirectional(dataFim.valueProperty(), tarefaSelecionada.propDataFim);
                Bindings.unbindBidirectional(horasEstimadas.valueProperty(), tarefaSelecionada.propHorasEstimadas);
                Bindings.unbindBidirectional(setor.valueProperty(), tarefaSelecionada.propSetor);

            }

            tarefaSelecionada = t;

            if (nv.getParent() != null) { //se não for root
                Arrays.asList(new Node[]{nomeTarefa, dataInicio, dataFim, horasEstimadas, setor}).forEach(x -> x.setDisable(false));
                Bindings.bindBidirectional(nomeTarefa.textProperty(), tarefaSelecionada.propNome);
                Bindings.bindBidirectional(setor.valueProperty(), tarefaSelecionada.propSetor);

                if (nv.isLeaf()) { //só se for folha pode mudar datas e horas
                    Bindings.bindBidirectional(dataInicio.valueProperty(), tarefaSelecionada.propDataInicio);
                    Bindings.bindBidirectional(dataFim.valueProperty(), tarefaSelecionada.propDataFim);
                    Bindings.bindBidirectional(horasEstimadas.valueProperty(), tarefaSelecionada.propHorasEstimadas);

                } else {
                    Arrays.asList(new Node[]{dataInicio, dataFim, horasEstimadas}).forEach(x -> x.setDisable(true));
                }

            } else { //se for root desabilita tudo!

                Arrays.asList(new Node[]{nomeTarefa, dataInicio, dataFim, horasEstimadas, setor}).forEach(x -> x.setDisable(true));

            }
        });

        tree.getSelectionModel().selectFirst();
        Arrays.asList(new Node[]{nomeTarefa, dataInicio, dataFim, horasEstimadas, setor}).forEach(x -> x.setDisable(true));
    }

    public void render(TreeItem<Object> item) {
        Tarefa val = (Tarefa) item.getValue();

        if (!item.isLeaf()) {
            if (item.isExpanded()) {

                item.setGraphic(
                        new ImageView(
                                new Image(
                                        timelord.TimeLord.class
                                        .getResourceAsStream("/ui/imagens/fopen.png"))));

            } else {
                item.setGraphic(
                        new ImageView(
                                new Image(
                                        timelord.TimeLord.class
                                        .getResourceAsStream("/ui/imagens/fclosed.png"))));
            }

            if (item.getChildren().size() > 0) {
                item.getChildren()
                        .forEach(x -> render(x));
            }

        } else {
            item.setGraphic(
                    new ImageView(
                            new Image(
                                    timelord.TimeLord.class
                                    .getResourceAsStream("/ui/imagens/play.png"))));

        }
    }
    
    
    

    public void build(Tarefa t, TreeItem<Tarefa> last) {

        TreeItem<Tarefa> item = new TreeItem<>(t);

        if (last != null) {

            last.getChildren().add(item);

        } else {
            tree.setRoot(item);

        }

        List<Tarefa> tarefasFilhas = new ArrayList<>();

        allTarefas.forEach(x -> {
            if (x.getTarefaPai() != null) {

                if (x.getTarefaPai().getId() == t.getId()) {
                    tarefasFilhas.add(x);
                }
            }
        });

        if (tarefasFilhas.size() > 0) {

            /* item.setGraphic(
             new ImageView(
             new Image(
             timelord.TimeLord.class
             .getResourceAsStream("/ui/imagens/fopen.png"))));
             */
            item.setExpanded(
                    true);

            tarefasFilhas.forEach(x
                    -> build(x, item));

        } /*else {

         item.setGraphic(
         new ImageView(
         new Image(
         timelord.TimeLord.class
         .getResourceAsStream("/ui/imagens/play.png"))));

         }*/

    }

    @FXML
    void addTask(ActionEvent event) {
        TreeItem<Tarefa> item = tree.getSelectionModel().getSelectedItem();

        if (item != null) {

            Tarefa t = new Tarefa();
            t.setProjeto(projeto);
            t.setTarefaPai(item.getValue());
            t.setDataInicio(LocalDate.now());
            t.setDataFim(LocalDate.now().plusDays(1));
            t.setNome("Nova Tarefa");

            item.getChildren().add(new TreeItem<>(t));
            item.setExpanded(true);
            Arrays.asList(new Node[]{dataInicio, dataFim, horasEstimadas}).forEach(x -> x.setDisable(true)); //desabilita esses campos atuais
        }
    }

    @FXML
    void removeTask(ActionEvent event) {

        TreeItem<Tarefa> item = tree.getSelectionModel().getSelectedItem();

        if (!item.isLeaf()) {
            notificacao("Deletando Tarefa", "Não é possível deletar uma categoria!", Pos.CENTER);
        } else {
            item.getParent().getChildren().remove(item);
        }
    }

    private Projeto projeto;

    public void setProjeto(Projeto prj) {
        this.projeto = prj;
        buildTree();
    }

    public Projeto getProjeto() {
        return projeto;
    }

}
