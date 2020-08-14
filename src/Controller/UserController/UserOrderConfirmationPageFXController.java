package Controller.UserController;

import Model.Exceptions.UserNotInDatabaseException;
import View.UserView.UserOrderConfirmationPageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserOrderConfirmationPageFXController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button goBackButton, checkOutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        //VBox vbox = UserOrderConfirmationPageView.buildOrderConfirmationPageView();
        GridPane gridPane = UserOrderConfirmationPageView.buildOrderConfirmationPageView();

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setId("ordersummary-scrollpane");
        scrollPane.getStylesheets().add("/CSS/style.css");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        anchorPane.getChildren().add(scrollPane);
        AnchorPane.setTopAnchor(scrollPane, (double) 70);
        AnchorPane.setLeftAnchor(scrollPane, (double) 80);
        AnchorPane.setBottomAnchor(scrollPane, (double) 30);

    }

    public void handleGoBackButton(ActionEvent actionEvent) {

    }
}
