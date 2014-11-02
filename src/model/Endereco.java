package model;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enderecos")
public class Endereco implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public StringProperty propEndereco = new SimpleStringProperty();
    public StringProperty propNumeroEndereco = new SimpleStringProperty();
    public StringProperty propBairro = new SimpleStringProperty();
    public StringProperty propCEP = new SimpleStringProperty();
    public StringProperty propComplemento = new SimpleStringProperty();
    public StringProperty propCidade = new SimpleStringProperty();
    public StringProperty propUF = new SimpleStringProperty();
    public BooleanProperty propStatus = new SimpleBooleanProperty();
    public ObjectProperty<Cliente> propCliente = new SimpleObjectProperty<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return propId.get();
    }

    @Column(length = 200, nullable = false)
    public String getEndereco() {
        return propEndereco.get();
    }

    @Column(length = 20, nullable = false)
    public String getNumeroEndereco() {
        return propNumeroEndereco.get();
    }

    @Column(length = 200, nullable = false)
    public String getBairro() {
        return propBairro.get();
    }

    @Column(length = 8, nullable = false)
    public String getCep() {
        return propCEP.get();
    }

    @Column(length = 200)
    public String getComplemento() {
        return propComplemento.get();
    }

    @Column(length = 100, nullable = false)
    public String getCidade() {
        return propCidade.get();
    }

    @Column(length = 2, nullable = false)
    public String getUf() {
        return propUF.get();
    }

    public Boolean getStatus() {
        return propStatus.get();
    }

    // public Boolean getSelecionado(){ return propStatus.get();}
    @ManyToOne
    public Cliente getCliente() {
        return propCliente.get();
    }

    public void setId(int value) {
        this.propId.set(value);
    }

    public void setEndereco(String value) {
        this.propEndereco.set(value);
    }

    public void setNumeroEndereco(String value) {
        this.propNumeroEndereco.set(value);
    }

    public void setBairro(String value) {
        this.propBairro.set(value);
    }

    public void setCep(String value) {
        this.propCEP.set(value);
    }

    public void setComplemento(String value) {
        this.propComplemento.set(value);
    }

    public void setCidade(String value) {
        this.propCidade.set(value);
    }

    public void setUf(String value) {
        this.propUF.set(value);
    }

    public void setStatus(Boolean value) {
        this.propStatus.set(value);
    }

    public void setCliente(Cliente value) {
        this.propCliente.set(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Endereco) {
                Endereco e = (Endereco) obj;

                if (e.getId() == this.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.propId);
        return hash;
    }

}
