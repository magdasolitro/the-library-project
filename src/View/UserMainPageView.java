package View;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMainPageView {

    public static ScrollPane buildCatalogView() throws InvalidStringException, SQLException,
            IllegalValueException {

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Book> catalog = new ArrayList<>();

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefSize(1034, 700);
        scrollPane.isResizable();

        // fill the catalog
        //catalog.addAll(bookDAO.getAllBooks());

        scrollPane.setContent(buildSingleBookView(bookDAO.getBook("978-14" +
                "-08855-65-2")));

        return scrollPane;

    }

    private static BorderPane buildSingleBookView(Book book){

        BorderPane bookRoot = new BorderPane();

        GridPane singleBook = new GridPane();

        Label titleLabel;
        Label authorLabel;
        Label genreLabel;

        Label priceLabel;
        Label discountLabel;

        // book info labels
        titleLabel = new Label(book.getTitle());
        authorLabel = new Label(book.getAuthors());
        genreLabel = new Label(book.getGenre());

        priceLabel = new Label("$" + book.getPrice());

        // set columns costraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(50);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        column2.setPercentWidth(50);

        // set rows constraints
        RowConstraints titleRow = new RowConstraints();
        titleRow.setValignment(VPos.CENTER);
        titleRow.setPercentHeight(100.0 / 3);       // perché ci sono 3 righe

        RowConstraints authorRow = new RowConstraints();
        authorRow.setValignment(VPos.CENTER);
        authorRow.setPercentHeight(100.0 / 3);

        RowConstraints genreRow = new RowConstraints();
        genreRow.setValignment(VPos.CENTER);
        genreRow.setPercentHeight(100.0 / 3);

        RowConstraints priceRow = new RowConstraints();
        priceRow.setValignment(VPos.CENTER);
        priceRow.setPercentHeight(100.0 / 2);

        RowConstraints discountRow = new RowConstraints();
        discountRow.setValignment(VPos.CENTER);
        discountRow.setPercentHeight(100.0 / 2);

        if(book.getDiscount().compareTo(new BigDecimal(0)) > 0) {
            discountLabel = new Label(book.getDiscount() + " %");
        } else {
            discountLabel = new Label();
        }

        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(genreLabel, 0, 2);

        singleBook.add(priceLabel, 1, 0);
        singleBook.add(discountLabel, 1 ,1);

        // adds contraints to grid pane
        singleBook.getColumnConstraints().addAll(column1, column2);
        singleBook.getRowConstraints().addAll(titleRow, authorRow, genreRow,
                priceRow, discountRow);

        bookRoot.setCenter(singleBook);

        return bookRoot;
    }


}
