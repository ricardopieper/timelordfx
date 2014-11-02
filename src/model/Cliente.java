package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import utils.formatting.Formats;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public StringProperty propNome = new SimpleStringProperty();
    public StringProperty propCPF = new SimpleStringProperty();
    public ObjectProperty<LocalDate> propDatanasc = new SimpleObjectProperty<>();
    public ObjectProperty<LocalDate> propDataCadastro = new SimpleObjectProperty<>();
    public BooleanProperty propStatus = new SimpleBooleanProperty();
    public ListProperty<ArquivoCliente> propArquivos = new SimpleListProperty<>(FXCollections.observableArrayList());
    public ListProperty<Endereco> propEnderecos = new SimpleListProperty<>(FXCollections.observableArrayList());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return propId.get();
    }

    @Column(length = 200, nullable = false)
    public String getNome() {
        return propNome.get();
    }

    @Column(length = 11, nullable = false)
    public String getCpf() {
        return propCPF.get();
    }

    @OneToMany(mappedBy = "cliente")
    public List<Endereco> getEnderecos() {
        return propEnderecos.get();
    }

    public LocalDate getDatanasc() {
        return propDatanasc.get();
    }

    public LocalDate getDataCadastro() {
        return propDataCadastro.get();
    }

    public boolean getStatus() {
        return propStatus.get();
    }

    @OneToMany(mappedBy = "cliente")
    public List<ArquivoCliente> getArquivos() {
        return propArquivos.get();
    }

    public void setId(int value) {
        this.propId.set(value);
    }

    public void setNome(String value) {
        this.propNome.set(value);
    }

    public void setCpf(String value) {
        this.propCPF.set(value);
    }

    public void setDatanasc(LocalDate value) {
        this.propDatanasc.set(value);
    }

    public void setDataCadastro(LocalDate value) {
        this.propDataCadastro.set(value);
    }

    public void setStatus(boolean value) {
        this.propStatus.set(value);
    }

    public void setArquivos(List<ArquivoCliente> arquivos) {
        this.propArquivos.clear();
        this.propArquivos.addAll(arquivos);
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.propEnderecos.clear();
        this.propEnderecos.addAll(enderecos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Cliente) {
                Cliente c = (Cliente) obj;

                if (c.getId() == this.getId()) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.propId.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return this.getNome() + " - " + Formats.CPF.putMask(this.getCpf());
    }

}
