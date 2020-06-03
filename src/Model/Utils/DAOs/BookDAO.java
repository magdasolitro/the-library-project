package Model.Utils.DAOs;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.GenresEnum;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDAO {

    public void addBook(String ISBN, String title, String authors, GenresEnum genre,
                        BigDecimal price, String description, String publishingHouse,
                        int publishingYear, BigDecimal discount, int availableCopies,
                        int libroCardPoints) throws SQLException, InvalidStringException;

    public Book getBook(String ISBN) throws SQLException, InvalidStringException,
            IllegalValueException;

    public ArrayList<Book> getAllBooks() throws SQLException, InvalidStringException,
            IllegalValueException;

    public ArrayList<Book> getBooksByGenre(GenresEnum genre) throws SQLException,
            InvalidStringException, IllegalValueException;

    public boolean isAvailable(String ISBN) throws SQLException;

    public void setDiscount(String ISBN, BigDecimal discount) throws SQLException;

    public void editDescription(String ISBN, String newDescription) throws SQLException;
}
