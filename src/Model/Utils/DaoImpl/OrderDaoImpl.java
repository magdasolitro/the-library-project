package Model.Utils.DaoImpl;

import Model.Order;
import Model.User;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDAO {

    @Override
    public Order getOrder(String orderID) throws SQLException {
        String sql = "SELECT * FROM order WHERE orderID = " + orderID;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        Order order = new Order(connection.rs.getString("orderID"),
                connection.rs.getString("date"),
                connection.rs.getString("status"),
                connection.rs.getString("paymentMethod"),
                connection.rs.getFloat("price"),
                connection.rs.getInt("points"),
                connection.rs.getString("shippingAddress"),
                connection.rs.getString("user"),
                connection.rs.getString("userNotReg"));

        connection.closeConnection();

        return order;
    }

    @Override
    public ArrayList<Order> getAllOrders() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Order> getOrdersByUser(User user) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Order> getOrdersByStatus(String status) throws SQLException {
        return null;
    }

    @Override
    public void addOrder(String orderID, String date, String status, String paymentMethod, String price, String points, String shippingAddress, String user, String user_notReg) throws SQLException {

    }

    @Override
    public void updateStatus(Order order, String newStatus) throws SQLException {

    }
}
