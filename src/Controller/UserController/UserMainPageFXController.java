package Controller.UserController;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserMainPageFXController implements Initializable {
    @FXML
    ChoiceBox genresDropDown;

    @FXML
    AnchorPane leftPane, rightPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // set possible choices in drop down menu
        genresDropDown.getItems().addAll("All", "Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");

        try {
            ScrollPane bookView = UserMainPageView.buildCatalogView();
            rightPane.getChildren().add(bookView);
            //rightPane.positionInArea(bookView, 0, 100, 1034, 700, 0, HPos.CENTER, VPos.CENTER);
        } catch (InvalidStringException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

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
