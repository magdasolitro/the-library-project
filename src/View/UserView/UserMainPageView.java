package View.UserView;

import Controller.BookInstanceController;
import Model.Book;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMainPageView {

    public static ScrollPane buildBooksView(ArrayList<Book> booksToShow){

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(1050);
        scrollPane.setPrefHeight(600);
        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane bookContainer = new GridPane();

        int i = 0;

        for (Book b : booksToShow) {
            GridPane currentBook = buildSingleBookView(b);
            bookContainer.add(currentBook, 0, i);
            GridPane.setMargin(currentBook, new Insets(20, 0, 20, 30));
            i++;
        }

        scrollPane.setContent(bookContainer);

        bookContainer.prefWidthProperty().bind(scrollPane.widthProperty());
        bookContainer.prefHeightProperty().bind(scrollPane.heightProperty());

        return scrollPane;

    }

    private static GridPane buildSingleBookView(Book book){
        GridPane singleBook = new GridPane();

        // this id will be used in the css file
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

        RowConstraints priceRow = new RowConstraints();
        RowConstraints discountRow = new RowConstraints();
        RowConstraints notAvailableRow = new RowConstraints();

        priceRow.setValignment(VPos.CENTER);
        priceRow.setPercentHeight(100.0/3);

        discountRow.setValignment(VPos.CENTER);
        priceRow.setPercentHeight(100.0/3);

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

        singleBook.isResizable();

        singleBook.add(titleLabel, 0, 0);
        singleBook.add(authorLabel, 0, 1);
        singleBook.add(publishingYearLabel, 0, 2);
        singleBook.add(genreLabel, 0, 3);

        singleBook.add(priceLabel, 1, 0);
        singleBook.add(discountLabel, 1 ,1);

        // adds contraints to grid pane
        singleBook.getColumnConstraints().addAll(column1, column2);
        singleBook.getRowConstraints().addAll(titleRow, authorRow, publishingYearRow,
                priceRow, discountRow, notAvailableRow);

        singleBook.setOnMouseClicked(e -> {
            BookInstanceController.setCurrentBookISBN(book.getISBN());

            try{
                Stage stage = (Stage) singleBook.getScene().getWindow();
                stage.close();

                viewPage("../../FXML/UserFXML/SingleBookPageFX.fxml");
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

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }


}
