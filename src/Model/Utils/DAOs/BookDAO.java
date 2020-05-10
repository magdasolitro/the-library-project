package Model.Utils.DAOs;

import Model.Book;
import java.sql.SQLException;

public interface BookDAO {

    public void addBook(Book book) throws SQLException;

    public Book getBook(String ISBN) throws SQLException;

    public boolean isAvailable(Book book) throws SQLException;

    public void setDiscount(Book book, float discount) throws SQLException;

    public void editDescription(Book book, String newDescription) throws SQLException;
}
