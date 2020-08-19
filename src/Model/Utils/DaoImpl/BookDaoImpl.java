package Model.Utils.DaoImpl;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.GenresEnum;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDaoImpl implements BookDAO {

    /**
     * Adds a new book to the store
     */
    @Override
    public void addBook(Book book) throws SQLException {

        String sql = "INSERT INTO book(ISBN, title, authors, genre, price, " +
                "description, publishingHouse, publishingYear, discount, " +
                "availableCopies, libroCardPoints) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, book.getISBN());
        pstmt.setString(2, book.getTitle());
        pstmt.setString(3, book.getAuthors());
        pstmt.setString(4, book.getGenre());
        pstmt.setBigDecimal(5, book.getPrice());
        pstmt.setString(6, book.getDescription());
        pstmt.setString(7, book.getPublishingHouse());
        pstmt.setInt(8, book.getPublishingYear());
        pstmt.setBigDecimal(9, book.getDiscount());
        pstmt.setInt(10, book.getAvailableCopies());
        pstmt.setInt(11, book.getLibroCardPoints());

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    /**
     * Returns the Book that has the specified ISBN
     */
    @Override
    public Book getBook(String ISBN) throws SQLException, InvalidStringException,
            IllegalValueException {
        String sql = "SELECT * FROM book WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, ISBN);

        ResultSet rs = pstmt.executeQuery();
        
        Book book  = new Book(rs.getString("ISBN"),
                rs.getString("title"),
                rs.getString("authors"),
                rs.getString("genre"),
                rs.getBigDecimal("price"),
                rs.getString("description"),
                rs.getString("publishingHouse"),
                rs.getInt("publishingYear"),
                rs.getBigDecimal("discount"),
                rs.getInt("availableCopies"),
                rs.getInt("libroCardPoints"));

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return book;
    }


    /**
     * List of books that have a specified title.
     * The store might have multiple books with the same title
     * @param title book title
     * @return a list of Book
     */
    @Override
    public ArrayList<Book> getBookByTitle(String title) throws SQLException,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM book WHERE title = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, title);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Book> books = new ArrayList<>();

        while(rs.next()){
            books.add(new Book(rs.getString("ISBN"),
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

        return books;
    }
    
    public ArrayList<Book> getAllBooks() throws SQLException, InvalidStringException,
            IllegalValueException {

        String sql = "SELECT * FROM book";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Book> allBooks = new ArrayList<>();

        while(rs.next()){
            allBooks.add(new Book(rs.getString("ISBN"),
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

        return allBooks;
    }

    public int titlesInCatalog() throws SQLException{
        String sql = "SELECT COUNT(ISBN) FROM book";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        int titlesInCatalog = rs.getInt(1);

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return titlesInCatalog;
    }

    public ArrayList<Book> getBooksByGenre(GenresEnum genre) throws SQLException,
            InvalidStringException, IllegalValueException {

        String sql = "SELECT * FROM book WHERE genre = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        
        pstmt.setString(1, genre.toString());

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Book> allBooksByGenre = new ArrayList<>();

        while(rs.next()){
            allBooksByGenre.add(new Book(rs.getString("ISBN"),
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

        return allBooksByGenre;
    }

    @Override
    public boolean isAvailable(String ISBN) throws SQLException {
        String sql = "SELECT availableCopies FROM book WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        
        pstmt.setString(1, ISBN);

        ResultSet rs = pstmt.executeQuery();

        int availableCopies = rs.getInt("availableCopies");

        rs.close();
        pstmt.close();

        connection.closeConnection();

        return availableCopies > 0;
    }

    @Override
    public void setDiscount(String ISBN, BigDecimal discount) throws SQLException {
        String sql = "UPDATE book SET discount = ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setBigDecimal(1, discount);
        pstmt.setString(2, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void editDescription(String ISBN, String newDescription)
            throws SQLException {
        String sql = "UPDATE book SET description = ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newDescription);
        pstmt.setString(2, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void decreaseAvailableCopies(String ISBN, int removedUnits) throws SQLException {
        String sql = "UPDATE book SET availableCopies = availableCopies - ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setInt(1, removedUnits);
        pstmt.setString(2, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void increaseAvailableCopies(String ISBN, int addedUnits) throws SQLException {
        String sql = "UPDATE book SET availableCopies = availableCopies + ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setInt(1, addedUnits);
        pstmt.setString(2, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void setAvailableCopies(String ISBN, int availableCopies) throws SQLException {
        String sql = "UPDATE book SET availableCopies = ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setInt(1, availableCopies);
        pstmt.setString(2, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }
}
