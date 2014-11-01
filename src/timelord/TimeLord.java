package timelord;

import dao.ClientesDAO;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.BaseController;
import views.cliente.ListaClientesController;
import views.home.HomeController;

/**
 *
 * @author Ricardo
 */
public class TimeLord extends Application {

    public static Stage MainStage;

    private static BorderPane masterLayout;
    
    public static TimeLord ApplicationInstance;
    

    @Override
    public void start(Stage primaryStage) {
        TimeLord.MainStage = primaryStage;
        TimeLord.MainStage.setTitle("TimeLord");

        inicMasterLayout();

         mostrarHome();
        
        TimeLord.ApplicationInstance = this;

    }

    public void inicMasterLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TimeLord.class.getResource("/views/MasterLayout.fxml"));

            masterLayout = (BorderPane) loader.load();

            Scene scene = new Scene(masterLayout);

            MainStage.setScene(scene);

            MainStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

  

    public void mostrarClientes() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TimeLord.class.getResource("/views/cliente/ListaClientes.fxml"));

            AnchorPane listaClientes = (AnchorPane) loader.load();

            masterLayout.setCenter(listaClientes);
            
            ListaClientesController controller = loader.getController();
            
            controller.setApplication(this);
            controller.setDialogStage(MainStage);
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void mostrarHome() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TimeLord.class.getResource("/views/home/Home.fxml"));

            AnchorPane home = (AnchorPane) loader.load();

            masterLayout.setCenter(home);
            
            HomeController controller = loader.getController();
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
