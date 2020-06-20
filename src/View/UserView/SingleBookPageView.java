package View.UserView;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class SingleBookPageView {

    public static GridPane showBookInfos(String ISBN){
        GridPane gpBookInfos = new GridPane();

        Label authorLabel;
        Label priceLabel;
        Label genreLable;
        Label publishingHouseLabel;
        Label publishingYearLabel;
        Label discountLabel;
        Label libroCardPointsLabel;
        Label ISBNLabel;

        Font infoFont = new Font("Avenir Book", 20);

        try{
            BookDAO bookDAO = new BookDaoImpl();

            Book book = bookDAO.getBook(ISBN);

            authorLabel = new Label(book.getAuthors());
            priceLabel = new Label(book.getPrice().setScale(2).toString());
            genreLable = new Label(book.getGenre());
            publishingHouseLabel = new Label(book.getPublishingHouse());
            publishingYearLabel = new Label("" + book.getPublishingYear());
            discountLabel = new Label(book.getDiscount().toString());
            libroCardPointsLabel = new Label("" + book.getLibroCardPoints());
            ISBNLabel = new Label(book.getISBN());

            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.LEFT);

            RowConstraints authorRow = new RowConstraints();
            authorRow.setValignment(VPos.CENTER);
            authorRow.setPercentHeight(100.0 / 8);

            RowConstraints priceRow = new RowConstraints();
            priceRow.setValignment(VPos.CENTER);
            priceRow.setPercentHeight(100.0 / 8);

            RowConstraints genreRow = new RowConstraints();
            genreRow.setValignment(VPos.CENTER);
            genreRow.setPercentHeight(100.0 / 8);

            RowConstraints publishingHouseRow = new RowConstraints();
            publishingHouseRow.setValignment(VPos.CENTER);
            publishingHouseRow.setPercentHeight(100.0 / 8);

            RowConstraints publishingYearRow = new RowConstraints();
            publishingYearRow.setValignment(VPos.CENTER);
            publishingYearRow.setPercentHeight(100.0 / 8);

            RowConstraints discountRow = new RowConstraints();
            discountRow.setValignment(VPos.CENTER);
            discountRow.setPercentHeight(100.0 / 8);

            RowConstraints libroCardPointsRow = new RowConstraints();
            libroCardPointsRow.setValignment(VPos.CENTER);
            libroCardPointsRow.setPercentHeight(100.0 / 8);

            RowConstraints ISBNRow = new RowConstraints();
            ISBNRow.setValignment(VPos.CENTER);
            ISBNRow.setPercentHeight(100.0 / 8);

            authorLabel.setFont(infoFont);
            priceLabel.setFont(infoFont);
            genreLable.setFont(infoFont);
            publishingHouseLabel.setFont(infoFont);
            publishingYearLabel.setFont(infoFont);
            discountLabel.setFont(infoFont);
            libroCardPointsLabel.setFont(infoFont);
            ISBNLabel.setFont(infoFont);

            gpBookInfos.add(authorLabel, 0, 0);
            gpBookInfos.add(priceLabel, 0, 1);
            gpBookInfos.add(genreLable, 0, 2);
            gpBookInfos.add(publishingHouseLabel, 0, 3);
            gpBookInfos.add(publishingYearLabel, 0, 4);
            gpBookInfos.add(discountLabel, 0, 5);
            gpBookInfos.add(libroCardPointsLabel, 0, 6);
            gpBookInfos.add(ISBNLabel, 0, 7);

            gpBookInfos.setVgap(20);

            gpBookInfos.getRowConstraints().addAll(authorRow, priceRow, genreRow,
                    publishingHouseRow, publishingYearRow, discountRow, libroCardPointsRow,
                    ISBNRow);

            gpBookInfos.getColumnConstraints().add(column);

        } catch (InvalidStringException ise) {
            ise.printStackTrace();
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return gpBookInfos;
    }
}
