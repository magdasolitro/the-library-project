package View.UserView;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class SingleBookPageView {

    public static GridPane showBookInfos(String ISBN){
        GridPane gpBook = new GridPane();

        Label authorInfo, authorLabel;
        Label priceInfo, priceLabel;
        Label genreInfo, genreLabel;
        Label publishingHouseInfo, publishingHouseLabel;
        Label publishingYearInfo, publishingYearLabel;
        Label discountInfo, discountLabel;
        Label libroCardPointsInfo, libroCardPointsLabel;
        Label ISBNInfo, ISBNLabel;

        Font infoFont = new Font("Avenir Book", 20);
        Font labelsFont = new Font("Avenir Next Bold", 20);

        authorLabel = new Label("Author(s):");
        priceLabel = new Label("Price:");
        genreLabel = new Label("Genre:");
        publishingHouseLabel = new Label("Publishing House:");
        publishingYearLabel = new Label("Publishing Year:");
        discountLabel = new Label("Discount:");
        libroCardPointsLabel = new Label("Libro Card Points:");
        ISBNLabel = new Label("ISBN:");

        gpBook.add(authorLabel, 0, 0);
        gpBook.add(priceLabel, 0, 1);
        gpBook.add(genreLabel, 0, 2);
        gpBook.add(publishingHouseLabel, 0, 3);
        gpBook.add(publishingYearLabel, 0, 4);
        gpBook.add(discountLabel, 0, 5);
        gpBook.add(libroCardPointsLabel, 0, 6);
        gpBook.add(ISBNLabel, 0, 7);

        try{
            BookDAO bookDAO = new BookDaoImpl();

            Book book = bookDAO.getBook(ISBN);

            authorInfo = new Label(book.getAuthors());
            priceInfo = new Label("$ " + book.getPrice().setScale(2).toString());
            genreInfo = new Label(book.getGenre());
            publishingHouseInfo = new Label(book.getPublishingHouse());
            publishingYearInfo = new Label("" + book.getPublishingYear());
            discountInfo = new Label(book.getDiscount().toString() + " %");
            libroCardPointsInfo = new Label("" + book.getLibroCardPoints());
            ISBNInfo = new Label(book.getISBN());

            gpBook.add(authorInfo, 1, 0);
            gpBook.add(priceInfo, 1, 1);
            gpBook.add(genreInfo, 1, 2);
            gpBook.add(publishingHouseInfo, 1, 3);
            gpBook.add(publishingYearInfo, 1, 4);
            gpBook.add(discountInfo, 1, 5);
            gpBook.add(libroCardPointsInfo, 1, 6);
            gpBook.add(ISBNInfo, 1, 7);

            authorLabel.setFont(labelsFont);
            priceLabel.setFont(labelsFont);
            genreLabel.setFont(labelsFont);
            publishingHouseLabel.setFont(labelsFont);
            publishingYearLabel.setFont(labelsFont);
            discountLabel.setFont(labelsFont);
            libroCardPointsLabel.setFont(labelsFont);
            ISBNLabel.setFont(labelsFont);

            authorInfo.setFont(infoFont);
            priceInfo.setFont(infoFont);
            genreInfo.setFont(infoFont);
            publishingHouseInfo.setFont(infoFont);
            publishingYearInfo.setFont(infoFont);
            discountInfo.setFont(infoFont);
            libroCardPointsInfo.setFont(infoFont);
            ISBNInfo.setFont(infoFont);


            ColumnConstraints labelsColumn = new ColumnConstraints();
            labelsColumn.setHalignment(HPos.LEFT);

            ColumnConstraints infoColumn = new ColumnConstraints();
            infoColumn.setHalignment(HPos.LEFT);

            gpBook.setVgap(20);
            gpBook.setHgap(40);

            gpBook.getColumnConstraints().addAll(labelsColumn, infoColumn);

        } catch (InvalidStringException | IllegalValueException | SQLException e) {
            e.printStackTrace();
        }

        return gpBook;
    }
}
