package Controller.UserController.UserNotRegController;

import Controller.OrderIDController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserNROrderSuccessfulPageFXController implements Initializable {

    @FXML
    private Pane whitePane;

    @FXML
    private Button trackOrdersButton, backToMainPageButton;

    private Label orderIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIDLabel = new Label(OrderIDController.getCurrentOrderID());
        orderIDLabel.setFont(new Font("Avenir Book", 20));

        whitePane.getChildren().add(orderIDLabel);
        orderIDLabel.relocate(400,230);
    }

    public void goToAllOrdersPage() {
        Stage stage = (Stage) trackOrdersButton.getScene().getWindow();
        stage.close();

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/UserFXML/UserNotRegFXML/UserNRAllOrdersPageFX.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoBackToMainPage() {
        Stage stage = (Stage) backToMainPageButton.getScene().getWindow();
        stage.close();

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
