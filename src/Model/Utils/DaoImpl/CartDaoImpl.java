package Model.Utils.DaoImpl;

import Model.Book;
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
    public void removeBookFromCart(String ISBN, String username) throws SQLException {
        String sql = "DELETE FROM cart WHERE book = ? AND user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, username);

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



    @Override
    public void checkout(User user, String paymentMethod) throws SQLException {
        /*
        String sql = "INSERT INTO order(orderID, date, status, paymentMethod," +
                "price, points, shippingAddress, user, user_notReg) VALUES(?," +
                "?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.connection.prepareStatement(sql);

        // test if the user is

        if(Pattern.matches("^NOTREG", user.getUsername())){
            connection.pstmt.setString(8, null);
            connection.pstmt.setString(9, user.getUsername());
        } else {
            connection.pstmt.setString(8, user.getUsername());
            connection.pstmt.setString(9, null);
        }

        // generate an orderID in some way...

        connection.pstmt.setString(1, orderID);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(calendar.getTime());

        connection.pstmt.setString(2, currentDate);

        connection.pstmt.setString(3, ...);

        connection.pstmt.setString(4, paymentMethod);

        connection.pstmt.setString(5, ...);

        connection.pstmt.setString(6, ...);

        connection.pstmt.setString(7, ...);
    */
    }

}
