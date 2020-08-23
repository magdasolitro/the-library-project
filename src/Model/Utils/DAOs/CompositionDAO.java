package Model.Utils.DAOs;

import Model.Book;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompositionDAO {

    void addBookToOrder(String ISBN, String orderID, int quantity)
            throws SQLException;

    ArrayList<String> getBooksInOrder(String orderID) throws SQLException;
}
