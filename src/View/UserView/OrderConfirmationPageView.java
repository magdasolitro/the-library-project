package View.UserView;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DaoImpl.CartDaoImpl;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderConfirmationPageView {

    public static ScrollPane buildOrderSummary(String email){
        ScrollPane scrollPane = new ScrollPane();
        GridPane gpContainer = new GridPane();

        int i = 0;

        CartDAO cartDAO = new CartDaoImpl();
        ArrayList<Book> booksInCart = new ArrayList<>();

        try{
            booksInCart.addAll(cartDAO.cartContent(email));
        } catch (SQLException | InvalidStringException | IllegalValueException sqle){
            sqle.printStackTrace();
        }

        for (Book b : booksInCart) {
            GridPane currentBook = UserMainPageView.buildSingleBookView(b);
            gpContainer.add(currentBook, 0, i);
            GridPane.setMargin(currentBook, new Insets(20, 0, 20, 30));
            i++;
        }

        return scrollPane;
    }



}
