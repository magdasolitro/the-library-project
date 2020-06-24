package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageFXController implements Initializable {

    @FXML
    Button userButton, employeeButton;

    @FXML
    Label continueAsGuest, signInLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    private void handleUserButtonClick(MouseEvent evt) throws IOException {
        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();

        viewPage("../FXML/UserFXML/UserLoginFX.fxml");
    }

    @FXML
    private void handleEmployeeButtonClick(MouseEvent evt) throws IOException {
        Stage stage = (Stage) employeeButton.getScene().getWindow();
        stage.close();

        viewPage("../FXML/EmployeeFXML/EmployeeLoginFX.fxml");
    }

    @FXML
    private void handleGuestClick(MouseEvent evt) throws IOException {
        Stage stage = (Stage) continueAsGuest.getScene().getWindow();
        stage.close();

        //viewPage("...");
    }

    @FXML
    private void handleSignInClick(MouseEvent evt) throws IOException{
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
