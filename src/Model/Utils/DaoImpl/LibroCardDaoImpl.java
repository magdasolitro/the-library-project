package Model.Utils.DaoImpl;

import Model.Book;
import Model.Exceptions.NotSameUserException;
import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibroCardDaoImpl implements LibroCardDAO {
    @Override
    public void addLibroCard(LibroCard newLibroCard) throws SQLException {

        String sql = "INSERT INTO LibroCard(cardID, issueDate, points, email)" +
                "VALUES(?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newLibroCard.getCardID());
        pstmt.setString(2, newLibroCard.getIssueDate());
        pstmt.setInt(3, newLibroCard.getPoints());
        pstmt.setString(4, newLibroCard.getUser());

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public String getUserEmail(String cardID) throws SQLException{
        String sql = "SELECT email FROM LibroCard WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, cardID);

        ResultSet rs = pstmt.executeQuery();

        String userEmail = rs.getString("email");

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return userEmail;
    }

    @Override
    public LibroCard getLibroCard(String cardID) throws SQLException {
        String sql = "SELECT * FROM LibroCard WHERE cardID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, cardID);

        ResultSet rs = pstmt.executeQuery();

        LibroCard libroCard = new LibroCard(rs.getString("cardID"),
                rs.getString("email"),
                rs.getInt("points"),
                rs.getString("issueDate"));

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return libroCard;
    }

    @Override
    public ArrayList<LibroCard> getAllLibroCards() throws SQLException {
        String sql = "SELECT * " +
                "FROM LibroCard";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        // store the result of the query in a list
        ArrayList<LibroCard> allLibroCards = new ArrayList<>();

        while(rs.next()) {
            allLibroCards.add(new LibroCard(rs.getString("cardID"),
                    rs.getString("issueDate"),
                    rs.getInt("points"),
                    rs.getString("email")));
        }

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return allLibroCards;

    }

    public LibroCard getUserLibroCard(String email) throws SQLException{
        String sql = "SELECT * FROM LibroCard WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();

        LibroCard libroCard = new LibroCard(rs.getString("cardID"),
                rs.getString("email"),
                rs.getInt("points"),
                rs.getString("issueDate"));

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return libroCard;
    }

    @Override
    public void deleteLibroCard(String cardID) throws SQLException {
        String sql = "DELETE FROM LibroCard WHERE cardID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, cardID);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void addPoints(String cardID, String orderID) throws SQLException, NotSameUserException {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        try{
            String sql = "UPDATE LibroCard SET points = points + " +
                    "(SELECT points " +
                    "FROM orders " +
                    "WHERE orderID = ?)" +
                    "WHERE cardID = ?";

            PreparedStatement pstmt = connection.conn.prepareStatement(sql);

            pstmt.setString(1, orderID);
            pstmt.setString(2, cardID);

            pstmt.executeUpdate();

            pstmt.close();

        } catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }

        connection.closeConnection();
    }
}
