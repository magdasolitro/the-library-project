package Controller.UserController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import View.UserView.UserMainPageView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class UserMainPageFXController implements Initializable {
    @FXML
    private ChoiceBox<String> genresChoiceBox;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private TextField bookSearchTextField;

    @FXML
    private RadioButton priceAscRB, priceDescRB, publYearRB, titleRB;

    @FXML
    private Button searchButton;

    @FXML
    private ImageView profileIcon, cartIcon;

    @FXML
    private Label logoutLabel;

    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        // set possible choices in drop down menu
        genresChoiceBox.getItems().addAll("All", "Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");

        // build full catalog view
        try {
            BookDAO bookDAO = new BookDaoImpl();

            ArrayList<Book> fullCatalog = new ArrayList<>(bookDAO.getAllBooks());

            scrollPane = UserMainPageView.buildBooksView(fullCatalog);

            rightPane.getChildren().add(scrollPane);
            scrollPane.prefWidthProperty().bind(rightPane.widthProperty());

            AnchorPane.setTopAnchor(scrollPane, (double) 100);
            AnchorPane.setBottomAnchor(scrollPane, (double) 0);
        } catch (InvalidStringException | SQLException | IllegalValueException e) {
            e.printStackTrace();
        }

        // set toggle group for radio buttons, so that only one button can be
        // selected at once
        ToggleGroup group = new ToggleGroup();

        priceAscRB.setToggleGroup(group);
        priceDescRB.setToggleGroup(group);
        publYearRB.setToggleGroup(group);
        titleRB.setToggleGroup(group);

        // set style for search button
        searchButton.setId("search-button");
        searchButton.getStylesheets().add("/CSS/style.css");

    }


    public void handleLogOutRequest(MouseEvent evt) throws IOException {

        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Log Out");
        confirmLogOut.setHeaderText("You will exit the program");
        confirmLogOut.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            Stage stage = (Stage) logoutLabel.getScene().getWindow();
            stage.close();

            GeneralLoginController.logout();

            viewPage("../../FXML/WelcomePageFX.fxml");
        } else {
            evt.consume();
            confirmLogOut.close();
        }

    }


    public void handlePriceAscFilter() throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        orderedBooks.sort(Comparator.comparing(Book::getPrice));

        // clear rightPane
        rightPane.getChildren().remove(scrollPane);

        changeBookView(orderedBooks);

    }

    public void handlePriceDescFilter() throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        orderedBooks.sort(Comparator.comparing(Book::getPrice));
        Collections.reverse(orderedBooks);

        // clear rightPane
        rightPane.getChildren().remove(scrollPane);

        changeBookView(orderedBooks);
    }

    public void handlePublYearFilter() throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        orderedBooks.sort(Comparator.comparing(Book::getPublishingYear));

        // clear rightPane
        rightPane.getChildren().remove(scrollPane);

       changeBookView(orderedBooks);
    }

    public void handleTitleFilter() throws InvalidStringException,
            SQLException, IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> orderedBooks = bookDAO.getAllBooks();

        orderedBooks.sort(Comparator.comparing(Book::getTitle));

        // clear rightPane
        rightPane.getChildren().remove(scrollPane);

        changeBookView(orderedBooks);

    }


    public void goToProfilePage() {
        try{
            Stage stage = (Stage) profileIcon.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/UserProfileFX.fxml");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void goToCartPage() {
        try{
            Stage stage = (Stage) cartIcon.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/CartPageFX.fxml");
        } catch (IOException ioe){
            System.out.println("IOException" + ioe.getMessage());
        }

    }

    public void handleBookSearch(MouseEvent evt) throws InvalidStringException,
            SQLException, IllegalValueException {

        String bookTitle = bookSearchTextField.getText();

        if (bookTitle.equals("")) {
            // build full catalog view
            try {
                BookDAO bookDAO = new BookDaoImpl();

                ArrayList<Book> fullCatalog = new ArrayList<>(bookDAO.getAllBooks());

                changeBookView(fullCatalog);
            } catch (InvalidStringException | SQLException | IllegalValueException ise) {
                ise.printStackTrace();
            }
        } else {

            // show only the selected book
            BookDAO bookDAO = new BookDaoImpl();

            ArrayList<Book> searchedBook = new ArrayList<>(bookDAO.getBookByTitle(bookTitle));

            if(searchedBook.isEmpty()){
                Alert bookNotFound = new Alert(Alert.AlertType.WARNING);

                bookNotFound.setTitle("Book Not Found");
                bookNotFound.setHeaderText("This book is not in sale");
                bookNotFound.setContentText("Try with another title...");

                bookNotFound.showAndWait();
            } else {
                rightPane.getChildren().remove(scrollPane);
                changeBookView(searchedBook);
            }
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

    private void changeBookView(ArrayList<Book> bookList) {
        scrollPane = UserMainPageView.buildBooksView(bookList);

        rightPane.getChildren().add(scrollPane);
        scrollPane.prefWidthProperty().bind(rightPane.widthProperty());

        AnchorPane.setTopAnchor(scrollPane, (double) 100);
        AnchorPane.setBottomAnchor(scrollPane, (double) 0);
    }

}
