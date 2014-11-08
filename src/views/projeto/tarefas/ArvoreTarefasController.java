package views.projeto.tarefas;

import dao.RecursoDAO;
import dao.SetoresDAO;
import dao.TarefaRecursoDAO;
import dao.TarefasDAO;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.event.DocumentEvent;
import jidefx.scene.control.field.DateField;
import jidefx.scene.control.field.IntegerField;
import jidefx.scene.control.field.LocalDateField;
import jidefx.scene.control.field.LocalDateTimeField;
import jidefx.scene.control.field.NumberField;
import model.Projeto;
import model.Recurso;
import model.Setor;
import model.Tarefa;
import model.TarefaRecurso;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ListSelectionView;
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

    private final RecursoDAO recursosDao = new RecursoDAO();

    public ArvoreTarefasController() {
        super(new TarefasDAO());
    }

    /**
     * Initializes the controller class.
     */
    private TarefaRecurso recursoSelecionado = null;

    private final CheckListView<TarefaRecurso> view = new CheckListView<>();

    private final List<TarefaRecurso> recursosCollection = new ArrayList<>();

    private final HashMap<Tarefa, ObservableList<TarefaRecurso>> tarefaRecursos
            = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        format.setMaximumIntegerDigits(10);
        format.setMaximumFractionDigits(2);
        format.setGroupingUsed(false);
        format.setMinimumFractionDigits(2);

        custoRecurso.setDecimalFormat(format);

        ObservableList<Setor> setores = FXCollections.observableArrayList(new SetoresDAO().GetAtivos());
        setores.add(0, null);
        setor.setItems(setores);

        custoRecurso.setDisable(true);

        view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        view.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends TarefaRecurso> c) -> {

            if (c.getList().size() > 0) {

                TarefaRecurso selectedItem = c.getList().get(0);

                if (recursoSelecionado != null) {
                    //atualiza o valor

                    recursoSelecionado.setCusto(custoRecurso.getValue().doubleValue());

                    Bindings.unbindBidirectional(custoRecurso.valueProperty(), recursoSelecionado.propCusto);

                }
                recursoSelecionado = selectedItem;
                Bindings.bindBidirectional(custoRecurso.valueProperty(), recursoSelecionado.propCusto);

                enableCusto();
            }

        });

        view.setItems(FXCollections.observableArrayList(recursosCollection));

        recursos.getChildren().add(view);
        fitToParent(view);

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

    @FXML
    private AnchorPane recursos;

    @FXML
    private NumberField custoRecurso;

    private List<Tarefa> allTarefas;// = daoObject.GetAllFromProjeto(projeto);

    private ObservableList<TarefaRecurso> markToDelete = FXCollections.observableArrayList();

    private Tarefa tarefaSelecionada = null;

    private CheckChange currentChecker;

    public void buildTree() {

        if (projeto == null) {
            Dialog.createAlert("Erro",
                    "Projeto não encontrado",
                    "O projeto não foi encontrado!");
            return;
        }
        allTarefas = daoObject.GetAllFromProjeto(projeto);

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
            custoRecurso.setDisable(true);

            if (tarefaSelecionada != null) {

                Bindings.unbindBidirectional(nomeTarefa.textProperty(), tarefaSelecionada.propNome);
                Bindings.unbindBidirectional(dataInicio.valueProperty(), tarefaSelecionada.propDataInicio);
                Bindings.unbindBidirectional(dataFim.valueProperty(), tarefaSelecionada.propDataFim);
                Bindings.unbindBidirectional(horasEstimadas.valueProperty(), tarefaSelecionada.propHorasEstimadas);
                Bindings.unbindBidirectional(setor.valueProperty(), tarefaSelecionada.propSetor);

            }

            tarefaSelecionada = nv.getValue();

            view.setDisable(true);
            if (nv.getParent() != null) { //se não for root
                Arrays.asList(new Node[]{nomeTarefa, dataInicio, dataFim, horasEstimadas, setor}).forEach(x -> x.setDisable(false));
                Bindings.bindBidirectional(nomeTarefa.textProperty(), tarefaSelecionada.propNome);

                if (nv.isLeaf()) { //só se for folha pode mudar datas e horas

                    view.setDisable(false);

                    Bindings.bindBidirectional(dataInicio.valueProperty(), tarefaSelecionada.propDataInicio);
                    Bindings.bindBidirectional(dataFim.valueProperty(), tarefaSelecionada.propDataFim);
                    Bindings.bindBidirectional(horasEstimadas.valueProperty(), tarefaSelecionada.propHorasEstimadas);

                    if (tarefaRecursos.containsKey(tarefaSelecionada)) {

                        ObservableList<TarefaRecurso> l = tarefaRecursos.get(tarefaSelecionada);

                        view.setItems(l);

                    } else {

                        ObservableList<TarefaRecurso> l = FXCollections.observableArrayList();

                        recursosDao
                                .GetAll()
                                .forEach(x -> {
                                    TarefaRecurso tar = new TarefaRecurso();
                                    tar.setRecurso(x);
                                    tar.setCusto(x.getCustoPadrao());
                                    tar.setTarefa(tarefaSelecionada);
                                    l.add(tar);
                                });

                        tarefaRecursos.put(tarefaSelecionada, l);
                        view.setItems(l);
                    }

                    view.getCheckModel().clearChecks();
                    //seta os recursos
                    tarefaSelecionada.getRecursos().forEach(x -> {

                        view.getCheckModel().check(x);

                        tarefaRecursos
                                .get(tarefaSelecionada)
                                .forEach(y -> {

                                    if (y.equals(x)) {
                                        y.setCusto(x.getCusto());
                                    }
                                });
                    });
                    if (currentChecker != null) {
                        view.getCheckModel().getCheckedItems().removeListener(currentChecker);
                    }
                    currentChecker = new CheckChange();
                    view.getCheckModel().getCheckedItems().addListener(currentChecker);

                    enableCusto();

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

    class CheckChange implements ListChangeListener<TarefaRecurso> {

        @Override
        public void onChanged(ListChangeListener.Change<? extends TarefaRecurso> c) {
            while (c.next()) {

                c.getRemoved().forEach(x -> {
                    markToDelete.add(x);
                    tarefaSelecionada.getRecursos().remove(x);
                });
                c.getAddedSubList().forEach(x -> {

                    markToDelete.remove(x);
                    x.setTarefa(tarefaSelecionada);

                    tarefaSelecionada
                            .getRecursos()
                            .add(x);
                });

            }
            enableCusto();
        }
    }

    private void enableCusto() {
        custoRecurso.setDisable(true);
        if (tree.getSelectionModel().getSelectedItem().isLeaf()) {

            if (tarefaSelecionada.getRecursos().contains(view.getSelectionModel().getSelectedItem())) {

                custoRecurso.setDisable(false);
            }
        }
    }

    private void render(TreeItem<Object> item) {

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
            if (item.getValue().getSetor() != null) {
                t.setSetor(item.getValue().getSetor());
            }
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
    private final TarefaRecursoDAO tarRecDao = new TarefaRecursoDAO();

    @FXML
    void salvar(ActionEvent event) {

        Dialog.createYesNo("Salvar Tarefas", "Salvar Tarefas",
                "Deseja salvar as modificações?", (o) -> {
                    if (o.getButtonData() == ButtonBar.ButtonData.YES) {

                        //ArrayList<Tarefa> tarefas = new ArrayList<>();
                        if (saveTree(tree.getRoot())) {
                            markToDelete.forEach(x -> tarRecDao.Delete(x));
                            this.close();
                        }
                    }
                });

    }

    @FXML
    void cancel() {

        Dialog.createYesNo("Cancelar Edição", "Cancelar Edição", "Tem certeza que deseja cancelar a edição da WBS?", (x) -> {

            if (x.getButtonData() == ButtonBar.ButtonData.YES) {

                this.close();

            }
        });
    }

    void doNothing() {
    }

    public boolean saveTree(TreeItem<Tarefa> treeItem) {

        Tarefa item = treeItem.getValue();
        boolean failed = false;
        try {
            if (treeItem.isLeaf() && item.getDataInicio() == null) {
                Dialog.createAlert("Erro de Validação", "Valor Inválido!", "Preencha a data de início da tarefa " + item.getNome());
                failed = true;
            }
            if (!failed) {

                if (item.getId() == 0) {
                    daoObject.Insert(item);
                } else {
                    item.getRecursos().forEach(x -> {
                        tarRecDao.SaveOrUpdate(x);
                    });
                    daoObject.Update(item);
                }

            }

        } catch (Exception ex) {
            failed = true;
            Dialog.createException("Ocorreu um erro inesperado ao salvar a tarefa " + item.getNome() + "!", ex);

            Dialog.createYesNo("Nova Tentativa", "Tentar Novamente", "Deseja continuar nesta tela e tentar novamente?", (x) -> {

                if (x.getButtonData() == ButtonBar.ButtonData.NO) {

                    this.close();

                }
            });
        }
        if (!failed) {

            boolean childSuccess = true;
            for (TreeItem<Tarefa> it : treeItem.getChildren()) {
                if (childSuccess) {
                    childSuccess = saveTree(it);
                } else {
                    break;
                }
            }
        }
        return !failed;
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
