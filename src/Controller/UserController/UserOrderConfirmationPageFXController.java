package Controller.UserController;

import View.UserView.UserOrderConfirmationPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserOrderConfirmationPageFXController implements Initializable {
    @FXML
    private AnchorPane whiteAnchorPane;

    @FXML
    private Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        GridPane gridPane = UserOrderConfirmationPageView.buildOrderConfirmationPageView();

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setId("ordersummary-scrollpane");
        scrollPane.getStylesheets().add("/CSS/style.css");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.fitToWidthProperty();

        gridPane.prefWidthProperty().bind(whiteAnchorPane.widthProperty());

        whiteAnchorPane.getChildren().add(scrollPane);

        AnchorPane.setTopAnchor(scrollPane, (double) 30);
        AnchorPane.setRightAnchor(scrollPane, (double) 0);
        AnchorPane.setBottomAnchor(scrollPane, (double) 30);
        AnchorPane.setLeftAnchor(scrollPane, (double) 0);

    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserCartPageFX.fxml");
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
