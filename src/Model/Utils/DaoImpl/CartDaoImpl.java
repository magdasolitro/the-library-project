package Model.Utils.DaoImpl;

import Model.Book;

import Model.Composition;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DatabaseConnection;

import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CartDaoImpl implements CartDAO {

    @Override
    public void addBookToCart(String ISBN, String email, int quantity) throws SQLException {
        String sql = "INSERT INTO cart(user, book, quantity) VALUES (?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        
        connection.pstmt.setString(1, email);
        connection.pstmt.setString(2, ISBN);
        connection.pstmt.setInt(3, quantity);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void increaseQuantity(String ISBN, String email) throws SQLException {
        String sql = "UPDATE cart SET quantity = quantity + 1 WHERE book = ?" +
                "AND user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void decreaseQuantity(String ISBN, String email) throws SQLException {
        String sql = "UPDATE cart SET quantity = quantity - 1 WHERE book = ?" +
                "AND user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public ArrayList<Book> showCartContent(String email) throws SQLException {
        String sql = "SELECT ISBN, title, authors, genre, price, description," +
                "publishingHouse, publishingYear, discount, availableCopies," +
                "quantity FROM cart JOIN book ON ISBN = book WHERE user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        // store the result of the query in a list
        ArrayList<Book> cartContent = new ArrayList<>();

        while(connection.rs.next()) {
            cartContent.add(new Book(connection.rs.getString("ISBN"),
                    connection.rs.getString("title"),
                    connection.rs.getString("authors"),
                    connection.rs.getString("genre"),
                    connection.rs.getFloat("price"),
                    connection.rs.getString("description"),
                    connection.rs.getString("publishingHouse"),
                    connection.rs.getInt("publishingYear"),
                    connection.rs.getFloat("discount"),
                    connection.rs.getInt("availableCopies")));
        }

        return cartContent;
    }

    public String checkoutUserReg(String email) throws SQLException {

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();


        // obtain user's name and surname to create the orderID
        String userNameSurname = "SELECT name, surname FROM user INNER JOIN cart " +
                "ON user.email = cart.user WHERE user.email = ?";

        connection.pstmt = connection.conn.prepareStatement(userNameSurname);
        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        String orderID = generateOrderID(connection.rs.getString("name"),
                connection.rs.getString("surname"));



        // select all the books and relative quantities from the user cart
        String booksInCart = "SELECT book, quantity FROM cart WHERE user = ?";

        connection.pstmt = connection.conn.prepareStatement(booksInCart);
        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        // create an ArrayList of Compositions to store all the books that
        // compose the order
        ArrayList<Composition> compositions = new ArrayList<>();

        while(connection.rs.next()){
            compositions.add(new Composition(connection.rs.getString("book"),
                    orderID, connection.rs.getInt("quantity")));
            // + aggiungi riga a tabella composition
        }

        // chiedo all'utente: metodo di pagamento, indirizzo di spedizione
        // creo oggetto ordine
        // inserisco nella tabella order il nuovo ordine

        return null;
    }

    public String checkoutUserNotReg(String email) throws SQLException {
        return null;
    }


    /* Creates a unique alphanumeric string that will be user to identify the
       order. It is generated using: first 3 letters of the surname + first
       3 letter of the name + integer timestamp (es. the orderID is generated
       on 15 May 2020, at 09:26:22 --> integer timestamp = 15052020092622
    */
    private String generateOrderID(String name, String surname){

        String firstToken = name.substring(0,3).toUpperCase() +
                            surname.substring(0,3).toUpperCase();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String dateTimeToken  = dtf.format(now);

        return firstToken + dateTimeToken;
    }

}
