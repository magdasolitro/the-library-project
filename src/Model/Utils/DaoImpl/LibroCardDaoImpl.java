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
    public void addLibroCard(LibroCard newLibroCard) throws SQLException {

        String sql = "INSERT INTO LibroCard(cardID, issueDate, points, user)" +
                "VALUES(?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();


        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newLibroCard.getCardID());
        connection.pstmt.setString(2, newLibroCard.getIssueDate());
        connection.pstmt.setInt(3, newLibroCard.getPoints());
        connection.pstmt.setString(4, newLibroCard.getUser());

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public LibroCard getLibroCard(String cardID) throws SQLException {
        String sql = "SELECT * FROM LibroCard WHERE cardID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, cardID);

        connection.rs = connection.pstmt.executeQuery();

        LibroCard libroCard = new LibroCard(connection.rs.getString("cardID"),
                connection.rs.getString("user"),
                connection.rs.getInt("points"),
                connection.rs.getString("issueDate"));

        connection.closeConnection();

        return libroCard;
    }

    public LibroCard getUserLibroCard(String email) throws SQLException{
        String sql = "SELECT * FROM LibroCard WHERE user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);

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
        String sql = "DELETE FROM LibroCard WHERE cardID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, cardID);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void addPoints(String cardID, String orderID) throws SQLException {
        // verifico che la carta e l'ordine siano associate allo stesso utente

        /*
        if(libroCard.getUser().equals(order.getUser())) {

        }
        */

        String sql = "UPDATE LibroCard SET points = points + " +
                "(SELECT o.points " +
                "FROM orders o " +
                "WHERE o.orderID = ?)" +
                "WHERE cardID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, orderID);
        connection.pstmt.setString(2, cardID);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
