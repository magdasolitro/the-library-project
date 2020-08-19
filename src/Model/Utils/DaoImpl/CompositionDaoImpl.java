package Model.Utils.DaoImpl;

import Model.Book;
import Model.Utils.DAOs.CompositionDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompositionDaoImpl implements CompositionDAO {
    @Override
    public void addBookToOrder(String ISBN, String orderID, int quantity)
            throws SQLException {

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String newComposition = "INSERT INTO composition(book, orderID, quantity)" +
                "VALUES (?,?,?)";

        PreparedStatement pstmt = connection.conn.prepareStatement(newComposition);
        pstmt.setString(1, ISBN);
        pstmt.setString(2, orderID);
        pstmt.setInt(3, quantity);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public ArrayList<String> getBooksInOrder(String orderID) throws SQLException {
        String sql = "SELECT book FROM composition WHERE orderID = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, orderID);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<String> booksInOrder = new ArrayList<>();

        while(rs.next()){
            booksInOrder.add(rs.getString("book"));
        }

        pstmt.close();
        rs.close();

        connection.closeConnection();

        return booksInOrder;

    }
}
