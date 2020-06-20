package View.UserView;

import Model.Book;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMainPageView {

    public static ScrollPane buildBooksView(ArrayList<Book> booksToShow){

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefHeight(700);
        scrollPane.fitToWidthProperty();
        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane bookContainer = new GridPane();
        bookContainer.setHgap(20);

        int i = 0;

        for (Book b : booksToShow) {
            GridPane currentBook = buildSingleBookView(b);
            bookContainer.add(currentBook, 0, i);
            GridPane.setMargin(currentBook, new Insets(20, 0, 20, 30));
            i++;
        }

        scrollPane.setContent(bookContainer);

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
        Label notAvailableLabel;

        Font bookTitleFont = new Font("Avenir Book Bold", 25);
        Font genericLabelFont = new Font("Avenir Book", 20);

        // set book informations' labels and font
        titleLabel = new Label("\"" + book.getTitle() + "\"");
        titleLabel.setFont(bookTitleFont);

        authorLabel = new Label("by " + book.getAuthors());
        authorLabel.setFont(genericLabelFont);

        publishingYearLabel = new Label("" + book.getPublishingYear());
        publishingYearLabel.setFont(genericLabelFont);

        genreLabel = new Label(book.getGenre());
        genreLabel.setFont(genericLabelFont);

        priceLabel = new Label("$ " + book.getPrice().setScale(2));
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

        // check if book is available: if not, display a message
        BookDAO bookDAO = new BookDaoImpl();

        RowConstraints priceRow = new RowConstraints();
        RowConstraints discountRow = new RowConstraints();
        RowConstraints notAvailableRow = new RowConstraints();

        try{
            if(bookDAO.isAvailable(book.getISBN())){
                priceRow.setValignment(VPos.CENTER);
                priceRow.setPercentHeight(100.0 / 2);

                discountRow.setValignment(VPos.CENTER);
                discountRow.setPercentHeight(100.0 / 2);
            } else {
                priceRow.setValignment(VPos.CENTER);
                priceRow.setPercentHeight(100.0 / 3);

                discountRow.setValignment(VPos.CENTER);
                discountRow.setPercentHeight(100.0 / 3);

                notAvailableRow.setValignment(VPos.CENTER);
                notAvailableRow.setPercentHeight(100.0 / 3);

                notAvailableLabel = new Label("Not Available");
                notAvailableLabel.setFont(new Font("Avenir Book", 20));
                notAvailableLabel.setTextFill(Color.RED);

                singleBook.add(notAvailableLabel, 1,2);
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        }

        singleBook.isResizable();
        singleBook.setHgap(20);

        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(publishingYearLabel, 0, 2);
        singleBook.add(genreLabel, 0, 3);

        singleBook.add(priceLabel, 2, 0);
        singleBook.add(discountLabel, 2 ,1);

        // adds contraints to grid pane
        singleBook.getColumnConstraints().addAll(column1, column2);
        singleBook.getRowConstraints().addAll(titleRow, authorRow, genreRow,
                priceRow, discountRow);

        return singleBook;
    }


}
