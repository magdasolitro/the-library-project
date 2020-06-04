package Controller.UserController;

import Model.Exceptions.InvalidStringException;
import Model.User;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.UserDaoImpl;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UserLoginFXController {
    @FXML
    TextField userEmailField;

    @FXML
    TextField userPasswordField;

    @FXML
    public void handleLoginInfos(MouseEvent evt) throws SQLException,
            InvalidStringException {

        String userEmail = userEmailField.getText();
        String userPassword = userPasswordField.getText();

        UserDAO userDAO = new UserDaoImpl();

        User currentUser = userDAO.getUser(userEmail);

        System.out.println(currentUser);
    }

    @FXML
    public void handleGoBackButtonClick(MouseEvent evt) throws IOException {
        ((Button) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../FXML/WelcomePageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
