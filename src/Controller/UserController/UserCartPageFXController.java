package Controller.UserController;

import Controller.GeneralLoginController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DaoImpl.CartDaoImpl;
import View.UserView.UserMainPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserCartPageFXController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @FXML
    Button goBackButton;

    ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane = new ScrollPane();

        // build full cart view
        try {
            CartDAO cartDAO = new CartDaoImpl();

            ArrayList<Book> booksInCart = new ArrayList<>(cartDAO.cartContent(GeneralLoginController.getLoginInstance()));

            scrollPane = UserMainPageView.buildBooksView(booksInCart);
            scrollPane.setPrefHeight(500);
            scrollPane.setPrefWidth(836);

        } catch (InvalidStringException | SQLException | IllegalValueException e) {
            e.printStackTrace();
        }

        CartDAO cartDAO = new CartDaoImpl();
        BigDecimal totalCost = new BigDecimal(0);

        try {
            totalCost = cartDAO.totalCost(GeneralLoginController.getLoginInstance());
        } catch (SQLException | IllegalValueException | InvalidStringException e) {
            e.printStackTrace();
        }

        Label totalLabel = new Label("TOTAL: ");
        totalLabel.setFont(new Font("Avenir Next Bold", 30));

        Label totalCostLabel = new Label("$ " + totalCost.toString());
        totalCostLabel.setFont(new Font("Avenir Next", 30));

        anchorPane.getChildren().addAll(scrollPane, totalLabel, totalCostLabel);

        AnchorPane.setTopAnchor(scrollPane, (double) 200);
        AnchorPane.setLeftAnchor(scrollPane, (double) 90);

        AnchorPane.setTopAnchor(totalLabel, (double) 320);
        AnchorPane.setLeftAnchor(totalLabel, (double) 1000);

        AnchorPane.setTopAnchor(totalCostLabel, (double) 320);
        AnchorPane.setLeftAnchor(totalCostLabel, (double) 1130);
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

