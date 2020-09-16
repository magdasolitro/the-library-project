package Controller.UserController.UserRegController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserOrderSuccessfulPageFXController implements Initializable {
    @FXML
    Button goToOrdersButton, backToMainPageButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void goToAllOrdersPage() {
        Stage stage = (Stage) goToOrdersButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../../FXML/UserFXML/UserRegFXML/UserAllOrdersPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleGoBackToMainPage() {
        Stage stage = (Stage) backToMainPageButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../../FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
