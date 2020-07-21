import Controller.LastOpenedPageController;
import Model.EmployeeRoleEnum;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DaoImpl.EmployeeDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXML/WelcomePageFX.fxml"));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("../../FXML/WelcomePageFX.fxml");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        /*EmployeeDAO employeeDAO = new EmployeeDaoImpl();

        employeeDAO.addEmployee("anna", "Anna", "Ricciardi", "13-11-1993",
                EmployeeRoleEnum.CASHIER, "01-09-2010", "");*/
    }

    public static void main(String[] args){
        launch(args);
    }
}