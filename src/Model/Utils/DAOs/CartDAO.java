package Model.Utils.DAOs;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CartDAO {

    void addBookToCart(String ISBN, String email, int quantity)
            throws SQLException;

    void removeBookFromCart(String ISBN, String email) throws SQLException;

    BigDecimal totalCost(String email) throws SQLException, InvalidStringException, IllegalValueException;

    ArrayList<Book> cartContent(String email) throws SQLException,
            InvalidStringException, IllegalValueException;

    String generateOrderID(String email)
            throws SQLException, UserNotInDatabaseException;
}
