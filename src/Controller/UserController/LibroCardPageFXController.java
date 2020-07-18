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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibroCardPageFXController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    Pane gpLibroCardInfos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* gpLibroCardInfos = LibroCardPageView.buildLibroCardView(GeneralLoginController.getLoginInstance());

        anchorPane.getChildren().add(gpLibroCardInfos);

        AnchorPane.setLeftAnchor(gpLibroCardInfos, (double) 260);
        AnchorPane.setTopAnchor(gpLibroCardInfos, (double) 315);*/
    }

}
