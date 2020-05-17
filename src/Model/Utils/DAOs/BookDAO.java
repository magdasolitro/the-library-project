package Model.Utils.DAOs;

import Model.Book;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface BookDAO {

    public void addBook(Book book) throws SQLException;

    public Book getBook(String ISBN) throws SQLException;

    public boolean isAvailable(String ISBN) throws SQLException;

    public void setDiscount(String ISBN, BigDecimal discount) throws SQLException;

    public void editDescription(String ISBN, String newDescription) throws SQLException;
}
