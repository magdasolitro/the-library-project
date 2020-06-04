package View;

import Model.Book;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UserMainPageView {

    public void buildCatalogView(){

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

    public HBox buildSingleBookView(){

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

        titleLabel = new Label("Titolo");
        authorLabel = new Label("Autore");
        genreLabel = new Label("Genere");

        bookInfoVBox.getChildren().addAll(titleLabel, authorLabel, genreLabel);

        bookPriceVBox = new VBox();

        priceLabel = new Label("Prezzo");
        discountLabel = new Label("Sconto");

        bookPriceVBox.getChildren().addAll(priceLabel, discountLabel);

        singleBookHBox.getChildren().addAll(bookInfoVBox, bookPriceVBox);

        HBox.setHgrow(bookInfoVBox, Priority.ALWAYS);
        HBox.setHgrow(bookPriceVBox, Priority.ALWAYS);
        bookInfoVBox.setMaxWidth(Double.MAX_VALUE);
        bookPriceVBox.setMaxWidth(Double.MAX_VALUE);

        return singleBookHBox;
    }
}
