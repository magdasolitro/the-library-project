package Controller.EmployeeController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Employee;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DaoImpl.EmployeeDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeLoginFXController implements Initializable {

    @FXML
    public Button loginButton, goBackButton;

    @FXML
    public TextField employeeEmailField, employeePasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }

    @FXML
    public void handleGoBackButtonClick() throws IOException {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        viewPage("../../FXML/WelcomePageFX.fxml");
    }


    public void handleLogInRequest() {
        if(employeeEmailField.getText().isEmpty() || employeePasswordField.getText().isEmpty()){
            Alert missingFields = new Alert(Alert.AlertType.ERROR);

            missingFields.setTitle("Missing Fields");
            missingFields.setHeaderText("You did not fill all the fields!");
            missingFields.setContentText("Your email and password are required in order to log in.");

            missingFields.showAndWait();
        } else {
            String employeeEmail = employeeEmailField.getText();
            String employeePassword = employeePasswordField.getText();

            EmployeeDAO employeeDAO = new EmployeeDaoImpl();

            try {
                Employee currentEmployee = employeeDAO.getEmployee(employeeEmail);

                if (currentEmployee == null) {
                    Alert userNotRegistred = new Alert(Alert.AlertType.ERROR);

                    userNotRegistred.setTitle("User Not Registered");
                    userNotRegistred.setHeaderText("It looks like you are not registered!");
                    userNotRegistred.setContentText("Try to sign in instead.");

                    userNotRegistred.show();
                } else if (!employeePassword.equals(currentEmployee.getPassword())) {
                    Alert wrongPassword = new Alert(Alert.AlertType.ERROR);

                    wrongPassword.setTitle("Wrong Password");
                    wrongPassword.setHeaderText("Your password is wrong!");
                    wrongPassword.setContentText("Try to re-type it.");

                    wrongPassword.show();
                } else {
                    GeneralLoginController.setLoginInstance(employeeEmail);

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();

                    viewPage("../../FXML/EmployeeFXML/EmployeeMainPageFX.fxml");

                }
            } catch (SQLException | InvalidStringException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("../../FXML/EmployeeFXML/EmployeeLoginFX.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
