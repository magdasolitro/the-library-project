package Model.Utils.DAOs;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface BookDAO {

    public void addBook(Book book) throws SQLException;

    public void addBook(String ISBN, String title, String authors, String genre,
                        BigDecimal price, String description, String publishingHouse,
                        int publishingYear, BigDecimal discount, int availableCopies,
                        int libroCardPoints) throws SQLException, InvalidStringException;

    public Book getBook(String ISBN) throws SQLException, InvalidStringException,
            IllegalValueException;

    public boolean isAvailable(String ISBN) throws SQLException;

    public void setDiscount(String ISBN, BigDecimal discount) throws SQLException;

    public void editDescription(String ISBN, String newDescription) throws SQLException;
}
