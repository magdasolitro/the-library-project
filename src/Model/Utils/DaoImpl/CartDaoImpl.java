package Model.Utils.DaoImpl;

import Model.Book;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.NotSameUserException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.*;
import Model.Utils.DatabaseConnection;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class CartDaoImpl implements CartDAO {

    @Override
    public void addBookToCart(String ISBN, String email, int quantity) throws SQLException {

        String sql = "INSERT INTO cart(user, book, quantity) VALUES (?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        
        pstmt.setString(1, email);
        pstmt.setString(2, ISBN);
        pstmt.setInt(3, quantity);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void removeBookFromCart(String ISBN, String email) throws SQLException {
        String sql = "DELETE FROM cart WHERE book = ? AND user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, ISBN);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

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

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        ArrayList<Book> booksInCart = new ArrayList<>();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            booksInCart.add(new Book(rs.getString("ISBN"),
                    rs.getString("title"),
                    rs.getString("authors"),
                    rs.getString("genre"),
                    rs.getBigDecimal("price"),
                    rs.getString("description"),
                    rs.getString("publishingHouse"),
                    rs.getInt("publishingYear"),
                    rs.getBigDecimal("discount"),
                    rs.getInt("availableCopies"),
                    rs.getInt("libroCardPoints")));
        }

        rs.close();
        pstmt.close();

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

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, ISBN);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void decreaseQuantity(String ISBN, String email) throws SQLException {
        String sql = "UPDATE cart SET quantity = quantity - 1 WHERE book = ?" +
                     "AND user = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, ISBN);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

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

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();

        // store the result of the query in a list
        ArrayList<Book> cartContent = new ArrayList<>();

        while(rs.next()) {
            cartContent.add(new Book(rs.getString("ISBN"),
                    rs.getString("title"),
                    rs.getString("authors"),
                    rs.getString("genre"),
                    rs.getBigDecimal("price"),
                    rs.getString("description"),
                    rs.getString("publishingHouse"),
                    rs.getInt("publishingYear"),
                    rs.getBigDecimal("discount"),
                    rs.getInt("availableCopies"),
                    rs.getInt("libroCardPoints")));
        }

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return cartContent;
    }

    public String checkout(String email, String paymentMethod,
                           String shippingAddress)
            throws SQLException, UserNotInDatabaseException, NotSameUserException,
            InvalidStringException, IllegalValueException {

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String orderID = generateOrderID(connection, email);

        // select all the books and relative quantities from the user cart
        String booksInCartQuery = "SELECT book, quantity " +
                                  "FROM cart " +
                                  "WHERE user = ?";

        PreparedStatement pstmt1 = connection.conn.prepareStatement(booksInCartQuery);
        pstmt1.setString(1, email);

        ResultSet rs1 = pstmt1.executeQuery();

        ArrayList<Book> booksInCartAttributes = new ArrayList<>();
        BookDAO bookDao = new BookDaoImpl();

        // for each book in cart, add a new row in table Composition
        while(rs1.next()){
            String currentBook = rs1.getString("book");
            int currentQuantity = rs1.getInt("quantity");

            booksInCartAttributes.add(bookDao.getBook(currentBook));

            CompositionDAO newComposition = new CompositionDaoImpl();
            newComposition.addBookToOrder(currentBook, orderID, currentQuantity);
        }

        rs1.close();
        pstmt1.close();

        BigDecimal totalCost = totalCost(email);

        Order newOrder;

        if(!Pattern.matches("NOTREG*", orderID) ){
            int totalPoints = getOrderPoints(connection, booksInCartAttributes);

            // add new row in Order table
            OrderDAO orderDAO = new OrderDaoImpl();

            newOrder = new Order(orderID, getCurrentDate(),
                    OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                    paymentMethod, totalCost, totalPoints, shippingAddress,
                    email, null);

            orderDAO.addOrder(newOrder);

            // add points to user's LibroCard
            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

            String cardIDQuery = "SELECT cardID " +
                                 "FROM LibroCard " +
                                 "WHERE email = ?";

            PreparedStatement pstmt2 = connection.conn.prepareStatement(cardIDQuery);

            pstmt2.setString(1, email);

            ResultSet rs2 = pstmt2.executeQuery();

            libroCardDAO.addPoints(rs2.getString("cardID"), orderID);

            rs2.close();
            pstmt2.close();

        } else {
            // add new row in Order table
            OrderDAO orderDAO = new OrderDaoImpl();

            newOrder = new Order(orderID, getCurrentDate(),
                    OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                    paymentMethod, totalCost, null, shippingAddress,
                    null, email);

            orderDAO.addOrder(newOrder);
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

        String userNameSurnameQuery = "SELECT name, surname " +
                                      "FROM user JOIN cart ON user.email = cart.user " +
                                      "WHERE user.email = ?";

        PreparedStatement pstmt1 = connection.conn.prepareStatement(userNameSurnameQuery);
        pstmt1.setString(1, email);

        ResultSet rs1 = pstmt1.executeQuery();

        // if ResultSet is empty, try to find user in userNotReg table
        if(!rs1.next()){

            userNameSurnameQuery = "SELECT name, surname " +
                                   "FROM userNotReg JOIN cart ON userNotReg.email = cart.user " +
                                   "WHERE userNotReg.email = ?";

            PreparedStatement pstmt2 = connection.conn.prepareStatement(userNameSurnameQuery);
            pstmt2.setString(1, email);

            ResultSet rs2 = pstmt2.executeQuery();

            if(!rs2.next()){
                throw new UserNotInDatabaseException();
            } else {
                name = rs2.getString("name");
                surname = rs2.getString("surname");

                rs1.close();
                pstmt1.close();
                pstmt2.close();

                connection.closeConnection();

                String firstToken = name.substring(0, 3).toUpperCase() +
                        surname.substring(0, 3).toUpperCase();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime now = LocalDateTime.now();
                String dateTimeToken = dtf.format(now);

                return "NOTREG" + firstToken + dateTimeToken;
            }
        } else {
            name = rs1.getString("name");
            surname = rs1.getString("surname");

            rs1.close();
            pstmt1.close();

            connection.closeConnection();

            String firstToken = name.substring(0,3).toUpperCase() +
                    surname.substring(0,3).toUpperCase();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime now = LocalDateTime.now();
            String dateTimeToken  = dtf.format(now);

            return firstToken + dateTimeToken;
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
