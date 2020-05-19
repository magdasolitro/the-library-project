package Model.Utils.DAOs;

import Model.Book;
import Model.Exceptions.UserNotInDatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CartDAO {

    public void addBookToCart(String ISBN, String email, int quantity) throws SQLException;

    public void increaseQuantity(String ISBN, String email) throws SQLException;

    public void decreaseQuantity(String ISBN, String email) throws SQLException;

    public ArrayList<Book> showCartContent(String email) throws SQLException;

    // returns orderID
    public String checkoutUserReg(String email, String paymentMethod, String shippingAddress)
            throws SQLException, UserNotInDatabaseException;

    public String checkoutUserNotReg(String email, String paymentMethod)
            throws SQLException, UserNotInDatabaseException;
}
