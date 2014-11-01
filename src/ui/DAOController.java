
package ui;

import dao.BaseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public abstract class DAOController<E, T extends BaseDAO<?>> 
    extends BaseController<E>  {
    
    public ObservableList<E> objetos 
            = FXCollections.observableArrayList();
    
    public T daoObject;
    
    public DAOController(T daoObject){
        this.daoObject = daoObject;
    }
 
    
    
    
    
}
