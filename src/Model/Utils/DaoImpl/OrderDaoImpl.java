package Model.Utils.DaoImpl;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

        Order order = null;

        while(rs.next()) {
            order = new Order(rs.getString("orderID"),
                    rs.getString("date"),
                    rs.getString("status"),
                    rs.getString("paymentMethod"),
                    rs.getBigDecimal("price"),
                    rs.getInt("points"),
                    rs.getString("shippingAddress"),
                    rs.getString("user"),
                    rs.getInt("isRegistred"));
        }

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return order;

    }

    @Override
    public String getUser(String orderID) throws SQLException{
        String sql = "SELECT user FROM orders WHERE orderID = ?";
        String user;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, orderID);

        ResultSet rs = pstmt.executeQuery();

        user = rs.getString("user");

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
                    rs.getInt("isRegistred")));
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
                    rs.getInt("isRegistred")));
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
                " price, points, shippingAddress, user, isRegistred) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, order.getOrderID());
        pstmt.setString(2, order.getDate());
        pstmt.setString(3, order.getStatus());
        pstmt.setString(4, order.getPaymentMethod());
        pstmt.setBigDecimal(5, order.getPrice());
        if(order.getIsRegistred() == 0){
            pstmt.setObject(6, null);
        } else {
            pstmt.setInt(6, order.getPoints());
        }
        pstmt.setString(7, order.getShippingAddress());
        pstmt.setString(8, order.getUser());
        pstmt.setInt(9, order.getIsRegistred());

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public ArrayList<Order> getOrdersByStatus(OrderStatusEnum status) throws SQLException,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM orders WHERE status = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, status.toString());

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Order> ordersByStatus = new ArrayList<>();

        while(rs.next()){
            ordersByStatus.add(new Order(rs.getString("orderID"),
                    rs.getString("date"),
                    rs.getString("status"),
                    rs.getString("paymentMethod"),
                    rs.getBigDecimal("price"),
                    rs.getInt("points"),
                    rs.getString("shippingAddress"),
                    rs.getString("user"),
                    rs.getInt("isRegistred")));
        }

        pstmt.close();
        rs.close();

        connection.closeConnection();

        return ordersByStatus;
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
