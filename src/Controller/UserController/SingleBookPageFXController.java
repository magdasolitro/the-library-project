package Controller.UserController;

import Controller.BookInstanceController;
import Controller.GeneralLoginController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SingleBookPageFXController implements Initializable {

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

    VBox vbBook;

    String ISBN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ISBN = BookInstanceController.getCurrentBookISBN();

        spBookInfos = SingleBookPageView.buildBookInfos(ISBN);

        spBookInfos.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBookInfos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane.setTopAnchor(spBookInfos, (double) 130);
        AnchorPane.setRightAnchor(spBookInfos, (double) 500);
        AnchorPane.setBottomAnchor(spBookInfos, (double) 60);
        AnchorPane.setRightAnchor(spBookInfos, (double) 70);

        quantitySpinner = new Spinner<>(1, 10, 1, 1);
        quantitySpinner.setPrefHeight(45);
        quantitySpinner.setPrefWidth(80);
        AnchorPane.setTopAnchor(quantitySpinner, (double) 310);
        AnchorPane.setLeftAnchor(quantitySpinner, (double) 1020);

        anchorPane.getChildren().addAll(spBookInfos, quantitySpinner);

    }

    public void addToCart(MouseEvent mouseEvent) {
        CartDAO cartDAO = new CartDaoImpl();
        BookDAO bookDAO = new BookDaoImpl();

        try{
            if(!bookDAO.isAvailable(ISBN)){
                Alert bookAddedToCart = new Alert(Alert.AlertType.ERROR);

                bookAddedToCart.setTitle("Book Not Available");
                bookAddedToCart.setHeaderText("This book is currently out of stock.");
                bookAddedToCart.setContentText("... but we have many more books " +
                        "you can choose!");

                bookAddedToCart.showAndWait();
            } else {
                cartDAO.addBookToCart(ISBN, GeneralLoginController.getLoginInstance(),
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

    public void goToProfilePage(MouseEvent mouseEvent) {
        Stage stage = (Stage) profileIcon.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserProfileFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCartPage(MouseEvent mouseEvent) {
        Stage stage = (Stage) cartIcon.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/CartPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogOutRequest(MouseEvent mouseEvent) {
        Stage stage = (Stage) logoutLabel.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/CartPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoBackButton(MouseEvent mouseEvent) {
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

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
