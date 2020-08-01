package Model.Utils.DaoImpl;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OrderDaoImpl implements OrderDAO {

    @Override
    public Order getOrder(String orderID) throws SQLException ,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders WHERE orderID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, orderID);

        ResultSet rs = pstmt.executeQuery();

        Order order = new Order(rs.getString("orderID"),
                rs.getString("date"),
                rs.getString("status"),
                rs.getString("paymentMethod"),
                rs.getBigDecimal("price"),
                rs.getInt("points"),
                rs.getString("shippingAddress"),
                rs.getString("user"),
                rs.getString("userNotReg"));

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return order;
    }

    @Override
    public String getUserEmail(String orderID) throws SQLException{
        String sql = "SELECT user, userNotReg FROM orders WHERE orderID = ?";
        String user;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, orderID);

        ResultSet rs = pstmt.executeQuery();

        if(rs.getString("user") != null){
            user = rs.getString("user");
            connection.closeConnection();
            return user;
        }

        user = rs.getString("userNotReg");

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return user;

    }

    @Override
    public ArrayList<Order> getAllOrders() throws SQLException ,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Order> ordersList = new ArrayList<>();

        while(rs.next()){
            ordersList.add(new Order(rs.getString("orderID"),
                    rs.getString("date"),
                    rs.getString("status"),
                    rs.getString("paymentMethod"),
                    rs.getBigDecimal("price"),
                    rs.getInt("points"),
                    rs.getString("shippingAddress"),
                    rs.getString("user"),
                    rs.getString("user_notReg")));
        }

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return ordersList;
    }

    @Override
    public ArrayList<Order> getOrdersByUser(String email) throws SQLException,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders WHERE user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Order> ordersListPerUser = new ArrayList<>();

        while(rs.next()){
            ordersListPerUser.add(new Order(rs.getString("orderID"),
                    rs.getString("date"),
                    rs.getString("status"),
                    rs.getString("paymentMethod"),
                    rs.getBigDecimal("price"),
                    rs.getInt("points"),
                    rs.getString("shippingAddress"),
                    rs.getString("user"),
                    rs.getString("user_notReg")));
        }

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return ordersListPerUser;
    }

    @Override
    public void addOrder(Order order)
            throws SQLException {

        String sql = "INSERT INTO orders(orderID, date, status, paymentMethod," +
                " price, points, shippingAddress, user, userNotReg) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, order.getOrderID());
        pstmt.setString(2, order.getDate());
        pstmt.setString(3, order.getStatus());
        pstmt.setString(4, order.getPaymentMethod());
        pstmt.setBigDecimal(5, order.getPrice());
        pstmt.setInt(6, order.getPoints());
        pstmt.setString(7, order.getShippingAddress());

        // STABILIRE CODICE UTENTE NON REGISTRATO!! modificare la regex di conseguenza
        if(!Pattern.matches("NOTREG[0-9]", order.getUser())){
            pstmt.setString(8, order.getUser());
            pstmt.setString(9, null);
        } else {
            pstmt.setString(8, null);
            pstmt.setString(9, order.getUser());
        }

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void updateStatus(Order order, OrderStatusEnum newStatus) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE orderID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newStatus.toString());
        pstmt.setString(2, order.getOrderID());

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }
}
