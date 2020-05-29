package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageFXController implements Initializable {
    @FXML
    private void handleUserBottonClick(MouseEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/UserView/UserLoginPageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void handleEmployeeButtonClick(MouseEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/EmployeeView/EmployeeLoginPageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void handleGuestClick(MouseEvent evt) throws IOException {

    }

    @FXML
    private void handleSignInClick(MouseEvent evt) throws IOException{

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
