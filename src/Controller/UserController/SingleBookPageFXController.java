package Controller.UserController;

import Controller.BookInstanceController;
import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    Spinner<Integer> quantitySpinner;

    ScrollPane spBookInfos;

    @FXML
    Label logoutLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        spBookInfos = SingleBookPageView.buildBookInfos(BookInstanceController.getCurrentBookISBN());

        spBookInfos.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }

    public void addToCart() {
        CartDAO cartDAO = new CartDaoImpl();
        BookDAO bookDAO = new BookDaoImpl();

        String ISBN = BookInstanceController.getCurrentBookISBN();

        try{
            if(!bookDAO.isAvailable(ISBN)){
                Alert bookNotAvailable = new Alert(Alert.AlertType.ERROR);

                bookNotAvailable.setTitle("Book Not Available");
                bookNotAvailable.setHeaderText("This book is currently out of stock.");
                bookNotAvailable.setContentText("... but we have many more books " +
                        "you can choose!");

                bookNotAvailable.showAndWait();
            } else {
                cartDAO.addBookToCart(BookInstanceController.getCurrentBookISBN(), GeneralLoginController.getLoginInstance(),
                        quantitySpinner.getValue());

                Alert bookAddedToCart = new Alert(Alert.AlertType.INFORMATION);

                bookAddedToCart.setTitle("Book Added To Cart");
                bookAddedToCart.setHeaderText("We added this book to your cart!");
                bookAddedToCart.setContentText("Press \"OK\" to continue your shopping.");

                bookAddedToCart.showAndWait();
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
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
            viewPage("../../FXML/UserFXML/UserMainPageFX.fxml");
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
