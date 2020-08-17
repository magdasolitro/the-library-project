package Controller.UserController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DAOs.CompositionDAO;
import Model.Utils.DaoImpl.CartDaoImpl;
import Model.Utils.DaoImpl.CompositionDaoImpl;
import View.UserView.CartPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

public class UserCartPageFXController implements Initializable {
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

            ScrollPane scrollPane = CartPageView.buildCartView(booksInCart);

            scrollPane.setId("cart-scrollpane");
            scrollPane.getStylesheets().add("/CSS/style.css");

            cartPane.getChildren().add(scrollPane);

            AnchorPane.setTopAnchor(scrollPane, (double) 120);
            AnchorPane.setRightAnchor(scrollPane, (double) 0);
            AnchorPane.setBottomAnchor(scrollPane, (double) 30);
            AnchorPane.setLeftAnchor(scrollPane, (double) 0);

        } catch (InvalidStringException | SQLException | IllegalValueException e) {
            e.printStackTrace();
        }

        CartDAO cartDAO = new CartDaoImpl();
        BigDecimal totalCost = new BigDecimal(0);

        try {
            totalCost = cartDAO.totalCost(GeneralLoginController.getLoginInstance()).setScale(2, RoundingMode.FLOOR);
            System.out.println(totalCost);
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
                case "../../FXML/UserFXML/UserProfileFX.fxml": {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../../FXML/UserFXML/UserProfileFX.fxml"));
                    Parent root = loader.load();

                    LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserMainPageFX.fxml");

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

                    LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserMainPageFX.fxml");

                    Scene scene = new Scene(root);
                    Stage newStage = new Stage();

                    newStage.setScene(scene);
                    newStage.setMaximized(true);
                    newStage.show();
                    break;
                }
                case "../../FXML/UserFXML/UserMainPageFX.fxml": {
                    viewPage("../../FXML/UserFXML/UserMainPageFX.fxml");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleCheckOut(MouseEvent mouseEvent) {
        Alert checkOutAlert = new Alert(Alert.AlertType.CONFIRMATION);

        /*checkOutAlert.setTitle("Check Out");
        checkOutAlert.setHeaderText("Do you want confirm this order");
        checkOutAlert.setContentText("Press \"OK\" to confirm");

        Optional<ButtonType> response = checkOutAlert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserCartPageFX.fxml");

            CompositionDAO compositionDAO = new CompositionDaoImpl();

            //TODO: complete checkout operation

        } else {
            mouseEvent.consume();
            checkOutAlert.close();
        }

         */

        Stage stage = (Stage) checkOutButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserOrderConfirmationPageFX.fxml");
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

