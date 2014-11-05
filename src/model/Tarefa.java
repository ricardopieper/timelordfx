package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tarefas")
public class Tarefa implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public ObjectProperty<Tarefa> propTarefaPai = new SimpleObjectProperty<>();
    public StringProperty propNome = new SimpleStringProperty();
    public StringProperty propDescricao = new SimpleStringProperty();
    public ObjectProperty<LocalDate> propDataInicio = new SimpleObjectProperty<>();
    public ObjectProperty<LocalDate> propDataFim = new SimpleObjectProperty<>();
    public IntegerProperty propHorasEstimadas = new SimpleIntegerProperty();
    public DoubleProperty propCustoEstimado = new SimpleDoubleProperty();
    public DoubleProperty propCustoRealizado = new SimpleDoubleProperty();
    public ObjectProperty<Setor> propSetor = new SimpleObjectProperty<>();
    public ObjectProperty<Projeto> propProjeto = new SimpleObjectProperty<>();

    public IntegerProperty propStatus = new SimpleIntegerProperty();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return propId.get();
    }

    @ManyToOne
    public Tarefa getTarefaPai() {
        return propTarefaPai.get();
    }

    @Column(length = 200, nullable = false)
    public String getNome() {
        return propNome.get();
    }

    @Column(length = 2000, nullable = false)
    public String getDescricao() {
        return propDescricao.get();
    }

    @Column(nullable = false)
    public LocalDate getDataInicio() {
        return propDataInicio.get();
    }

    @Column(nullable = true)
    public LocalDate getDataFim() {
        return propDataFim.get();
    }

    @Column(nullable = true)
    public int getHorasEstimadas() {
        return propHorasEstimadas.get();
    }

    @Column(nullable = true)
    public double getCustoEstimado() {
        return propCustoEstimado.get();
    }

    @Column(nullable = true)
    public double getCustoRealizado() {
        return propCustoRealizado.get();
    }

    // @ManyToOne
    // public Colaborador getColaboradorResponsavel(){ return propColaboradorResponsavel.get();}
    @ManyToOne
    public Setor getSetor() {
        return propSetor.get();
    }

    @ManyToOne
    public Projeto getProjeto() {
        return propProjeto.get();
    }

    @Column(nullable = false)
    public int getStatus() {
        return propStatus.get();
    }

    public void setId(int value) {
        propId.set(value);
    }

    public void setTarefaPai(Tarefa value) {
        propTarefaPai.set(value);
    }

    public void setNome(String value) {
        propNome.set(value);
    }

    public void setDescricao(String value) {
        propDescricao.set(value);
    }

    public void setDataInicio(LocalDate value) {
        propDataInicio.set(value);
    }

    public void setDataFim(LocalDate value) {
        propDataFim.set(value);
    }

    public void setHorasEstimadas(int value) {
        propHorasEstimadas.set(value);
    }

    public void setCustoEstimado(double value) {
        propCustoEstimado.set(value);
    }

    public void setCustoRealizado(double value) {
        propCustoRealizado.set(value);
    }

    //   public void setColaboradorResponsavel(Colaborador value){ propColaboradorResponsavel.set(value); }
    public void setStatus(int value) {
        propStatus.set(value);
    }

    public void setSetor(Setor value) {
        propSetor.set(value);
    }

    public void setProjeto(Projeto value) {
        propProjeto.set(value);
    }

    @Override
    public String toString() {
        return this.getNome();
    }

}
