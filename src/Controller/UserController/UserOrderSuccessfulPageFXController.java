package Controller.UserController;

import Model.Utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOrderSummaryPageFXController implements Initializable {
    @FXML
    Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.openConnection();


    }
}
