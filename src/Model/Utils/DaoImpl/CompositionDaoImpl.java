package Model.Utils.DaoImpl;

import Model.Utils.DAOs.CompositionDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompositionDaoImpl implements CompositionDAO {
    @Override
    public void addBookToOrder(String ISBN, String orderID, int quantity)
            throws SQLException {

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String newComposition = "INSERT INTO composition(book, order, quantity)" +
                "VALUES (?,?,?)";

        PreparedStatement pstmt = connection.conn.prepareStatement(newComposition);
        pstmt.setString(1, ISBN);
        pstmt.setString(2, orderID);
        pstmt.setInt(3, quantity);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }
}
