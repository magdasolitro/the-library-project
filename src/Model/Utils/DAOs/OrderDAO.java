package Model.Utils.DAOs;

import Model.Order;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO {
    public Order getOrder(String orderID) throws SQLException;

    // visualizza tutti gli ordini effettuati da tutti gli utenti
    public ArrayList<Order> getAllOrders() throws SQLException;

    // visualizza tutti gli ordini di un certo utente
    public ArrayList<Order> getOrdersByUser(String email) throws SQLException;

    public void addOrder(String orderID, String date, String status, String paymentMethod,
                         float price, int points, String shippingAddress, String user,
                         String user_notReg) throws SQLException;

    public void updateStatus(Order order, String newStatus) throws SQLException;
}
