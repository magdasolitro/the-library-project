package Model.Utils.DAOs;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.OrderStatusEnum;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO {
    public Order getOrder(String orderID) throws SQLException,
            InvalidStringException, IllegalValueException ;

    public String getUserEmail(String orderID) throws SQLException;

    // visualizza tutti gli ordini effettuati da tutti gli utenti
    public ArrayList<Order> getAllOrders() throws SQLException,
            InvalidStringException, IllegalValueException ;

    // visualizza tutti gli ordini di un certo utente
    public ArrayList<Order> getOrdersByUser(String email) throws SQLException,
            InvalidStringException, IllegalValueException;

    public void addOrder(Order order)
            throws SQLException;

    public void updateStatus(Order order, OrderStatusEnum newStatus)
            throws SQLException;
}
