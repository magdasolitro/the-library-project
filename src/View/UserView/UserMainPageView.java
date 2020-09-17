package View.UserView;

import Controller.BookInstanceController;
import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMainPageView {

    public static ScrollPane buildBooksView(ArrayList<Book> booksToShow){

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefHeight(600);
        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane bookContainerGP = new GridPane();

        int i = 0;

        for (Book b : booksToShow) {
            GridPane currentBook = buildSingleBookView(b);
            bookContainerGP.add(currentBook, 0, i);
            GridPane.setMargin(currentBook, new Insets(20, 0, 20, 30));
            i++;
        }

        bookContainerGP.setPrefWidth(1000);   // no horizontal scroll
        bookContainerGP.prefHeightProperty().bind(scrollPane.heightProperty());

        bookContainerGP.setHgap(20);

        bookContainerGP.setId("mainpage-gridpane");
        bookContainerGP.getStylesheets().add("/CSS/style.css");

        scrollPane.setContent(bookContainerGP);

        return scrollPane;

    }

    public static GridPane buildSingleBookView(Book book){
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

        priceLabel = new Label("$ " + book.getPrice().setScale(2, RoundingMode.FLOOR));
        priceLabel.setFont(genericLabelFont);

        if(book.getDiscount().compareTo(new BigDecimal(0)) > 0) {
            discountLabel = new Label("Discount: " + book.getDiscount() + " %");
            discountLabel.setFont(genericLabelFont);
        } else {
            discountLabel = new Label();
        }

        // check if book is available: if not, display a message
        BookDAO bookDAO = new BookDaoImpl();

        try{
            if(!bookDAO.isAvailable(book.getISBN())){
                notAvailableLabel = new Label("Out of stock");
                notAvailableLabel.setFont(new Font("Avenir Book", 20));
                notAvailableLabel.setTextFill(Color.RED);

                singleBook.add(notAvailableLabel, 1,2);
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        }

        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(publishingYearLabel, 0, 2);
        singleBook.add(genreLabel, 0, 3);

        singleBook.add(priceLabel, 1, 0);
        singleBook.add(discountLabel, 1 ,1);

        singleBook.getColumnConstraints().addAll(new ColumnConstraints(450), new ColumnConstraints(450));

        singleBook.setVgap(10);

        singleBook.setOnMouseClicked(e -> {
            BookInstanceController.setCurrentBookISBN(book.getISBN());

            try{
                Stage stage = (Stage) singleBook.getScene().getWindow();
                stage.close();

                viewPage("/FXML/UserFXML/SingleBookPageFX.fxml");
            } catch (IOException ioe){
                System.out.println("IOException" + ioe.getMessage());
            }
        });

        return singleBook;
    }


    private static void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserMainPageView.class.getResource(path));
        Parent root = loader.load();

        if(GeneralLoginController.getLoginInstance().substring(0,6).equals("NOTREG")){
            LastOpenedPageController.setLastOpenedPage("/FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml");
        } else {
            LastOpenedPageController.setLastOpenedPage("/FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
