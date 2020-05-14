package Model.Utils.DAOs;

import Model.Book;
import Model.CartView;
import Model.User;
import Model.UserNotReg;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CartDAO {

    public void addBookToCart(String ISBN, String username, int quantity)
            throws SQLException;

    public void increaseQuantity(String ISBN, String username)
            throws SQLException;

    public void decreaseQuantity(String ISBN, String username)
            throws SQLException;

    public ArrayList<CartView> showCartContent(String username)
            throws SQLException;

    // returns orderID
    public String checkoutUserReg(String email, String paymentMethod)
            throws SQLException;

    public String checkoutUserNotReg(String email, String payment);
}
