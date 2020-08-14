package Model.Utils.DAOs;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.NotSameUserException;
import Model.Exceptions.UserNotInDatabaseException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CartDAO {

    public void addBookToCart(String ISBN, String email, int quantity)
            throws SQLException;

    public void removeBookFromCart(String ISBN, String email) throws SQLException;

    public BigDecimal totalCost(String email) throws SQLException, InvalidStringException, IllegalValueException;

    public void increaseQuantity(String ISBN, String email) throws SQLException;

    public void decreaseQuantity(String ISBN, String email) throws SQLException;

    public ArrayList<Book> cartContent(String email) throws SQLException,
            InvalidStringException, IllegalValueException;

    // returns orderID
    public String checkout(String email, String paymentMethod, String shippingAddress,
                           String orderID, BigDecimal totalCost) throws SQLException,
            NotSameUserException, InvalidStringException, IllegalValueException;

    public String generateOrderID(String email)
            throws SQLException, UserNotInDatabaseException;
}
