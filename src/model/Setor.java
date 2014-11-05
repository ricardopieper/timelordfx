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
@Table(name = "setores")
public class Setor implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();

    public StringProperty propNome = new SimpleStringProperty();

    public StringProperty propDescricao = new SimpleStringProperty();

    public BooleanProperty propStatus = new SimpleBooleanProperty();

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

    @Column(nullable = false)
    public boolean getStatus() {
        return propStatus.get();
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

    public void setStatus(boolean value) {
        this.propStatus.set(value);
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
