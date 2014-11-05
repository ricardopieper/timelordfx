package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "projetos")
public class Projeto implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public StringProperty propNome = new SimpleStringProperty();
    public StringProperty propDescricao = new SimpleStringProperty();

    public ObjectProperty<LocalDate> propDataPrevInicio = new SimpleObjectProperty<>();
    public ObjectProperty<LocalDate> propDataPrevFim = new SimpleObjectProperty<>();
    public DoubleProperty propCustoEstimado = new SimpleDoubleProperty();
    public ObjectProperty<Tarefa> propTarefaPrincipal = new SimpleObjectProperty<>();
    public IntegerProperty propStatus = new SimpleIntegerProperty();
    public ObjectProperty<LocalDate> propDataCadastro = new SimpleObjectProperty<>();
    public ObjectProperty<Cliente> propCliente = new SimpleObjectProperty<>();
    public ObservableList<ArquivoProjeto> propArqProjetos = FXCollections.observableArrayList();
    public ObjectProperty<Endereco> propEndereco = new SimpleObjectProperty<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return propId.get();
    }

    @Column(length = 200, nullable = false)
    public String getNome() {
        return propNome.get();
    }

    @Column(length = 2000, nullable = false)
    public String getDescricao() {
        return propDescricao.get();
    }

    @ManyToOne
    public Endereco getEndereco() {
        return propEndereco.get();
    }

    public LocalDate getDataPrevInicio() {
        return propDataPrevInicio.get();
    }

    public LocalDate getDataPrevFim() {
        return propDataPrevFim.get();
    }

    public double getCustoEstimado() {
        return propCustoEstimado.get();
    }

    @Column(nullable = false)
    public int getStatus() {
        return propStatus.get();
    }

    public LocalDate getDataCadastro() {
        return propDataCadastro.get();
    }

    @ManyToOne
    public Cliente getCliente() {
        return this.propCliente.get();
    }

    @ManyToOne
    public Tarefa getTarefaPrincipal() {
        return this.propTarefaPrincipal.get();
    }

    @OneToMany(mappedBy = "projeto")
    public List<ArquivoProjeto> getArquivos() {
        return propArqProjetos;
    }

    public void setId(int value) {
        this.propId.set(value);
    }

    public void setNome(String value) {
        this.propNome.set(value);
    }

    public void setDescricao(String value) {
        this.propDescricao.set(value);
    }

    public void setEndereco(Endereco value) {
        this.propEndereco.set(value);
    }

    public void setDataPrevInicio(LocalDate value) {
        this.propDataPrevInicio.set(value);
    }

    public void setDataPrevFim(LocalDate value) {
        this.propDataPrevFim.set(value);
    }

    public void setCustoEstimado(double value) {
        this.propCustoEstimado.set(value);
    }

    public void setStatus(int value) {
        this.propStatus.set(value);
    }

    public void setDataCadastro(LocalDate value) {
        this.propDataCadastro.set(value);
    }

    public void setCliente(Cliente value) {
        this.propCliente.set(value);
    }

    public void setTarefaPrincipal(Tarefa value) {
        this.propTarefaPrincipal.set(value);
    }

    public void setArquivos(List<ArquivoProjeto> arquivos) {
        if (arquivos != null) {
            try {
                propArqProjetos.setAll(arquivos);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
