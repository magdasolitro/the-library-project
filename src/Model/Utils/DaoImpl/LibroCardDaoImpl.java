package Model.Utils.DaoImpl;

import Model.Exceptions.NotSameUserException;
import Model.LibroCard;
import Model.Order;
import Model.User;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public class LibroCardDaoImpl implements LibroCardDAO {
    @Override
    public void addLibroCard(String cardID, String email, String issueDate)
            throws SQLException {

        String sql = "INSERT INTO LibroCard(cardID, issueDate, points, user)" +
                "VALUES(?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, cardID);
        connection.pstmt.setString(2, issueDate);
        connection.pstmt.setInt(3, 0);
        connection.pstmt.setString(4, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public LibroCard getLibroCard(String cardID) throws SQLException {
        String sql = "SELECT * FROM LibroCard WHERE cardID = " + cardID;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        LibroCard libroCard = new LibroCard(connection.rs.getString("cardID"),
                connection.rs.getString("user"),
                connection.rs.getInt("points"),
                connection.rs.getString("issueDate"));

        connection.closeConnection();

        return libroCard;
    }

    public LibroCard getLibroCard(User user) throws SQLException{
        String sql = "SELECT * FROM LibroCard WHERE user = "
                + user.getEmail();

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        LibroCard libroCard = new LibroCard(connection.rs.getString("cardID"),
                connection.rs.getString("user"),
                connection.rs.getInt("points"),
                connection.rs.getString("issueDate"));

        connection.closeConnection();

        return libroCard;
    }

    @Override
    public void deleteLibroCard(String cardID) throws SQLException {
        String sql = "DELETE FROM LibroCard WHERE cardID = " + cardID;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void addPoints(LibroCard libroCard, Order order) throws SQLException {
        // verifico che la carta e l'ordine siano associate allo stesso utente
        /*
        if(libroCard.getUser().equals(order.getUser())) {
            System.out.println("An error occurred: " + nsu.getMessage());
        }
         */

        String sql = "UPDATE LibroCard SET points = points + " +
                "(SELECT o.points " +
                "FROM order o " +
                "WHERE o.orderID = ?)" +
                "WHERE cardID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        // connection.pstmt.setString(1, order.getOrderID());
        // connection.pstmt.setString(2, libroCard.getCardID());

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
