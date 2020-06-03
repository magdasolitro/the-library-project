package Controller.UserController;

import Model.Book;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserMainPageFXController implements Initializable {
    @FXML
    ScrollPane scrollPane;

    @FXML
    ChoiceBox genresDropDown;

    @FXML
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

    @FXML
    public void handleCartClick(MouseEvent evt) throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // set possible choices in drop down menu
        genresDropDown.getItems().addAll("All", "Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> bookList = new ArrayList<>();

        // bookList = bookDAO.getAllBooks();

        // scrollPane.setContent(bookList);
    }


}
