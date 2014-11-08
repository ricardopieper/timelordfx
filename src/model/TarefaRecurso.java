package model;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.*;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tarefasrecursos")
public class TarefaRecurso implements Serializable {

    public ObjectProperty<TarefaRecursoId> propPk = new SimpleObjectProperty<>(new TarefaRecursoId());
    public DoubleProperty propCusto = new SimpleDoubleProperty();

    @EmbeddedId
    public TarefaRecursoId getPk() {
        return propPk.get();
    }

    public void setPk(TarefaRecursoId pk) {
        this.propPk.set(pk);
    }

    @Transient
    public Tarefa getTarefa() {
        return this.getPk().getTarefa();
    }

    public void setTarefa(Tarefa t) {
        this.getPk().setTarefa(t);
    }

    @Transient
    public Recurso getRecurso() {
        return this.getPk().getRecurso();
    }

    public void setRecurso(Recurso r) {
        this.getPk().setRecurso(r);
    }

    public double getCusto() {
        return propCusto.get();
    }

    public void setCusto(double value) {
        propCusto.set(value);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarefaRecurso obj = (TarefaRecurso) o;

        if (getPk() != null ? !getPk().equals(obj.getPk()) : obj.getPk() != null) {
            return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.propPk);
        hash = 19 * hash + Objects.hashCode(this.propCusto);
        return hash;

    }

    @Override
    public String toString() {
        return this.getRecurso().getNome();
    }

}
