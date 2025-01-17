package Model.Utils.DaoImpl;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDAO {

    @Override
    public Order getOrder(String orderID) throws SQLException ,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders WHERE orderID = ?";

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
    public String getUserEmail(String orderID) throws SQLException{
        String sql = "SELECT user, userNotReg FROM orders WHERE orderID = ?";
        String user;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, orderID);

        connection.rs = connection.pstmt.executeQuery();

        if(connection.rs.getString("user") != null){
            user = connection.rs.getString("user");
            connection.closeConnection();
            return user;
        }

        user = connection.rs.getString("userNotReg");
        connection.closeConnection();

        return user;

    }

    @Override
    public ArrayList<Order> getAllOrders() throws SQLException ,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders";

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

        connection.closeConnection();

        return ordersList;
    }

    @Override
    public ArrayList<Order> getOrdersByUser(String email) throws SQLException,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders WHERE user = ?";

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

        connection.closeConnection();

        return ordersListPerUser;
    }

    @Override
    public void addOrder(String orderID, String date, String status,
                         String paymentMethod, BigDecimal price, Integer points,
                         String shippingAddress, String user, String userNotReg)
            throws SQLException {

        String sql = "INSERT INTO orders(orderID, date, status, paymentMethod," +
                " price, points, shippingAddress, user, userNotReg) " +
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
        connection.pstmt.setString(9, userNotReg);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void updateStatus(Order order, OrderStatusEnum newStatus) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE orderID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newStatus.toString());
        connection.pstmt.setString(2, order.getOrderID());

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
