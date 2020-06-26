package View.UserView;

import Model.Book;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartPageView {

    public static ScrollPane buildBooksView(ArrayList<Book> booksToShow){

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefHeight(500);
        scrollPane.setPrefWidth(836);
        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane bookContainer = new GridPane();

        int i = 0;

        for (Book b : booksToShow) {
            GridPane currentBook = buildCartBookView(b);
            bookContainer.add(currentBook, 0, i);
            GridPane.setMargin(currentBook, new Insets(20, 0, 20, 30));
            i++;
        }

        scrollPane.setContent(bookContainer);

        bookContainer.prefWidthProperty().bind(scrollPane.widthProperty());
        bookContainer.prefHeightProperty().bind(scrollPane.heightProperty());

        return scrollPane;

    }

    private static GridPane buildCartBookView(Book book){
        GridPane singleBook = new GridPane();
        singleBook.isResizable();

        Label titleLabel;
        Label authorLabel;
        Label publishingYearLabel;
        Label genreLabel;

        Label quantityLabel;
        Label priceLabel;
        Label discountLabel;

        Spinner quantitySpinner;

        Button removeFromCartButton;

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

        quantityLabel = new Label("Quantity:");

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

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHalignment(HPos.LEFT);
        column2.setPercentWidth(50);

        // set rows constraints
        RowConstraints titleRow = new RowConstraints();
        titleRow.setValignment(VPos.CENTER);
        titleRow.setVgrow(Priority.SOMETIMES);

        RowConstraints authorRow = new RowConstraints();
        authorRow.setValignment(VPos.CENTER);
        authorRow.setPercentHeight(100.0 / 4);

        RowConstraints publishingYearRow = new RowConstraints();
        publishingYearRow.setValignment(VPos.CENTER);
        publishingYearRow.setPercentHeight(100.0 / 4);

        RowConstraints genreRow = new RowConstraints();
        genreRow.setValignment(VPos.CENTER);
        genreRow.setPercentHeight(100.0 / 4);

        quantitySpinner = new Spinner<>(1, 10, 1, 1);
        quantitySpinner.setPrefHeight(45);
        quantitySpinner.setPrefWidth(80);

        Label removeFromCartLabel = new Label("Remove from cart");
        removeFromCartLabel.setFont(new Font("Avenir Book Bold", 20));
        removeFromCartButton = new Button();

        RowConstraints priceRow = new RowConstraints();
        RowConstraints discountRow = new RowConstraints();

        priceRow.setValignment(VPos.CENTER);
        priceRow.setPercentHeight(100.0/2);

        discountRow.setValignment(VPos.CENTER);
        priceRow.setPercentHeight(100.0/2);

        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(publishingYearLabel, 0, 2);
        singleBook.add(genreLabel, 0, 3);

        singleBook.add(priceLabel, 1, 0);
        singleBook.add(discountLabel, 1 ,1);

        singleBook.add(quantitySpinner, 2, 0);
        singleBook.add(removeFromCartButton, 2, 1);


        singleBook.isResizable();

        // adds contraints to grid pane
        singleBook.getColumnConstraints().addAll(column1, column2);
        singleBook.getRowConstraints().addAll(titleRow, authorRow, publishingYearRow,
                priceRow, discountRow);

        return singleBook;
    }

}
