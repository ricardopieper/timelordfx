
package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import timelord.TimeLord;


public interface IControllerListagem {
   
    @FXML
    public void insert();
   
    @FXML
    public void edit();
   
    @FXML
    public void delete();
    
    @FXML
    public void filter();
    
    @FXML
    public void clearFilter();
    
    @FXML
    public void close();

       
    public void setDialogStage(Stage stage);
    
    public void setApplication(TimeLord lord);
    
    public Stage getDialogStage();
    
    public TimeLord getApplication();

}

