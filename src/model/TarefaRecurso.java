
package model;

import java.io.Serializable;
import javafx.beans.property.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tarefasrecursos")
public class TarefaRecurso implements Serializable {

   public IntegerProperty propRecursoId = new SimpleIntegerProperty();
   public IntegerProperty propTarefaId = new SimpleIntegerProperty();
   public DoubleProperty propCusto = new SimpleDoubleProperty();
        
    @Id 
    public int      getRecursoId(){ return propRecursoId.get();}
    @Id 
    public int      getTarefaId(){ return propRecursoId.get();}
    
    public double   getCusto() {return propCusto.get(); }

    
    
    public void setRecursoId(int value){ propRecursoId.set(value); }
    public void setTarefaId(int value){ propTarefaId.set(value); }
    public void setCusto(double value){ propCusto.set(value); }
      
}
