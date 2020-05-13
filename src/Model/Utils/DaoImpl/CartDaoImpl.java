package Model.Utils.DaoImpl;

import Model.Book;
import Model.Order;
import Model.User;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
// import java.util.regex.Pattern;

public class CartDaoImpl implements CartDAO {

    @Override
    public void addBookToCart(String ISBN, String username, int quantity) throws SQLException {
        String sql = "INSERT INTO cart(user, book, quantity) VALUES (?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        
        connection.pstmt.setString(1, username);
        connection.pstmt.setString(2, ISBN);
        connection.pstmt.setInt(3, quantity);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void increaseQuantity(String ISBN, String username) throws SQLException {
        String sql = "UPDATE cart SET quantity = quantity + 1";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void decreaseQuantity(String ISBN, String username) throws SQLException {
        String sql = "UPDATE cart SET quantity = quantity - 1";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public ArrayList<Book> showCartContent(String username) throws SQLException {
        String sql = "SELECT ISBN, title, authors, genre, price, " +
                "publishingHouse, publishingYear, discount, quantity " +
                "FROM cart JOIN book ON ISBN = book WHERE user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, username);

        connection.rs = connection.pstmt.executeQuery();

        // store the result of the query in a list
        ArrayList<Book> cartContent = new ArrayList<>();
        /*
        while(connection.rs.next()) {
            cartContent.add(new Book(connection.rs.getString("ISBN"),
                    connection.rs.getString("title"),
                    connection.rs.getString("authors"),
                    connection.rs.getString("genre"),
                    connection.rs.getString("price"),
                    connection.rs.getString("description"),
                    connection.rs.getString("publishingHouse"),
                    connection.rs.getString("publishingYear"),
                    connection.rs.getString("discount")
                    ));
        }
        */
        return cartContent;
    }


    public String checkoutUserReg(String email, String paymentMethod)
            throws SQLException {
        Order newOrder = new Order();
        return null;
    }

    public String checkoutUserNotReg(String email, String payment){
        return null;
    }

}
