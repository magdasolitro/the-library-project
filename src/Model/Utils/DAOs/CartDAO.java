package Model.Utils.DAOs;

import Model.Book;
import Model.User;
import Model.UserNotReg;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CartDAO {

    public void addBookToCart(String ISBN, String username, int quantity)
            throws SQLException;

    public void removeBookFromCart(String ISBN, String username)
            throws SQLException;

    public void increaseQuantity(String ISBN, String username)
            throws SQLException;

    public void decreaseQuantity(String ISBN, String username)
            throws SQLException;

    public ArrayList<Book> showCartContent(String username)
            throws SQLException;

    // prima di utilizzare il metodo checkout, l'utente deve specificare
    // metodo di pagamento e indirizzo di spedizione --> attenzione! l'utente
    // può specificare un indirizzo di spedizione diverso da quello di
    // residenza SOLO SE E' REGISTRATO!
    public void checkout(User user, String paymentMethod )
            throws SQLException;
}
