package View;

import Model.Book;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class UserMainPageView {

    public void buildCatalogView(){
        // ObservableList<HBox> catalog = new ObservableList<>();
    }

    /*
    *
    * +--------------------------------+
    * |          |          |          |
    * |          | titolo   | prezzo   |
    * |  IMAGE   | autore/i | (sconto) |
    * |          | genere   |          |
    * |          |          |          |
    * +--------------------------------+
    *
    * */

    public HBox buildSingleBookView(Book book){

        HBox singleBookHBox;
        VBox bookInfoVBox;
        VBox bookPriceVBox;

        Label titleLabel;
        Label authorLabel;
        Label genreLabel;

        Label priceLabel;
        Label discountLabel;

        singleBookHBox = new HBox();

        bookInfoVBox = new VBox();

        titleLabel = new Label(book.getTitle());
        authorLabel = new Label(book.getAuthors());
        genreLabel = new Label(book.getGenre());

        bookInfoVBox.getChildren().addAll(titleLabel, authorLabel, genreLabel);

        bookPriceVBox = new VBox();

        priceLabel = new Label("$" + book.getPrice());

        if(book.getDiscount().compareTo(new BigDecimal(0)) > 0) {
            discountLabel = new Label(book.getDiscount() + " %");
        } else {
            discountLabel = new Label();
        }

        bookPriceVBox.getChildren().addAll(priceLabel, discountLabel);

        singleBookHBox.getChildren().addAll(bookInfoVBox, bookPriceVBox);

        HBox.setHgrow(bookInfoVBox, Priority.ALWAYS);
        HBox.setHgrow(bookPriceVBox, Priority.ALWAYS);
        bookInfoVBox.setMaxWidth(Double.MAX_VALUE);
        bookPriceVBox.setMaxWidth(Double.MAX_VALUE);

        return singleBookHBox;
    }


}
