package Model.Utils.DaoImpl;

import Model.Book;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BookDaoImpl implements BookDAO {
    @Override
    public void addBook(Book book)
            throws SQLException {

        String sql = "INSERT INTO book(ISBN, title, authors, genre, price, " +
                "description, publishingHouse, publishingYear, discount, " +
                "availableCopies, libroCardPoints) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, book.getISBN());
        connection.pstmt.setString(2, book.getTitle());
        connection.pstmt.setString(3, book.getAuthors());
        connection.pstmt.setString(4, book.getGenre());
        connection.pstmt.setBigDecimal(5, book.getPrice());
        connection.pstmt.setString(6, book.getDescription());
        connection.pstmt.setString(7, book.getPublishingHouse());
        connection.pstmt.setInt(8, book.getPublishingYear());
        connection.pstmt.setBigDecimal(9, book.getDiscount());
        connection.pstmt.setInt(10, book.getAvailableCopies());
        connection.pstmt.setInt(11, book.getLibroCardPoints());

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void addBook(String ISBN, String title, String authors, String genre,
                        BigDecimal price, String description, String publishingHouse,
                        int publishingYear, BigDecimal discount, int availableCopies,
                        int libroCardPoints) throws SQLException{

        String sql = "INSERT INTO book(ISBN, title, authors, genre, price, " +
                "description, publishingHouse, publishingYear, discount, " +
                "availableCopies, libroCardPoints) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, ISBN);
        connection.pstmt.setString(2, title);
        connection.pstmt.setString(3, authors);
        connection.pstmt.setString(4, genre);
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
    public Book getBook(String ISBN) throws SQLException {
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
                connection.rs.getInt("libroCardPoint"));

        connection.closeConnection();

        return book;
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
