
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
@Table(name = "registrostarefa")
public class RegistroTarefa implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public ObjectProperty<Tarefa> propTarefa = new SimpleObjectProperty<>();

    public ObjectProperty<LocalDateTime> propDataHoraInicio = new SimpleObjectProperty<>();
    public ObjectProperty<LocalDateTime> propDataHoraFim = new SimpleObjectProperty<>();
    public BooleanProperty propRetrabalho = new SimpleBooleanProperty();
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    public int getId(){ return propId.get();}
    
    @ManyToOne
    public Tarefa getTarefa(){
        return propTarefa.get();
    }
    
    @Column(nullable = false)
    public LocalDateTime getDataHoraInicio(){ return propDataHoraInicio.get();}
   
    @Column(nullable = true)
    public LocalDateTime getDataHoraFim(){ return propDataHoraFim.get();}
  
    @Column(nullable = false)
    public boolean getRetrabalho(){ return propRetrabalho.get(); }
   
    
    public void setId(int value){ this.propId.set(value); }
    public void setTarefa(Tarefa value){ propTarefa.set(value); }
    public void setDataHoraInicio(LocalDateTime value){propDataHoraInicio.set(value);}
    public void setDataHoraFim(LocalDateTime value){propDataHoraFim.set(value);}
    public void setRetrabalho(boolean value){ this.propRetrabalho.set(value); }

    
}
