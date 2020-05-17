package Model.Utils.DaoImpl;

import Model.Order;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDAO {

    @Override
    public Order getOrder(String orderID) throws SQLException {
        String sql = "SELECT * FROM order WHERE orderID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, orderID);

        connection.rs = connection.pstmt.executeQuery();

        Order order = new Order(connection.rs.getString("orderID"),
                connection.rs.getString("date"),
                connection.rs.getString("status"),
                connection.rs.getString("paymentMethod"),
                connection.rs.getBigDecimal("price"),
                connection.rs.getInt("points"),
                connection.rs.getString("shippingAddress"),
                connection.rs.getString("user"),
                connection.rs.getString("userNotReg"));

        connection.closeConnection();

        return order;
    }

    @Override
    public ArrayList<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM order";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        ArrayList<Order> ordersList = new ArrayList<>();

        while(connection.rs.next()){
            ordersList.add(new Order(connection.rs.getString("orderID"),
                    connection.rs.getString("date"),
                    connection.rs.getString("status"),
                    connection.rs.getString("paymentMethod"),
                    connection.rs.getBigDecimal("price"),
                    connection.rs.getInt("points"),
                    connection.rs.getString("shippingAddress"),
                    connection.rs.getString("user"),
                    connection.rs.getString("user_notReg")));
        }

        return ordersList;
    }

    @Override
    public ArrayList<Order> getOrdersByUser(String email) throws SQLException {
        String sql = "SELECT * FROM order WHERE user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        ArrayList<Order> ordersListPerUser = new ArrayList<>();

        while(connection.rs.next()){
            ordersListPerUser.add(new Order(connection.rs.getString("orderID"),
                    connection.rs.getString("date"),
                    connection.rs.getString("status"),
                    connection.rs.getString("paymentMethod"),
                    connection.rs.getBigDecimal("price"),
                    connection.rs.getInt("points"),
                    connection.rs.getString("shippingAddress"),
                    connection.rs.getString("user"),
                    connection.rs.getString("user_notReg")));
        }

        return ordersListPerUser;
    }

    @Override
    public void addOrder(String orderID, String date, String status, String paymentMethod,
                         BigDecimal price, int points, String shippingAddress, String user,
                         String user_notReg) throws SQLException {

        String sql = "INSERT INTO order(orderID, date, status, paymentMethod," +
                " price, points, shippingAddress, user, user_notReg) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, orderID);
        connection.pstmt.setString(2, date);
        connection.pstmt.setString(3, status);
        connection.pstmt.setString(4, paymentMethod);
        connection.pstmt.setBigDecimal(5, price);
        connection.pstmt.setInt(6, points);
        connection.pstmt.setString(7, shippingAddress);
        connection.pstmt.setString(8, user);
        connection.pstmt.setString(9, user_notReg);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void updateStatus(Order order, String newStatus) throws SQLException {
        String sql = "UPDATE order SET status = ? WHERE orderID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newStatus);
        connection.pstmt.setString(2, order.getOrderID());

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
