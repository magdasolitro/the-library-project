package Controller;

import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.User;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.UserDaoImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignInPageFXController {
    @FXML
    TextField nameField, surnameField, addressField, streetNumber,
            ZIPCodeField, cityField, phoneField, emailField, passwordField,
            passwordAgainField;

    @FXML
    public void handleSignInButton(MouseEvent evt) throws InvalidStringException,
            UserNotInDatabaseException, SQLException, IOException {

        // check if all the fields have been filled
        if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() ||
                addressField.getText().isEmpty() || streetNumber.getText().isEmpty() ||
                ZIPCodeField.getText().isEmpty() || cityField.getText().isEmpty() ||
                phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() || passwordAgainField.getText().isEmpty()){

            Alert missingField = new Alert(Alert.AlertType.ERROR, "Please, " +
                    "fill all the required fields", ButtonType.OK);

            missingField.showAndWait();

            return;
        }

        // check if password matches in both password fields
        if(!passwordField.getText().equals(passwordAgainField.getText())){
            Alert missingField = new Alert(Alert.AlertType.ERROR, "Password fields " +
                    "do not match! Try to spell it again.", ButtonType.OK);

            missingField.showAndWait();

            return;
        }

        UserDAO userDAO = new UserDaoImpl();

        // check if user is already in db
        if(userDAO.getUser(emailField.getText()) != null ){
            Alert missingField = new Alert(Alert.AlertType.ERROR, "You are already " +
                    "registred! Try to log in.", ButtonType.OK);

            missingField.showAndWait();

            return;
        }

        User newUser = new User(nameField.getText(), surnameField.getText(),
                phoneField.getText(), emailField.getText(), passwordField.getText(),
                addressField.getText(), streetNumber.getText(),
                ZIPCodeField.getText(), cityField.getText());

        userDAO.addUser(newUser);

        ((Button) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/UserMainPageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void handleGoBackButton(MouseEvent evt) throws IOException {
        ((Button) evt.getSource()).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/WelcomePageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
