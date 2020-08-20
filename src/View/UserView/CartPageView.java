package View.UserView;

import Controller.GeneralLoginController;
import Model.Book;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DaoImpl.CartDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CartPageView {

    public static ScrollPane buildCartView(ArrayList<Book> booksToShow, boolean canBeRemoved){

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane bookContainer = new GridPane();

        bookContainer.setId("cart-gridpane");
        bookContainer.getStylesheets().add("/CSS/style.css");

        int i = 0;

        for (Book b : booksToShow) {
            GridPane currentBook = buildCartBookView(b, canBeRemoved);
            bookContainer.add(currentBook, 0, i);
            GridPane.setMargin(currentBook, new Insets(20, 0, 20, 30));

            i++;
        }

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(50);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        column2.setPercentWidth(50);

        bookContainer.getColumnConstraints().addAll(column1, column2);

        bookContainer.setVgap(20);

        scrollPane.setContent(bookContainer);
        scrollPane.isResizable();

        return scrollPane;

    }

    private static GridPane buildCartBookView(Book book, boolean canBeRemoved){
        GridPane singleBook = new GridPane();

        Label titleLabel;
        Label authorLabel;
        Label publishingYearLabel;
        Label genreLabel;

        Label priceLabel;
        Label discountLabel;
        Label quantityLabel;
        Button removeFromCartButton;


        // FIRST COLUMN
        Font bookTitleFont = new Font("Avenir Book Bold", 25);
        Font genericLabelFont = new Font("Avenir Book", 20);

        titleLabel = new Label("\"" + book.getTitle() + "\"");
        titleLabel.setFont(bookTitleFont);

        authorLabel = new Label("by " + book.getAuthors());
        authorLabel.setFont(genericLabelFont);

        publishingYearLabel = new Label("" + book.getPublishingYear());
        publishingYearLabel.setFont(genericLabelFont);

        genreLabel = new Label(book.getGenre());
        genreLabel.setFont(genericLabelFont);


        // add title, author, publishing year and genre to gridpane
        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(publishingYearLabel, 0, 2);
        singleBook.add(genreLabel, 0, 3);


        // SECOND COLUMN
        priceLabel = new Label("$ " + book.getPrice().setScale(2, RoundingMode.FLOOR));
        priceLabel.setFont(genericLabelFont);

        if(book.getDiscount().compareTo(new BigDecimal(0)) > 0) {
            discountLabel = new Label("Discount: " + book.getDiscount() + " %");
            discountLabel.setFont(genericLabelFont);
        } else {
            discountLabel = new Label();
        }

        try {
            quantityLabel = new Label("Qty: " + book.getQuantity(GeneralLoginController.getLoginInstance()));
            quantityLabel.setFont(genericLabelFont);
        } catch (SQLException e) {
            quantityLabel = new Label();
        }

        if(canBeRemoved){
            removeFromCartButton = new Button("Remove from cart");

            removeFromCartButton.setId("removefromcart-button");
            removeFromCartButton.getStylesheets().add("/CSS/style.css");

            removeFromCartButton.setOnMouseClicked(e -> {
                Alert removalAlert = new Alert(Alert.AlertType.WARNING);

                removalAlert.setTitle("Book Removal");
                removalAlert.setHeaderText("This book will be removed from your cart");
                removalAlert.setContentText("Are you sure you want to proceed?");

                Optional<ButtonType> response = removalAlert.showAndWait();

                if(response.isPresent() && response.get() == ButtonType.OK) {
                    CartDAO cartDAO = new CartDaoImpl();
                    try {
                        cartDAO.removeBookFromCart(book.getISBN(), GeneralLoginController.getLoginInstance());
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                    }
                } else {
                    e.consume();
                    removalAlert.close();
                }
            });

            singleBook.add(removeFromCartButton, 1, 3);
        }


        // add price, discount, quantity, remove button to gridpane
        singleBook.add(priceLabel, 1, 0);
        singleBook.add(discountLabel, 1 ,1);
        singleBook.add(quantityLabel, 1, 2);


        // set column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(70);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        column2.setPercentWidth(30);


        singleBook.setPrefWidth(750);
        singleBook.isResizable();
        singleBook.setVgap(10);

        // adds contraints to grid pane
        singleBook.getColumnConstraints().addAll(column1, column2);

        return singleBook;
    }

}
