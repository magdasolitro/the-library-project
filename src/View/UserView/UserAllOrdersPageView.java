package View.UserView;

import Controller.GeneralLoginController;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.OrderDaoImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserAllOrdersPageView {

    public static ScrollPane buildAllOrdersView(){
        String currentUser = GeneralLoginController.getLoginInstance();

        OrderDAO orderDAO = new OrderDaoImpl();

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefHeight(600);
        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane ordersGP = new GridPane();

        Label myOrdersLabel;

        myOrdersLabel = new Label("My Orders");
        myOrdersLabel.setFont(new Font("Avenir Next Bold", 70));

        ordersGP.add(myOrdersLabel, 0,0);

        try {
            ArrayList<Order> allOrders = orderDAO.getOrdersByUser(currentUser);

            int i = 1;

            for(Order o : allOrders){
                GridPane singleOrderGP = singleOrderView(o, 220, 480);
                ordersGP.add(singleOrderGP, 0, i);
                i += 2;
            }

            scrollPane.setContent(ordersGP);
            scrollPane.setPadding(new Insets(20, 0, 20, 40));

            ordersGP.setVgap(40);
            ordersGP.setId("mainpage-gridpane");
            ordersGP.getStylesheets().add("/CSS/style.css");
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }

        scrollPane.setId("ordersummary-scrollpane");
        scrollPane.getStylesheets().add("/CSS/style.css");

        return scrollPane;
    }


    public static GridPane singleOrderView(Order order, int col1width, int col2width){
        GridPane ordersGridPane = new GridPane();

        Label orderIDLabel;
        Label dateLabel;
        Label statusLabel;
        Label paymentMethodLabel;
        Label priceLabel;
        Label pointsLabel;
        Label shippingAddressLabel;

        Label orderID;
        Label date;
        Label status;
        Label paymentMethod;
        Label price;
        Label points;
        Label shippingAddress;

        Font labelFont = new Font("Avenir Next Bold", 20);
        Font stdFont = new Font("Avenir Book", 20);

        orderIDLabel = new Label("OrderID:");
        orderIDLabel.setFont(labelFont);

        orderID = new Label(order.getOrderID());
        orderID.setFont(stdFont);

        dateLabel = new Label("Date:");
        dateLabel.setFont(labelFont);

        date = new Label(order.getDate());
        date.setFont(stdFont);

        statusLabel = new Label("Status:");
        statusLabel.setFont(labelFont);

        status = new Label(order.getStatus());
        status.setFont(stdFont);

        paymentMethodLabel = new Label("Payment method:");
        paymentMethodLabel.setFont(labelFont);

        paymentMethod = new Label(order.getPaymentMethod());
        paymentMethod.setFont(stdFont);

        priceLabel = new Label("Price:");
        priceLabel.setFont(labelFont);

        price = new Label("$ " + order.getPrice().setScale(2, RoundingMode.FLOOR));
        price.setFont(stdFont);

        pointsLabel = new Label("LibroCard points:");
        pointsLabel.setFont(labelFont);

        shippingAddressLabel = new Label("Shipping address:");
        shippingAddressLabel.setFont(labelFont);

        shippingAddress = new Label(order.getShippingAddress());
        shippingAddress.setFont(stdFont);

        if(order.getIsRegistred() == 1) {
            points = new Label("" + order.getPoints());
            points.setFont(stdFont);

            ordersGridPane.add(pointsLabel, 0,5);
            ordersGridPane.add(shippingAddressLabel, 0,6);

            ordersGridPane.add(points, 1,5);
            ordersGridPane.add(shippingAddress, 1,6);
        } else {
            ordersGridPane.add(shippingAddressLabel, 0,5);

            ordersGridPane.add(shippingAddress, 1,5);
        }

        ordersGridPane.add(orderIDLabel, 0 ,0);
        ordersGridPane.add(dateLabel, 0,1);
        ordersGridPane.add(statusLabel, 0, 2);
        ordersGridPane.add(paymentMethodLabel, 0, 3);
        ordersGridPane.add(priceLabel, 0, 4);

        ordersGridPane.add(orderID, 1 ,0);
        ordersGridPane.add(date, 1,1);
        ordersGridPane.add(status, 1, 2);
        ordersGridPane.add(paymentMethod, 1, 3);
        ordersGridPane.add(price, 1, 4);

        ordersGridPane.setVgap(10);
        ordersGridPane.setHgap(40);

        ordersGridPane.getColumnConstraints().addAll(new ColumnConstraints(col1width), new ColumnConstraints(col2width));

        ordersGridPane.setId("singleorder-gridpane");
        ordersGridPane.getStylesheets().add("/CSS/style.css");

        return ordersGridPane;
    }
}
