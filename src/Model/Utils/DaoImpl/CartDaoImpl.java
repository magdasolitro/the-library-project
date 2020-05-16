package Model.Utils.DaoImpl;

import Model.Book;

import Model.Composition;
import Model.Order;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
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

    // da fare: calcolare costo totale dell'ordine
    //          calcolare punti
    //
    public String checkoutUserReg(String email, String paymentMethod,
                                  String shippingAddress) throws SQLException {

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
            String currentBook = connection.rs.getString("book");
            int currentQuantity = connection.rs.getInt("quantity");

            compositions.add(new Composition(currentBook, orderID, currentQuantity));

            String newComposition = "INSERT INTO composition(book, order, quantity)" +
                    "VALUES (?,?,?)";

            connection.pstmt = connection.conn.prepareStatement(newComposition);
            connection.pstmt.setString(1, currentBook);
            connection.pstmt.setString(2, orderID);
            connection.pstmt.setInt(3, currentQuantity);

            connection.pstmt.executeUpdate();
        }

        // create a new order and insert it in the table
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String currentDate  = dtf.format(LocalDateTime.now());

        /* Order newOrder = new Order(orderID, currentDate,
                OrderStatus.ORDER_REQUEST_RECEIVED, paymentMethod, 0, 0,
                shippingAddress, email, null);*/

        return orderID;
    }

    public String checkoutUserNotReg(String email, String paymentMethod)
            throws SQLException {
        return null;
    }


    /*
        Creates a unique alphanumeric string that will be user to identify the
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

    // costo totale dell'ordine: prezzo libro * quantità
    // DA MODIFICARE!!
    private float getOrderPrice(String email) throws SQLException{
        // seleziono i prezzi dei libri
        String bookPrices = "SELECT book, price FROM book JOIN cart ON book.ISBN = cart.book " +
                "WHERE cart.user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(bookPrices);
        connection.pstmt.setString(1, email);
        connection.rs = connection.pstmt.executeQuery();

        float totalPrice = 0;

        while(connection.rs.next()){
            float currentBookPrice = connection.rs.getFloat("price");

            String bookQuantity = "SELECT quantity FROM cart WHERE book = ? AND" +
                    " user = ?";
            connection.pstmt = connection.conn.prepareStatement(bookQuantity);
            connection.pstmt.setString(1, connection.rs.getString("book"));
            connection.pstmt.setString(2, email);

            connection.pstmt.executeQuery();
            currentBookPrice *= connection.rs.getInt("quantity");

            totalPrice += currentBookPrice;
        }

    }

}
