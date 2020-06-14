package Model.Utils.DaoImpl;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.GenresEnum;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDaoImpl implements BookDAO {

    @Override
    public void addBook(String ISBN, String title, String authors, GenresEnum genre,
                        BigDecimal price, String description, String publishingHouse,
                        int publishingYear, BigDecimal discount, int availableCopies,
                        int libroCardPoints) throws SQLException, InvalidStringException {

        String sql = "INSERT INTO book(ISBN, title, authors, genre, price, " +
                "description, publishingHouse, publishingYear, discount, " +
                "availableCopies, libroCardPoints) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, title);
        connection.pstmt.setString(3, authors);
        connection.pstmt.setString(4, genre.toString());
        connection.pstmt.setBigDecimal(5, price);
        connection.pstmt.setString(6, description);
        connection.pstmt.setString(7, publishingHouse);
        connection.pstmt.setInt(8, publishingYear);
        connection.pstmt.setBigDecimal(9, discount);
        connection.pstmt.setInt(10, availableCopies);
        connection.pstmt.setInt(11, libroCardPoints);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public Book getBook(String ISBN) throws SQLException, InvalidStringException,
            IllegalValueException {
        String sql = "SELECT * FROM book WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, ISBN);

        connection.rs = connection.pstmt.executeQuery();
        Book book  = new Book(connection.rs.getString("ISBN"),
                connection.rs.getString("title"),
                connection.rs.getString("authors"),
                connection.rs.getString("genre"),
                connection.rs.getBigDecimal("price"),
                connection.rs.getString("description"),
                connection.rs.getString("publishingHouse"),
                connection.rs.getInt("publishingYear"),
                connection.rs.getBigDecimal("discount"),
                connection.rs.getInt("availableCopies"),
                connection.rs.getInt("libroCardPoints"));

        connection.closeConnection();

        return book;
    }

    @Override
    public ArrayList<Book> getBookByTitle(String title) throws SQLException,
            InvalidStringException, IllegalValueException {
        String sql = "SELECT * FROM book WHERE title = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, title);

        connection.rs = connection.pstmt.executeQuery();

        ArrayList<Book> books = new ArrayList<>();

        while(connection.rs.next()){
            books.add(new Book(connection.rs.getString("ISBN"),
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

        return books;
    }

    public ArrayList<Book> getAllBooks() throws SQLException, InvalidStringException,
            IllegalValueException {

        String sql = "SELECT * FROM book";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        ArrayList<Book> allBooks = new ArrayList<>();

        while(connection.rs.next()){
            allBooks.add(new Book(connection.rs.getString("ISBN"),
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

        return allBooks;
    }

    public int titlesInCatalog() throws SQLException{
        String sql = "SELECT COUNT(ISBN) FROM book";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        return connection.rs.getInt(1);
    }

    public ArrayList<Book> getBooksByGenre(GenresEnum genre) throws SQLException,
            InvalidStringException, IllegalValueException {

        String sql = "SELECT * FROM book WHERE genre = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, genre.toString());

        connection.rs = connection.pstmt.executeQuery();

        ArrayList<Book> allBooksByGenre = new ArrayList<>();

        while(connection.rs.next()){
            allBooksByGenre.add(new Book(connection.rs.getString("ISBN"),
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

        return allBooksByGenre;
    }

    @Override
    public boolean isAvailable(String ISBN) throws SQLException {
        String sql = "SELECT availableCopies FROM book WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, ISBN);

        connection.rs = connection.pstmt.executeQuery();
        int availableCopies = connection.rs.getInt("availableCopies");

        connection.closeConnection();

        return availableCopies > 0;
    }

    @Override
    public void setDiscount(String ISBN, BigDecimal discount) throws SQLException {
        String sql = "UPDATE book SET discount = ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setBigDecimal(1, discount);
        connection.pstmt.setString(2, ISBN);

        connection.pstmt.executeUpdate();

        connection.closeConnection();

    }

    @Override
    public void editDescription(String ISBN, String newDescription)
            throws SQLException {
        String sql = "UPDATE book SET description = ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newDescription);
        connection.pstmt.setString(2, ISBN);

        connection.pstmt.executeUpdate();

        connection.closeConnection();;
    }
}
