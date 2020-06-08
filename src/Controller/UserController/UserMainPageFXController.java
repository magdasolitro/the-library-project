package Controller.UserController;

import View.UserMainPageView;
import View.UserMainPageView.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMainPageFXController implements Initializable {
    @FXML
    ScrollPane scrollPane;

    @FXML
    ChoiceBox genresDropDown;

    public void handleProfileClick(MouseEvent evt) throws IOException {
        ((Button) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXML/UserFXML/UserMainPageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void handleCartClick(MouseEvent evt) throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // set possible choices in drop down menu
        genresDropDown.getItems().addAll("All", "Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");
    }

    public void handleLogOutRequest(MouseEvent evt) throws IOException {
        ((Button) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../WelcomePageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void handlePriceAscFilter(MouseEvent evt) {

    }

    public void handlePriceDescFilter(MouseEvent evt) {

    }

    public void handlePublYearFilter(MouseEvent evt) {

    }
}
