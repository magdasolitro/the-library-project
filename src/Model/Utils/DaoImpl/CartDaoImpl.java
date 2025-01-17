package Model.Utils.DaoImpl;

import Model.Book;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.NotSameUserException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.*;
import Model.Utils.DatabaseConnection;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class CartDaoImpl implements CartDAO {
    DatabaseConnection connection;

    @Override
    public void addBookToCart(String ISBN, String email, int quantity) throws SQLException {

        String sql = "INSERT INTO cart(user, book, quantity) VALUES (?,?,?)";

        connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        
        connection.pstmt.setString(1, email);
        connection.pstmt.setString(2, ISBN);
        connection.pstmt.setInt(3, quantity);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void removeBookFromCart(String ISBN, String email) throws SQLException {
        String sql = "DELETE FROM cart WHERE book = ? AND user = ?";

        connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    /**
     * Calculates the total price of all the books in cart, discount included
     * @param email user cart's email
     * @return total cost
     * @throws SQLException
     * @throws InvalidStringException
     * @throws IllegalValueException
     */
    @Override
    public BigDecimal totalCost(String email) throws SQLException, InvalidStringException, IllegalValueException {
        // obtain all books with their prices and discount in user cart
        String sql = "SELECT * " +
                     "FROM book JOIN cart ON book.ISBN = cart.book " +
                     "WHERE user = ?";

        ArrayList<Book> booksInCart = new ArrayList<>();

        connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        while(connection.rs.next()){
            booksInCart.add(new Book(connection.rs.getString("ISBN"),
                    connection.rs.getString("title"),
                    connection.rs.getString("authors"),
                    connection.rs.getString("genre"),
                    connection.rs.getBigDecimal("price"),
                    connection.rs.getString("description"),
                    connection.rs.getString("publishingHouse"),
                    connection.rs.getInt("publishingYear"),
                    connection.rs.getBigDecimal("discount"),
                    connection.rs.getInt("availableCopies"),
                    connection.rs.getInt("libroCardPoints")));
        }

        connection.closeConnection();

        BigDecimal totalCost = new BigDecimal(0);
        BigDecimal currentBookPrice;

        for(Book b : booksInCart){
            currentBookPrice = b.getPrice();

            // apply discount if present
            if(b.getDiscount().compareTo(new BigDecimal(0)) > 0) {
                BigDecimal effectiveBookPrice = currentBookPrice.subtract(currentBookPrice.multiply(b.getDiscount().divide(new BigDecimal(100))));

                // multiply price by quantity
                // method getQuantity() is defined in Book class
                effectiveBookPrice = effectiveBookPrice.multiply(new BigDecimal(b.getQuantity(email)));

                totalCost = totalCost.add(effectiveBookPrice);
            } else {
                currentBookPrice = currentBookPrice.multiply(new BigDecimal(b.getQuantity(email)));

                totalCost = totalCost.add(currentBookPrice);
            }
        }

        return totalCost.setScale(2);
    }

    @Override
    public void increaseQuantity(String ISBN, String email) throws SQLException {
        String sql = "UPDATE cart SET quantity = quantity + 1 " +
                     "WHERE book = ?" +
                     "AND user = ?";

        connection = new DatabaseConnection();
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

        connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public ArrayList<Book> cartContent(String email) throws SQLException,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT ISBN, title, authors, genre, price, description," +
                        "publishingHouse, publishingYear, discount, availableCopies, " +
                        "libroCardPoints, quantity " +
                     "FROM cart " +
                     "   JOIN book ON book.ISBN = cart.book " +
                     "WHERE user = ?";

        connection = new DatabaseConnection();
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
                    connection.rs.getBigDecimal("price"),
                    connection.rs.getString("description"),
                    connection.rs.getString("publishingHouse"),
                    connection.rs.getInt("publishingYear"),
                    connection.rs.getBigDecimal("discount"),
                    connection.rs.getInt("availableCopies"),
                    connection.rs.getInt("libroCardPoints")));
        }

        connection.closeConnection();

        return cartContent;
    }

    public String checkout(String email, String paymentMethod,
                           String shippingAddress)
            throws SQLException, UserNotInDatabaseException, NotSameUserException,
            InvalidStringException, IllegalValueException {

        connection = new DatabaseConnection();
        connection.openConnection();

        String orderID = generateOrderID(connection, email);

        // select all the books and relative quantities from the user cart
        String booksInCartQuery = "SELECT book, quantity " +
                                  "FROM cart " +
                                  "WHERE user = ?";

        connection.pstmt = connection.conn.prepareStatement(booksInCartQuery);
        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        ArrayList<Book> booksInCartAttributes = new ArrayList<>();
        BookDAO bookDao = new BookDaoImpl();

        // for each book in cart, add a new row in table Composition
        while(connection.rs.next()){
            String currentBook = connection.rs.getString("book");
            int currentQuantity = connection.rs.getInt("quantity");

            booksInCartAttributes.add(bookDao.getBook(currentBook));

            CompositionDAO newComposition = new CompositionDaoImpl();
            newComposition.addBookToOrder(currentBook, orderID, currentQuantity);
        }

        BigDecimal totalCost = totalCost(email);

        if(!Pattern.matches("NOTREG*", orderID) ){
            int totalPoints = getOrderPoints(connection, booksInCartAttributes);

            // add new row in Order table
            OrderDAO orderDAO = new OrderDaoImpl();

            orderDAO.addOrder(orderID, getCurrentDate(),
                    OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                    paymentMethod, totalCost, totalPoints, shippingAddress,
                    email, null);

            // add points to user's LibroCard
            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

            String cardIDQuery = "SELECT cardID " +
                                 "FROM LibroCard " +
                                 "WHERE email = ?";

            connection.pstmt = connection.conn.prepareStatement(cardIDQuery);

            connection.pstmt.setString(1, email);

            connection.rs = connection.pstmt.executeQuery();

            libroCardDAO.addPoints(connection.rs.getString("cardID"), orderID);

        } else {
            // add new row in Order table
            OrderDAO orderDAO = new OrderDaoImpl();

            orderDAO.addOrder(orderID, getCurrentDate(),
                    OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                    paymentMethod, totalCost, null, shippingAddress,
                    null, email);
        }

        connection.closeConnection();

        return orderID;
    }

    /*
       Creates a unique alphanumeric string that will be user to identify the
       order. It is generated using: first 3 letters of the surname + first
       3 letter of the name + integer timestamp (es. the orderID is generated
       on 15 May 2020, at 09:26:22 --> integer timestamp = 15052020092622
    */
    private String generateOrderID(DatabaseConnection connection, String email)
            throws SQLException, UserNotInDatabaseException{

        String name, surname;
        boolean isRegistred = true;

        String userNameSurnameQuery = "SELECT name, surname " +
                                      "FROM user JOIN cart ON user.email = cart.user " +
                                      "WHERE user.email = ?";

        connection.pstmt = connection.conn.prepareStatement(userNameSurnameQuery);
        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        // if ResultSet is empty, try to find user in userNotReg table
        if(!connection.rs.next()){
            isRegistred = false;

            userNameSurnameQuery = "SELECT name, surname " +
                                   "FROM userNotReg JOIN cart ON userNotReg.email = cart.user " +
                                   "WHERE userNotReg.email = ?";

            connection.pstmt = connection.conn.prepareStatement(userNameSurnameQuery);
            connection.pstmt.setString(1, email);

            connection.rs = connection.pstmt.executeQuery();

            if(!connection.rs.next()){
                throw new UserNotInDatabaseException();
            }
        }

        name = connection.rs.getString("name");
        surname = connection.rs.getString("surname");

        connection.closeConnection();

        String firstToken = name.substring(0,3).toUpperCase() +
                surname.substring(0,3).toUpperCase();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String dateTimeToken  = dtf.format(now);

        if(isRegistred) {
            return firstToken + dateTimeToken;
        } else{
            return "NOTREG" + firstToken + dateTimeToken;
        }
    }


    private int getOrderPoints(DatabaseConnection connection, ArrayList<Book> bookArray) {
        int totalPoints = 0;

        for (Book b : bookArray){
            totalPoints += b.getLibroCardPoints();
        }

        return totalPoints;
    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }
}
