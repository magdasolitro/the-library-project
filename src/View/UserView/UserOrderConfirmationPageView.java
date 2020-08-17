package View.UserView;

import Controller.GeneralLoginController;
import Model.*;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.NotSameUserException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Utils.DAOs.*;
import Model.Utils.DaoImpl.*;
import Model.Utils.DatabaseConnection;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class UserOrderConfirmationPageView {

    public static GridPane buildOrderConfirmationPageView(){
        GridPane gridPane = new GridPane();

        Label orderConfirmationLabel;
        Label totalCostLabel;
        ScrollPane booksInCartSP;
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

        try {
            booksInCartSP = CartPageView.buildCartView(cartDAO.cartContent(currentUserEmail));
            booksInCartSP.setId("booksInCart-scrollpane");
            booksInCartSP.getStylesheets().add("/CSS/style.css");
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            booksInCartSP = new ScrollPane();
        }


        GridPane paymentMethodGP = new GridPane();

        paymentMethodLabel = new Label("Payment method: ");
        paymentMethodLabel.setFont(new Font("Avenir Book", 20));

        paymentMethodCB = new ChoiceBox<>();

        paymentMethodCB.getItems().addAll("Credit Card", "Paypal", "Cash in delivery");

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

        checkOutButton.setOnMouseClicked(e -> {
            if(shippingAddressTF.getText().isEmpty() || paymentMethodCB.getValue().isEmpty()){
                Alert missingFields = new Alert(Alert.AlertType.ERROR);

                missingFields.setTitle("Fields Error");
                missingFields.setHeaderText("You did not fill all the fields!");
                missingFields.setContentText("To complete the order successfully, " +
                        "please provide all the requested informations");

                missingFields.showAndWait();

            } else {
                Alert checkOutAlert = new Alert(Alert.AlertType.CONFIRMATION);

                checkOutAlert.setTitle("Check Out Confirmation");
                checkOutAlert.setHeaderText("Your order will be confirmed.");
                checkOutAlert.setContentText("Are you sure you want to proceed with the check out?");

                Optional<ButtonType> response = checkOutAlert.showAndWait();

                if (response.isPresent() && response.get() == ButtonType.OK) {
                    try {
                        // calculate all parameters needed to checkout
                        String orderID = cartDAO.generateOrderID(currentUserEmail);

                        BigDecimal orderPrice = cartDAO.totalCost(currentUserEmail);


                        DatabaseConnection connection = new DatabaseConnection();
                        connection.openConnection();

                        // select all the books and relative quantities from the user cart
                        String booksInCartQuery = "SELECT book, quantity " +
                                "FROM cart " +
                                "WHERE user = ?";

                        PreparedStatement pstmt1 = connection.conn.prepareStatement(booksInCartQuery);
                        pstmt1.setString(1, currentUserEmail);

                        ResultSet rs1 = pstmt1.executeQuery();

                        Map<String, Integer> bookAndQuantities = new HashMap<>();

                        while(rs1.next()){
                            bookAndQuantities.put(rs1.getString("book"), rs1.getInt("quantity"));
                        }

                        rs1.close();
                        pstmt1.close();

                        connection.closeConnection();


                        ArrayList<Book> booksInCartAttributes = new ArrayList<>();

                        BookDAO bookDAO = new BookDaoImpl();
                        CompositionDAO compositionDAO = new CompositionDaoImpl();

                        for(String bookISBN : bookAndQuantities.keySet()){
                            booksInCartAttributes.add(bookDAO.getBook(bookISBN));

                            compositionDAO.addBookToOrder(bookISBN, orderID, bookAndQuantities.get(bookISBN));

                        }


                        // current date
                        Calendar calendar = Calendar.getInstance();

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String currentDate = formatter.format(calendar.getTime());

                        // perform different action if the user is registered
                        if(!Pattern.matches("NOTREG*", orderID) ){

                            // calculate total libroCard points
                            int totalPoints = 0;

                            for (Book b : booksInCartAttributes){
                                totalPoints += b.getLibroCardPoints();
                            }

                            // add new row in Order table
                            OrderDAO orderDAO = new OrderDaoImpl();

                            Order newOrder = new Order(orderID, currentDate,
                                    OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                                    paymentMethodCB.getValue(), orderPrice, totalPoints,
                                    shippingAddressTF.getText(), currentUserEmail, null);

                            orderDAO.addOrder(newOrder);


                            DatabaseConnection connection1 = new DatabaseConnection();
                            connection1.openConnection();

                            // retrieve user's LibroCard ID
                            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

                            String cardIDQuery = "SELECT cardID " +
                                    "FROM LibroCard " +
                                    "WHERE email = ?";

                            PreparedStatement pstmt2 = connection1.conn.prepareStatement(cardIDQuery);

                            pstmt2.setString(1, currentUserEmail);

                            ResultSet rs2 = pstmt2.executeQuery();

                            String cardID = rs2.getString("cardID");

                            rs2.close();
                            pstmt2.close();

                            connection1.closeConnection();

                            // add points to user's LibroCard
                            libroCardDAO.addPoints(cardID, orderID);

                            DatabaseConnection connection2 = new DatabaseConnection();
                            connection2.openConnection();

                            String clearCartQuery = "DELETE FROM cart " +
                                                    "WHERE user = ?";

                            PreparedStatement pstmt3 = connection2.conn.prepareStatement(clearCartQuery);

                            pstmt3.setString(1, currentUserEmail);

                            pstmt3.executeUpdate();

                            connection2.closeConnection();

                        } else {
                            // add new row in Order table
                            OrderDAO orderDAO = new OrderDaoImpl();

                            Order newOrder = new Order(orderID, currentDate,
                                    OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                                    paymentMethodCB.getValue(), orderPrice, null,
                                    shippingAddressTF.getText(), null, currentUserEmail);

                            orderDAO.addOrder(newOrder);

                            // TODO: clear cart
                        }


                    } catch (SQLException | UserNotInDatabaseException | InvalidStringException |
                            IllegalValueException | NotSameUserException ex) {
                        /*Alert orderFailure = new Alert(Alert.AlertType.ERROR);

                        orderFailure.setTitle("Order Failure");
                        orderFailure.setHeaderText("Something went wrong: ");
                        orderFailure.setContentText("ERROR MESSAGE: " + ex.getMessage());

                        orderFailure.showAndWait();*/
                        ex.printStackTrace();

                    }
                } else {
                    e.consume();
                    checkOutAlert.close();
                }
            }
        });

        gridPane.add(orderConfirmationLabel, 0,0);
        gridPane.add(booksInCartSP, 0,1);
        gridPane.add(paymentMethodGP, 0,2);
        gridPane.add(shippingAddressGP, 0,3);
        gridPane.add(totalCostLabel, 0,4);
        gridPane.add(checkOutButton, 0,5);


        Insets insets = new Insets(0, 0, 20, 20);

        GridPane.setMargin(orderConfirmationLabel, insets);
        GridPane.setMargin(booksInCartSP, insets);
        GridPane.setMargin(paymentMethodGP, insets);
        GridPane.setMargin(shippingAddressGP, insets);
        GridPane.setMargin(totalCostLabel, insets);
        GridPane.setMargin(checkOutButton, insets);

        return gridPane;
    }


}
