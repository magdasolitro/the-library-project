package View.UserView;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class SingleBookPageView {

    public static ScrollPane buildBookInfos(String ISBN){
        ScrollPane scrollPane = new ScrollPane();
        VBox vbBook = new VBox(30);
        GridPane gpBook = new GridPane();

        BookDAO bookDAO = new BookDaoImpl();

        Label titleInfo;
        Label authorInfo, authorLabel;
        Label priceInfo, priceLabel;
        Label genreInfo, genreLabel;
        Label publishingHouseInfo, publishingHouseLabel;
        Label publishingYearInfo, publishingYearLabel;
        Label discountInfo, discountLabel;
        Label libroCardPointsInfo, libroCardPointsLabel;
        Label ISBNInfo, ISBNLabel;

        Book book = null;

        try {
            book = bookDAO.getBook(ISBN);

            // BUILD TITLE
            String splittedTitleString = splitString(book.getTitle(), 27);
            titleInfo = new Label("\"" + splittedTitleString + "\"");
            titleInfo.setFont(new Font("Avenir Next Bold", 50));


            // BUILD BOOK INFO GRIDPANE
            Font infoFont = new Font("Avenir Book", 20);
            Font labelsFont = new Font("Avenir Next Bold", 20);

            // create labels
            authorLabel = new Label("Author(s):");
            priceLabel = new Label("Price:");
            genreLabel = new Label("Genre:");
            publishingHouseLabel = new Label("Publishing House:");
            publishingYearLabel = new Label("Publishing Year:");
            discountLabel = new Label("Discount:");
            libroCardPointsLabel = new Label("Libro Card Points:");
            ISBNLabel = new Label("ISBN:");

            // set fonts
            authorLabel.setFont(labelsFont);
            priceLabel.setFont(labelsFont);
            genreLabel.setFont(labelsFont);
            publishingHouseLabel.setFont(labelsFont);
            publishingYearLabel.setFont(labelsFont);
            discountLabel.setFont(labelsFont);
            libroCardPointsLabel.setFont(labelsFont);
            ISBNLabel.setFont(labelsFont);

            // add labels to gridpane
            gpBook.add(authorLabel, 0, 0);
            gpBook.add(priceLabel, 0, 1);
            gpBook.add(genreLabel, 0, 2);
            gpBook.add(publishingHouseLabel, 0, 3);
            gpBook.add(publishingYearLabel, 0, 4);
            gpBook.add(discountLabel, 0, 5);
            gpBook.add(libroCardPointsLabel, 0, 6);
            gpBook.add(ISBNLabel, 0, 7);

            // create information labels
            authorInfo = new Label(book.getAuthors());
            priceInfo = new Label("$ " + book.getPrice().setScale(2).toString());
            genreInfo = new Label(book.getGenre());
            publishingHouseInfo = new Label(book.getPublishingHouse());
            publishingYearInfo = new Label("" + book.getPublishingYear());
            discountInfo = new Label(book.getDiscount().toString() + " %");
            libroCardPointsInfo = new Label("" + book.getLibroCardPoints());
            ISBNInfo = new Label(book.getISBN());

            // set fonts
            authorInfo.setFont(infoFont);
            priceInfo.setFont(infoFont);
            genreInfo.setFont(infoFont);
            publishingHouseInfo.setFont(infoFont);
            publishingYearInfo.setFont(infoFont);
            discountInfo.setFont(infoFont);
            libroCardPointsInfo.setFont(infoFont);
            ISBNInfo.setFont(infoFont);

            // add labels to gridpane
            gpBook.add(titleInfo, 0, 0);
            gpBook.add(authorInfo, 1, 0);
            gpBook.add(priceInfo, 1, 1);
            gpBook.add(genreInfo, 1, 2);
            gpBook.add(publishingHouseInfo, 1, 3);
            gpBook.add(publishingYearInfo, 1, 4);
            gpBook.add(discountInfo, 1, 5);
            gpBook.add(libroCardPointsInfo, 1, 6);
            gpBook.add(ISBNInfo, 1, 7);

            // set column constraint
            ColumnConstraints labelsColumn = new ColumnConstraints();
            labelsColumn.setHalignment(HPos.LEFT);
            labelsColumn.setPrefWidth(300);

            ColumnConstraints infoColumn = new ColumnConstraints();
            infoColumn.setHalignment(HPos.LEFT);
            infoColumn.setPrefWidth(415);

            gpBook.setVgap(20);

            gpBook.getColumnConstraints().addAll(labelsColumn, infoColumn);

            // BUILD DESCRIPTION
            Label bookDescription = buildDescription(ISBN);

            // add nodes to vbox
            vbBook.getChildren().addAll(titleInfo, gpBook, bookDescription);
            VBox.setVgrow(titleInfo, Priority.ALWAYS);
            VBox.setVgrow(bookDescription, Priority.ALWAYS);

        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }

        // SET STYLESHEETS
        gpBook.setId("single-book-gridpane");
        gpBook.getStylesheets().add("/CSS/style.css");

        vbBook.setId("single-book-vbox");
        vbBook.getStylesheets().add("/CSS/style.css");

        scrollPane.setId("single-book-scrollpane");
        scrollPane.getStylesheets().add("/CSS/style.css");

        scrollPane.setContent(vbBook);
        scrollPane.setPadding(new Insets(25, 0, 0, 25));

        return scrollPane;
    }

    public static Label buildDescription(String ISBN){
        //Label descriptionLabel;
        Label bookDescription = new Label();
        String bookDescriptionString, remainingDescrString;

        //descriptionLabel = new Label("Description: \n");
        //descriptionLabel.setFont(new Font("Avenir Next Bold", 20));

        BookDAO bookDAO = new BookDaoImpl();

        Book book = null;
        try {
            book = bookDAO.getBook(ISBN);

            // split description
            bookDescriptionString = splitString(book.getDescription(), 80);

            bookDescription = new Label(bookDescriptionString);
            bookDescription.setFont(new Font("Avenir Book", 20));
            bookDescription.setMaxWidth(620);
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }

        return bookDescription;
    }

    private static String splitString(String string, int maxLength){
        //ArrayList<Integer> blankSpaceIndex = new ArrayList<>();
        String remainingString = string;
        String resultString = "";

        /*
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == ' '){
                blankSpaceIndex.add(i);
            }
        }
        */

        while(remainingString.length() >= maxLength){
            if(string.charAt(maxLength) == ' ') {
                resultString += remainingString.substring(0, maxLength) + "\n";
            } else {
                resultString += remainingString.substring(0, maxLength) + "-\n";
            }
            remainingString = remainingString.substring(maxLength);
        }

        return resultString + remainingString;
    }
}
