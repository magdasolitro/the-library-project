package Controller.UserController;

import Controller.LastOpenedPageController;
import View.UserView.UserAllOrdersPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAllOrdersPageFXController implements Initializable {

    @FXML
    Button goBackButton;

    @FXML
    AnchorPane violetAnchorPane, whiteAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        ScrollPane allOrdersSP = UserAllOrdersPageView.buildAllOrdersView();

        allOrdersSP.prefWidthProperty().bind(whiteAnchorPane.widthProperty());

        whiteAnchorPane.getChildren().add(allOrdersSP);

        AnchorPane.setTopAnchor(allOrdersSP, (double) 30);
        AnchorPane.setRightAnchor(allOrdersSP, (double) 0);
        AnchorPane.setBottomAnchor(allOrdersSP, (double) 30);
        AnchorPane.setLeftAnchor(allOrdersSP, (double) 0);
    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try{
            viewPage(LastOpenedPageController.getLastOpenedPage());
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    private void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserMainPageFX.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
}
