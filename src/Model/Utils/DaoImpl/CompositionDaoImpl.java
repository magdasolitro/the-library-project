package Model.Utils.DaoImpl;

import Model.Utils.DAOs.CompositionDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public class CompositionDaoImpl implements CompositionDAO {
    @Override
    public void addBookToOrder(String ISBN, String orderID, int quantity)
            throws SQLException {

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String newComposition = "INSERT INTO composition(book, order, quantity)" +
                "VALUES (?,?,?)";

        connection.pstmt = connection.conn.prepareStatement(newComposition);
        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, orderID);
        connection.pstmt.setInt(3, quantity);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
