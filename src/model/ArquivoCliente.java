package model;

import imagescroller.IArquivo;
import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "arquivoscliente")
public class ArquivoCliente implements Serializable, IArquivo {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public StringProperty propCaminho = new SimpleStringProperty();
    public StringProperty propNome = new SimpleStringProperty();
    public StringProperty propExtensao = new SimpleStringProperty();
    public BooleanProperty propStatus = new SimpleBooleanProperty();
    public ObjectProperty<Cliente> propCliente = new SimpleObjectProperty<>();

    public ArquivoCliente(String name, String path) {
        this.setCaminho(path);
        this.setNome(name);
        this.setStatus(true);
    }

    public ArquivoCliente() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return propId.get();
    }

    @Column(length = 200, nullable = true)
    @Override
    public String getNome() {
        return propNome.get();
    }

    @Column(length = 255, nullable = false)

    public String getCaminho() {
        return propCaminho.get();
    }

    @Override
    @Transient
    public String getCaminhoImagem() {
        return getCaminho();
    }

    @Column(length = 20)
    public String getExtensao() {
        return propExtensao.get();
    }

    public boolean getStatus() {
        return propStatus.get();
    }

    @ManyToOne
    public Cliente getCliente() {
        return propCliente.get();
    }

    public void setId(int value) {
        this.propId.set(value);
    }

    public void setNome(String value) {
        this.propNome.set(value);
    }

    public void setCaminho(String value) {
        this.propCaminho.set(value);
    }

    public void setExtensao(String value) {
        this.propExtensao.set(value);
    }

    public void setStatus(boolean value) {
        this.propStatus.set(value);
    }

    public void setCliente(Cliente value) {
        this.propCliente.set(value);
    }

}
