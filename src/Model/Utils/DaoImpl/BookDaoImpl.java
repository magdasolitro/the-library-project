package Model.Utils.DaoImpl;

import Model.Book;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public class BookDaoImpl implements BookDAO {
    @Override
    public void addBook(Book book)
            throws SQLException {

        String sql = "INSERT INTO book(ISBN, title, authors, genre, price, description, " +
                "publishingHouse, publishingYear, discount, availableCopies) VALUES (?," +
                "?,?,?,?,?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, book.getISBN());
        connection.pstmt.setString(2, book.getTitle());
        connection.pstmt.setString(3, book.getAuthors());
        connection.pstmt.setString(4, book.getGenre());
        connection.pstmt.setFloat(5, book.getPrice());
        connection.pstmt.setString(6, book.getDescription());
        connection.pstmt.setString(7, book.getPublishingHouse());
        connection.pstmt.setInt(8, book.getPublishingYear());
        connection.pstmt.setFloat(9, book.getDiscount());
        connection.pstmt.setInt(10, book.getAvailableCopies());

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
                connection.rs.getFloat("price"),
                connection.rs.getString("description"),
                connection.rs.getString("publishingHouse"),
                connection.rs.getInt("publishingYear"),
                connection.rs.getFloat("discount"),
                connection.rs.getInt("availableCopies"));

        connection.closeConnection();

        return book;
    }

    @Override
    public boolean isAvailable(String ISBN) throws SQLException {
        String sql = "SELECT availableCopies FROM book WHERE ISBN = " + ISBN;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();
        int availableCopies = connection.rs.getInt("availableCopies");

        connection.closeConnection();

        return availableCopies > 0;
    }

    @Override
    public void setDiscount(String ISBN, float discount) throws SQLException {
        String sql = "UPDATE book SET discount = ? WHERE ISBN = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setFloat(1, discount);
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
