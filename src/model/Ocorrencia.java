
package model;

import java.io.Serializable;
import javafx.beans.property.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencia implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
  
    public StringProperty propDescricao = new SimpleStringProperty();

    
    
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    public int getId(){ return propId.get();}
    
    @Column(columnDefinition = "TEXT", nullable = false)
    public String getDescricao(){ return propDescricao.get();}
    
    public void setId(int value){ this.propId.set(value); }
    public void setDescricao(String value){ this.propDescricao.set(value); }
      
}
