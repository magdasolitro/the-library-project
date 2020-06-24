package Controller.UserController;

import Controller.GeneralLoginController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DaoImpl.CartDaoImpl;
import View.UserView.UserMainPageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserCartPageFXController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane = new ScrollPane();

        // build full cart view
        try {
            CartDAO cartDAO = new CartDaoImpl();

            ArrayList<Book> booksInCart = new ArrayList<>(cartDAO.cartContent(GeneralLoginController.getLoginInstance()));

            scrollPane = UserMainPageView.buildBooksView(booksInCart);
            scrollPane.setPrefHeight(500);
            scrollPane.setPrefWidth(836);

            anchorPane.getChildren().add(scrollPane);
            AnchorPane.setTopAnchor(scrollPane, (double) 200);
            AnchorPane.setLeftAnchor(scrollPane, (double) 90);
        } catch (InvalidStringException | SQLException | IllegalValueException e) {
            e.printStackTrace();
        }
    }
}
