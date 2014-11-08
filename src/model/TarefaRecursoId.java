package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class TarefaRecursoId implements Serializable {

    private Tarefa tarefa;
    private Recurso recurso;

    @ManyToOne
    public Tarefa getTarefa() {
        return this.tarefa;
    }

    public void setTarefa(Tarefa t) {
        this.tarefa = t;
    }

    @ManyToOne
    public Recurso getRecurso() {
        return this.recurso;
    }

    public void setRecurso(Recurso r) {
        this.recurso = r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarefaRecursoId obj = (TarefaRecursoId) o;

        if (tarefa != null ? !tarefa.equals(obj.tarefa) : obj.tarefa != null) {
            return false;
        }
        if (recurso != null ? !recurso.equals(obj.recurso) : obj.recurso != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.tarefa);
        hash = 31 * hash + Objects.hashCode(this.recurso);
        return hash;
    }
    
    @Override
    public String toString(){
        return "("+this.getTarefa().getId()+")"+ this.getTarefa().getNome() + " - ("+this.getRecurso().getId()+") " +this.getRecurso().getNome();
    }
}
