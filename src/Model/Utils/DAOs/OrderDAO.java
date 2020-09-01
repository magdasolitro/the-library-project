package Model.Utils.DAOs;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.OrderStatusEnum;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO {
    Order getOrder(String orderID) throws SQLException,
            InvalidStringException, IllegalValueException ;

    String getUser(String orderID) throws SQLException;

    // visualizza tutti gli ordini effettuati da tutti gli utenti
    ArrayList<Order> getAllOrders() throws SQLException,
            InvalidStringException, IllegalValueException ;

    // visualizza tutti gli ordini di un certo utente
    ArrayList<Order> getOrdersByUser(String email) throws SQLException,
            InvalidStringException, IllegalValueException;

    void addOrder(Order order) throws SQLException;

    ArrayList<Order> getOrdersByStatus(OrderStatusEnum status) throws SQLException,
            InvalidStringException, IllegalValueException;

    void updateStatus(Order order, OrderStatusEnum newStatus)
            throws SQLException;
}
