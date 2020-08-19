package View.UserView;

import Controller.GeneralLoginController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DAOs.CompositionDAO;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import Model.Utils.DaoImpl.CartDaoImpl;
import Model.Utils.DaoImpl.CompositionDaoImpl;
import Model.Utils.DaoImpl.OrderDaoImpl;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserOrderSummaryPageView {

    public static GridPane buildOrderSummaryPageView(String orderID) {
        GridPane gridPane = new GridPane();

        Label orderConfirmationLabel;
        Label paymentMethodLabel = new Label();
        Label shippingAddressLabel = new Label();
        Label totalCostLabel = new Label();
        ScrollPane orderedBooksSP;

        // page title label
        orderConfirmationLabel = new Label("Order confirmed!");
        orderConfirmationLabel.setFont(new Font("Avenir Next Bold", 50));

        String currentUserEmail = GeneralLoginController.getLoginInstance();

        OrderDAO orderDAO = new OrderDaoImpl();
        CompositionDAO compositionDAO = new CompositionDaoImpl();
        BookDAO bookDAO = new BookDaoImpl();

        try {
            Order currentOrder = orderDAO.getOrder(orderID);
            ArrayList<String> booksISBN = compositionDAO.getBooksInOrder(orderID);
            ArrayList<Book> booksInOrder = new ArrayList<>();

            for(String isbn : booksISBN){
                booksInOrder.add(bookDAO.getBook(isbn));
            }
            orderedBooksSP = CartPageView.buildCartView(booksInOrder);
            orderedBooksSP.setId("booksInCart-scrollpane");
            orderedBooksSP.getStylesheets().add("/CSS/style.css");

            paymentMethodLabel = new Label("Payment method: " + currentOrder.getPaymentMethod());
            paymentMethodLabel.setFont(new Font("Avenir Book", 20));

            shippingAddressLabel = new Label("Shipping address: " + currentOrder.getShippingAddress());
            shippingAddressLabel.setFont(new Font("Avenir Book", 20));

            totalCostLabel = new Label("Total price: $ " + currentOrder.getPrice());
            totalCostLabel.setFont(new Font("Avenir Book", 20));
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            orderedBooksSP = new ScrollPane();
        }

        gridPane.add(orderConfirmationLabel, 0,0);
        gridPane.add(orderedBooksSP, 0,1);
        gridPane.add(paymentMethodLabel, 0,2);
        gridPane.add(shippingAddressLabel, 0,3);
        gridPane.add(totalCostLabel, 0,4);

        Insets insets = new Insets(0, 0, 20, 20);

        GridPane.setMargin(orderConfirmationLabel, insets);
        GridPane.setMargin(orderedBooksSP, insets);
        GridPane.setMargin(paymentMethodLabel, insets);
        GridPane.setMargin(shippingAddressLabel, insets);
        GridPane.setMargin(totalCostLabel, insets);

        return gridPane;
    }
}
