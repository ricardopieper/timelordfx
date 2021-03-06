package model;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recursos")
public class Recurso implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public StringProperty propNome = new SimpleStringProperty();
    public StringProperty propDescricao = new SimpleStringProperty();
    public IntegerProperty propTipo = new SimpleIntegerProperty();
    public DoubleProperty propCustoPadrao = new SimpleDoubleProperty();
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

    @Column(length = 2000)
    public String getDescricao() {
        return propDescricao.get();
    }

    @Column(nullable = false)
    public int getTipo() {
        return propTipo.get();
    }

    @Column(nullable = false)
    public double getCustoPadrao() {
        return propCustoPadrao.get();
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

    public void setTipo(int value) {
        this.propTipo.set(value);
    }

    public void setCustoPadrao(double value) {
        this.propCustoPadrao.set(value);
    }

    public void setStatus(boolean value) {
        this.propStatus.set(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Recurso) {
            Recurso r = (Recurso) o;
            if (r.getId() == this.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.propId);
        return hash;
    }

}
