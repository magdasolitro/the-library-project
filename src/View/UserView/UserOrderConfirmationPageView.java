package View.UserView;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.*;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.NotSameUserException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Utils.DAOs.*;
import Model.Utils.DaoImpl.*;
import Model.Utils.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserOrderConfirmationPageView {

    public static GridPane buildOrderConfirmationPageView(){
        GridPane gridPane = new GridPane();

        Label orderConfirmationLabel;
        Label totalCostLabel;
        Button checkOutButton;

        Label paymentMethodLabel;
        ChoiceBox<String> paymentMethodCB;

        Label shippingAddressLabel;
        TextField shippingAddressTF;

        // page title label
        orderConfirmationLabel = new Label("Order Confirmation");
        orderConfirmationLabel.setFont(new Font("Avenir Next Bold", 50));

        CartDAO cartDAO = new CartDaoImpl();

        String currentUserEmail = GeneralLoginController.getLoginInstance();

        try {
            totalCostLabel = new Label("Total: $ " + cartDAO.totalCost(currentUserEmail));
            totalCostLabel.setFont(new Font("Avenir Next Bold", 25));
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            totalCostLabel = new Label("ERROR: Unable to retrieve price");
            totalCostLabel.setFont(new Font("Avenir Book", 25));
        }


        GridPane paymentMethodGP = new GridPane();

        paymentMethodLabel = new Label("Payment method: ");
        paymentMethodLabel.setFont(new Font("Avenir Book", 20));

        paymentMethodCB = new ChoiceBox<>();

        paymentMethodCB.getItems().addAll("Credit Card", "Paypal", "Cash in delivery");
        paymentMethodCB.setPrefWidth(170);
        paymentMethodCB.setPrefHeight(33);

        paymentMethodGP.add(paymentMethodLabel, 0,0);
        paymentMethodGP.add(paymentMethodCB, 0, 1);
        paymentMethodGP.setVgap(10);

        GridPane shippingAddressGP = new GridPane();

        shippingAddressLabel = new Label("Shipping address: ");
        shippingAddressLabel.setFont(new Font("Avenir Book", 20));

        shippingAddressTF = new TextField();
        shippingAddressTF.setPrefHeight(36);
        shippingAddressTF.setPrefWidth(300);
        shippingAddressTF.setFont(new Font("Avenir Book", 17));

        shippingAddressGP.add(shippingAddressLabel, 0, 0);
        shippingAddressGP.add(shippingAddressTF, 0, 1);
        shippingAddressGP.setVgap(10);

        checkOutButton = new Button("Check Out");
        checkOutButton.setId("checkout-button");
        checkOutButton.getStylesheets().add("/CSS/style.css");


        gridPane.add(orderConfirmationLabel, 0,0);
        gridPane.add(paymentMethodGP, 0,2);
        gridPane.add(shippingAddressGP, 0,3);
        gridPane.add(totalCostLabel, 0,4);
        gridPane.add(checkOutButton, 0,5);


        Insets insets = new Insets(0, 0, 40, 20);

        GridPane.setMargin(orderConfirmationLabel, insets);
        GridPane.setMargin(paymentMethodGP, insets);
        GridPane.setMargin(shippingAddressGP, insets);
        GridPane.setMargin(totalCostLabel, insets);
        GridPane.setMargin(checkOutButton, insets);

        gridPane.setId("allorders-gridpane");
        gridPane.getStylesheets().add("/CSS/style.css");

        return gridPane;
    }

}
