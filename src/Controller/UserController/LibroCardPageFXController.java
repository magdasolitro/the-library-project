package Controller.UserController;

import Controller.GeneralLoginController;
import View.UserView.LibroCardPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibroCardPageFXController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @FXML
    Button goBackButton, myOrdersButton;

    GridPane gpLibroCardInfos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gpLibroCardInfos = LibroCardPageView.buildLibroCardView(GeneralLoginController.getLoginInstance());

        anchorPane.getChildren().add(gpLibroCardInfos);

        AnchorPane.setLeftAnchor(gpLibroCardInfos, (double) 260);
        AnchorPane.setTopAnchor(gpLibroCardInfos, (double) 315);
    }

    public void handleGoBackButtonClick(MouseEvent mouseEvent) {
        try{
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/UserProfileFX.fxml");
        } catch(IOException ioe){
            ioe.getStackTrace();
        }
    }

    public void goToOrdersPage() {
        try{
            Stage stage = (Stage) myOrdersButton.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/OrdersPageFX.fxml");
        } catch(IOException ioe){
            ioe.getStackTrace();
        }
    }


    private void viewPage(String path) throws IOException{
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
