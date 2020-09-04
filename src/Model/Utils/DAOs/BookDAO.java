package Model.Utils.DAOs;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.ObjectNotInDatabaseException;
import Model.GenresEnum;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDAO {

    void addBook(Book book) throws SQLException, InvalidStringException;

    Book getBook(String ISBN) throws SQLException, InvalidStringException,
            IllegalValueException, ObjectNotInDatabaseException;

    ArrayList<Book> getBookByTitle(String title) throws SQLException,
            InvalidStringException, IllegalValueException;

    ArrayList<Book> getAllBooks() throws SQLException, InvalidStringException,
            IllegalValueException;

    ArrayList<Book> getBooksByGenre(GenresEnum genre) throws SQLException,
            InvalidStringException, IllegalValueException;

    boolean isAvailable(String ISBN) throws SQLException;
    
    void editPrice(String ISBN, BigDecimal price) throws SQLException;

    void editDiscount(String ISBN, BigDecimal discount) throws SQLException;

    void editDescription(String ISBN, String newDescription) throws SQLException;

    void editLibroCardPoints(String ISBN, int libroCardPoints) throws SQLException;

    void editGenre(String ISBN, GenresEnum genre) throws SQLException;

    void decreaseAvailableCopies(String ISBN, int removedUnits) throws SQLException;

    void increaseAvailableCopies(String ISBN, int addedUnits) throws SQLException;

    void editAvailableCopies(String ISBN, int availableCopies) throws SQLException;

}
