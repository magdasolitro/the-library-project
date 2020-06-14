package Controller.UserController;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import View.UserMainPageView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class UserMainPageFXController implements Initializable {
    @FXML
    ChoiceBox genresDropDown;

    @FXML
    AnchorPane leftPane, rightPane;

    @FXML
    TextField bookSearchTextField;

    @FXML
    RadioButton priceAscRB, priceDescRB, publYearRB, titleRB;

    ScrollPane bookView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        // set possible choices in drop down menu
        genresDropDown.getItems().addAll("All", "Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");

        // build full catalog view
        try {
            BookDAO bookDAO = new BookDaoImpl();

            ArrayList<Book> fullCatalog = new ArrayList<>();
            fullCatalog.addAll(bookDAO.getAllBooks());

            bookView = UserMainPageView.buildCatalogView(fullCatalog);
            rightPane.getChildren().add(bookView);
            rightPane.setTopAnchor(bookView, (double) 100);
        } catch (InvalidStringException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        // set toggle group for radio buttons
        // (so that only one radio button can be selected at once!)
        ToggleGroup group = new ToggleGroup();
        priceAscRB.setToggleGroup(group);
        priceDescRB.setToggleGroup(group);
        publYearRB.setToggleGroup(group);
        titleRB.setToggleGroup(group);

    }

    public void handleProfileClick(MouseEvent evt) throws IOException {
        //((Image) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXML/UserFXML/UserProfileFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void handleCartClick(MouseEvent evt) throws IOException {
        //((Image) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXML/UserFXML/CartPageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    public void handleLogOutRequest(MouseEvent evt) throws IOException {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Log Out");
        confirmLogOut.setHeaderText("You will exit the program");
        confirmLogOut.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.get() == ButtonType.OK) {
            ((Label) evt.getSource()).getScene().getWindow().hide();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../FXML/WelcomePageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }


    public void handlePriceAscFilter(MouseEvent evt) throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        Collections.sort(orderedBooks, Comparator.comparing(Book::getTitle));

        // clear rightPane
        if(rightPane.getChildren() != null){
            rightPane.getChildren().removeAll();
        }

        bookView = UserMainPageView.buildCatalogView(orderedBooks);
        rightPane.getChildren().add(bookView);
        rightPane.setTopAnchor(bookView, (double) 100);

    }

    public void handlePriceDescFilter(MouseEvent evt) throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        Collections.sort(orderedBooks, Comparator.comparing(Book::getPrice));

        // clear rightPane
        if(rightPane.getChildren() != null){
            rightPane.getChildren().removeAll();
        }

        bookView = UserMainPageView.buildCatalogView(orderedBooks);
        rightPane.getChildren().add(bookView);
        rightPane.setTopAnchor(bookView, (double) 100);
    }

    public void handlePublYearFilter(MouseEvent evt) throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        Collections.sort(orderedBooks, Comparator.comparing(Book::getPublishingYear));

        // clear rightPane
        if(rightPane.getChildren() != null){
            rightPane.getChildren().removeAll();
        }

        bookView = UserMainPageView.buildCatalogView(orderedBooks);
        rightPane.getChildren().add(bookView);
        rightPane.setTopAnchor(bookView, (double) 100);
    }

    public void handleTitleFilter(MouseEvent mouseEvent) throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        Collections.sort(orderedBooks, Comparator.comparing(Book::getTitle));

        // clear rightPane
        if(rightPane.getChildren() != null){
            rightPane.getChildren().removeAll();
        }

        bookView = UserMainPageView.buildCatalogView(orderedBooks);
        rightPane.getChildren().add(bookView);
        rightPane.setTopAnchor(bookView, (double) 100);

    }


    public void goToProfilePage(MouseEvent mouseEvent) {

    }

    public void goToCartPage(MouseEvent mouseEvent) {

    }

    public void handleBookSearch(MouseEvent mouseEvent) throws InvalidStringException,
            SQLException, IllegalValueException {

        String bookTitle = bookSearchTextField.getText();

        if(!bookTitle.equals(null)){

            // clear rightPane
            if(rightPane.getChildren() != null){
                rightPane.getChildren().removeAll();
            }

            BookDAO bookDAO = new BookDaoImpl();

            ArrayList<Book> books = new ArrayList<>();

            books.addAll(bookDAO.getBookByTitle(bookTitle));

            bookView = UserMainPageView.buildCatalogView(books);
            rightPane.getChildren().add(bookView);
            rightPane.setTopAnchor(bookView, (double) 100);
        } else {
            /* DA IMPLEMENTARE: se si clicca il bottone di ricerca e
            * il TextField è vuoto, visualizzare l'intero catalogo */
        }
    }

}
