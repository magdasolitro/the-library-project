package Model.Utils.DAOs;

import Model.Book;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompositionDAO {

    public void addBookToOrder(String ISBN, String orderID, int quantity)
            throws SQLException;

    public ArrayList<String> getBooksInOrder(String orderID) throws SQLException;
}
