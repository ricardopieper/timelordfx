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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "arquivosprojeto")
public class ArquivoProjeto implements Serializable, IArquivo {

    public IntegerProperty propId = new SimpleIntegerProperty();
    public StringProperty propCaminho = new SimpleStringProperty();
    public StringProperty propNome = new SimpleStringProperty();
    public StringProperty propExtensao = new SimpleStringProperty();
    public BooleanProperty propStatus = new SimpleBooleanProperty();
    public ObjectProperty<Projeto> propProjeto = new SimpleObjectProperty<>();

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
    @Override
    public String getCaminho() {
        return propCaminho.get();
    }

    @Column(length = 20, nullable = false)
    public String getExtensao() {
        return propExtensao.get();
    }

    public boolean getStatus() {
        return propStatus.get();
    }

    @ManyToOne
    public Projeto getProjeto() {
        return propProjeto.get();
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
        this.propCaminho.set(value);
    }

    public void setStatus(boolean value) {
        this.propStatus.set(value);
    }

    public void setProjeto(Projeto value) {
        this.propProjeto.set(value);
    }

}
