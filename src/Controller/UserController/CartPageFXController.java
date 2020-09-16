package Controller.UserController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DaoImpl.CartDaoImpl;
import View.UserView.CartPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CartPageFXController implements Initializable {
    @FXML
    private AnchorPane rightPane;

    @FXML
    private Button goBackButton, checkOutButton;

    @FXML
    private AnchorPane cartPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // build full cart view
        try {
            CartDAO cartDAO = new CartDaoImpl();

            ArrayList<Book> booksInCart = cartDAO.cartContent(GeneralLoginController.getLoginInstance());

            if(booksInCart.size() == 0){
                Label emptyCartLabel = new Label("Your cart is empty :(");
                emptyCartLabel.setFont(new Font("Avenir Book", 25));

                cartPane.getChildren().add(emptyCartLabel);

                AnchorPane.setTopAnchor(emptyCartLabel, (double) 80);
                AnchorPane.setRightAnchor(emptyCartLabel, (double) 80);
                AnchorPane.setBottomAnchor(emptyCartLabel, (double) 80);
                AnchorPane.setLeftAnchor(emptyCartLabel, (double) 80);

            } else {
                ScrollPane scrollPane = CartPageView.buildCartView(booksInCart, true);

                scrollPane.setId("cart-scrollpane");
                scrollPane.getStylesheets().add("/CSS/style.css");

                cartPane.getChildren().add(scrollPane);

                AnchorPane.setTopAnchor(scrollPane, (double) 120);
                AnchorPane.setRightAnchor(scrollPane, (double) 0);
                AnchorPane.setBottomAnchor(scrollPane, (double) 30);
                AnchorPane.setLeftAnchor(scrollPane, (double) 0);
            }

        } catch (InvalidStringException | SQLException | IllegalValueException e) {
            e.printStackTrace();
        }

        CartDAO cartDAO = new CartDaoImpl();
        BigDecimal totalCost = new BigDecimal(0);

        try {
            totalCost = cartDAO.totalCost(GeneralLoginController.getLoginInstance()).setScale(2, RoundingMode.FLOOR);
        } catch (SQLException | IllegalValueException | InvalidStringException e) {
            e.printStackTrace();
        }

        Label totalCostLabel = new Label("$ " + totalCost);
        totalCostLabel.setFont(new Font("Avenir Next", 25));

        rightPane.getChildren().add(totalCostLabel);
        totalCostLabel.relocate(215,324);

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try{
            switch (LastOpenedPageController.getLastOpenedPage()) {
                case "../../../FXML/UserFXML/UserRegFXML/UserProfileFX.fxml": {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../../FXML/UserFXML/UserRegFXML/UserProfileFX.fxml"));
                    Parent root = loader.load();

                    LastOpenedPageController.setLastOpenedPage("../../../FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");

                    Scene scene = new Scene(root);
                    Stage newStage = new Stage();

                    newStage.setScene(scene);
                    newStage.setMaximized(true);
                    newStage.show();

                    break;
                }
                case "../../FXML/UserFXML/SingleBookPageFX.fxml": {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../../FXML/UserFXML/SingleBookPageFX.fxml"));
                    Parent root = loader.load();

                    if(GeneralLoginController.getLoginInstance().substring(0,6).equals("NOTREG")) {
                        LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml");
                    } else {
                        LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");
                    }

                    Scene scene = new Scene(root);
                    Stage newStage = new Stage();

                    newStage.setScene(scene);
                    newStage.setMaximized(true);
                    newStage.show();
                    break;
                }
                case "../../../FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml": {
                    viewPage("../../FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");
                    break;
                }
                case "../../../FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml":{
                    viewPage("../../FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleCheckOut() {
        Stage stage = (Stage) checkOutButton.getScene().getWindow();
        stage.close();

        try {
            if(GeneralLoginController.getLoginInstance().substring(0,6).equals("NOTREG")) {
                viewPage("../../FXML/UserNotRegFXML/UserNROrderConfirmationPageFX.fxml");
            } else {
                viewPage("../../FXML/UserFXML/UserOrderConfirmationPageFX.fxml");
            }
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

