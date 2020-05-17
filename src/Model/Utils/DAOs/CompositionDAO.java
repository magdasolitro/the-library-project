package Model.Utils.DAOs;

import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public interface CompositionDAO {

    public void addBookToOrder(String ISBN, String orderID, int quantity)
            throws SQLException;
}
