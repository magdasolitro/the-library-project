package Controller.UserController;

import Controller.BookInstanceController;
import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import Model.Utils.DaoImpl.CartDaoImpl;
import View.UserView.SingleBookPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SingleBookPageFXController implements Initializable {

    @FXML
    Button addToCartButton;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button goBackButton;

    @FXML
    ImageView profileIcon, cartIcon;

    @FXML
    Label logoutLabel;

    Spinner<Integer> quantitySpinner;

    ScrollPane spBookInfos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        spBookInfos = SingleBookPageView.buildBookInfos(BookInstanceController.getCurrentBookISBN());

        spBookInfos.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spBookInfos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        spBookInfos.setId("single-book-scrollpane");
        spBookInfos.getStylesheets().add("/CSS/style.css");

        AnchorPane.setTopAnchor(spBookInfos, (double) 80);
        AnchorPane.setRightAnchor(spBookInfos, (double) 480);
        AnchorPane.setBottomAnchor(spBookInfos, (double) 40);
        AnchorPane.setLeftAnchor(spBookInfos, (double) 80);

        quantitySpinner = new Spinner<>(1, 10, 1, 1);
        quantitySpinner.setPrefHeight(45);
        quantitySpinner.setPrefWidth(80);
        AnchorPane.setTopAnchor(quantitySpinner, (double) 314);
        AnchorPane.setLeftAnchor(quantitySpinner, (double) 1037);

        anchorPane.getChildren().addAll(spBookInfos, quantitySpinner);

        if(GeneralLoginController.getLoginInstance().substring(0,6).equals("NOTREG")){
            Label loginLabel = new Label("LOG IN");
            loginLabel.setFont(new Font("Avenir Book", 20));
            loginLabel.setUnderline(true);

            loginLabel.setOnMouseClicked(e -> {
                Stage stage = (Stage) loginLabel.getScene().getWindow();
                stage.close();

                try {
                    viewPage("../../FXML/WelcomePageFX.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Label signinLabel = new Label("SIGN IN");
            signinLabel.setFont(new Font("Avenir Book", 20));
            signinLabel.setUnderline(true);

            signinLabel.setOnMouseClicked(e -> {
                Stage stage = (Stage) signinLabel.getScene().getWindow();
                stage.close();

                try {
                    viewPage("../../FXML/SignInPageFX.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            anchorPane.getChildren().addAll(loginLabel, signinLabel);

            loginLabel.relocate(900,41);
            signinLabel.relocate(1020, 41);

        } else {
            Label logoutLabel = new Label("LOG OUT");
            logoutLabel.setFont(new Font("Avenir Book", 20));
            logoutLabel.setUnderline(true);

            logoutLabel.setOnMouseClicked(e -> {
                Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

                confirmLogOut.setTitle("Log Out");
                confirmLogOut.setHeaderText("Are you sure you want to log out?");
                confirmLogOut.setContentText("You will be redirected to the Welcome Page.");

                Optional<ButtonType> response = confirmLogOut.showAndWait();

                if(response.isPresent() && response.get() == ButtonType.OK) {
                    Stage stage = (Stage) logoutLabel.getScene().getWindow();
                    stage.close();

                    GeneralLoginController.logout();

                    try {
                        viewPage("../../FXML/WelcomePageFX.fxml");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    e.consume();
                    confirmLogOut.close();
                }
            });

            anchorPane.getChildren().add(logoutLabel);

            logoutLabel.relocate(900, 41);

            // load the image
            Image userIconImage = null;

            try {
                userIconImage = new Image(new FileInputStream("src/images/userIcon.png"));

                ImageView imgView = new ImageView();
                imgView.setImage(userIconImage);

                imgView.setFitWidth(80);
                imgView.setFitHeight(75);

                imgView.setOnMouseClicked( e -> {
                    Stage stage = (Stage) imgView.getScene().getWindow();
                    stage.close();

                    try {
                        viewPage("../../FXML/UserFXML/UserProfileFX.fxml");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                anchorPane.getChildren().add(imgView);

                imgView.relocate(1045, 14);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }

    public void addToCart() {
        CartDAO cartDAO = new CartDaoImpl();
        BookDAO bookDAO = new BookDaoImpl();

        String currentBookISBN = BookInstanceController.getCurrentBookISBN();
        String currentUser = GeneralLoginController.getLoginInstance();

        // retrieve cart content for else-if test
        try {
            ArrayList<Book> booksInCart = new ArrayList<>(cartDAO.cartContent(currentUser));
            String bookToAddISBN = bookDAO.getBook(currentBookISBN).getISBN();

            ArrayList<String> booksInCartISBN = new ArrayList<>();

            for (Book b : booksInCart){
                booksInCartISBN.add(b.getISBN());
            }

            if(booksInCartISBN.contains(bookToAddISBN)){
                Alert bookAlreadyPresent = new Alert(Alert.AlertType.ERROR);

                bookAlreadyPresent.setTitle("Book Already Present");
                bookAlreadyPresent.setHeaderText("This book is already present in your cart!");
                bookAlreadyPresent.setContentText("You may want to choose another book instead.");

                bookAlreadyPresent.showAndWait();
            } else if(!bookDAO.isAvailable(currentBookISBN)) {
                Alert bookNotAvailable = new Alert(Alert.AlertType.ERROR);

                bookNotAvailable.setTitle("Book Not Available");
                bookNotAvailable.setHeaderText("This book is currently out of stock.");
                bookNotAvailable.setContentText("You may want to choose another book instead.");

                bookNotAvailable.showAndWait();
            } else {
                cartDAO.addBookToCart(currentBookISBN, currentUser, quantitySpinner.getValue());

                Alert bookAddedToCart = new Alert(Alert.AlertType.INFORMATION);

                bookAddedToCart.setTitle("Book Added To Cart");
                bookAddedToCart.setHeaderText("We added this book to your cart!");
                bookAddedToCart.setContentText("Press \"OK\" to continue your shopping.");

                bookAddedToCart.showAndWait();
            }
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }
    }

    public void goToProfilePage() {
        Stage stage = (Stage) profileIcon.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserProfileFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCartPage() {
        Stage stage = (Stage) cartIcon.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserCartPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogOutRequest() {
        Stage stage = (Stage) logoutLabel.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserCartPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage(LastOpenedPageController.getLastOpenedPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/SingleBookPageFX.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
