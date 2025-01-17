package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomePageFXController {

    @FXML
    Button userButton, employeeButton;

    @FXML
    Label continueAsGuest, signInLabel;


    @FXML
    private void handleUserButtonClick() throws IOException {
        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();

        viewPage("../FXML/UserFXML/UserLoginFX.fxml");
    }

    @FXML
    private void handleEmployeeButtonClick() throws IOException {
        Stage stage = (Stage) employeeButton.getScene().getWindow();
        stage.close();

        viewPage("../FXML/EmployeeFXML/EmployeeLoginFX.fxml");
    }

    @FXML
    private void handleGuestClick() throws IOException {
        Stage stage = (Stage) continueAsGuest.getScene().getWindow();
        stage.close();

        // create unique identifier for non registered user
        String userNotRegIdentifier = "NOTREG";
        GeneralLoginController.setLoginInstance(userNotRegIdentifier);

        viewPage("../FXML/UserNotRegFXML/UserNotRegMainPageFX.fxml");
    }

    @FXML
    private void handleSignInClick() throws IOException{
        Stage stage = (Stage) signInLabel.getScene().getWindow();
        stage.close();

        viewPage("../FXML/SignInPageFX.fxml");
    }


    private void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
