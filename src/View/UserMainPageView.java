package View;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMainPageView {

    public static ScrollPane buildCatalogView(ArrayList<Book> booksToShow) throws InvalidStringException, SQLException,
            IllegalValueException {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1034, 700);
        scrollPane.isResizable();

        GridPane container = new GridPane();
        container.setHgap(20);

        int i = 0;
        for(Book b : booksToShow){
            GridPane currentBook = buildSingleBookView(b);
            container.add(currentBook, 0, i);
            container.setMargin(currentBook, new Insets(20, 0, 20, 30));
            i++;
        }

        scrollPane.setContent(container);

        return scrollPane;

    }

    private static GridPane buildSingleBookView(Book book){
        GridPane singleBook = new GridPane();

        Label titleLabel;
        Label authorLabel;
        Label publishingYearLabel;
        Label genreLabel;

        Label priceLabel;
        Label discountLabel;


        Font bookTitleFont = new Font("Avenir Book Bold", 25);
        Font genericLabelFont = new Font("Avenir Book", 20);

        // set book informations' labels and font
        titleLabel = new Label("\"" + book.getTitle() + "\"");
        titleLabel.setFont(bookTitleFont);

        // FIX TITLE VISUALIZATION
        /*
        if (book.getTitle().length() <= 40) {
            titleLabel = new Label("\"" + book.getTitle() + "\"");
            titleLabel.setFont(bookTitleFont);
        } else {
            int nRowsNeeded = (int) Math.ceil(book.getTitle().length() / 40.0);
            String title = book.getTitle().substring(0, 40) + "\n";

            for(int i = 41; i < 41*nRowsNeeded; i += 40){
                title += book.getTitle().substring(i, i+40) + "\n";
            }

            titleLabel = new Label(title);
        }
        */

        authorLabel = new Label("by " + book.getAuthors());
        authorLabel.setFont(genericLabelFont);

        publishingYearLabel = new Label("" + book.getPublishingYear());
        publishingYearLabel.setFont(genericLabelFont);

        genreLabel = new Label(book.getGenre());
        genreLabel.setFont(genericLabelFont);

        priceLabel = new Label("$ " + book.getPrice());
        priceLabel.setFont(genericLabelFont);

        if(book.getDiscount().compareTo(new BigDecimal(0)) > 0) {
            discountLabel = new Label("Discount: " + book.getDiscount() + " %");
            discountLabel.setFont(genericLabelFont);
        } else {
            discountLabel = new Label();
        }

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
        titleRow.setPercentHeight(100.0 / 4);       // perché ci sono 4 righe

        RowConstraints authorRow = new RowConstraints();
        authorRow.setValignment(VPos.CENTER);
        authorRow.setPercentHeight(100.0 / 4);

        RowConstraints publishingYearRow = new RowConstraints();
        publishingYearRow.setValignment(VPos.CENTER);
        publishingYearRow.setPercentHeight(100.0 / 4);

        RowConstraints genreRow = new RowConstraints();
        genreRow.setValignment(VPos.CENTER);
        genreRow.setPercentHeight(100.0 / 4);

        RowConstraints priceRow = new RowConstraints();
        priceRow.setValignment(VPos.CENTER);
        priceRow.setPercentHeight(100.0 / 2);

        RowConstraints discountRow = new RowConstraints();
        discountRow.setValignment(VPos.CENTER);
        discountRow.setPercentHeight(100.0 / 2);

        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(publishingYearLabel, 0, 2);
        singleBook.add(genreLabel, 0, 3);

        singleBook.add(priceLabel, 1, 0);
        singleBook.add(discountLabel, 1 ,1);

        // adds contraints to grid pane
        singleBook.getColumnConstraints().addAll(column1, column2);
        singleBook.getRowConstraints().addAll(titleRow, authorRow, genreRow,
                priceRow, discountRow);

        return singleBook;
    }


}
