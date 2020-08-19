package Controller;

import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.LibroCard;
import Model.User;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.LibroCardDaoImpl;
import Model.Utils.DaoImpl.UserDaoImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInPageFXController implements Initializable {
    @FXML
    TextField nameField, surnameField, addressField, streetNumber,
            ZIPCodeField, cityField, phoneField, emailField, passwordField,
            passwordAgainField;

    @FXML
    Button signInButton, goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }

    @FXML
    public void handleSignInButton(MouseEvent evt) throws InvalidStringException,
            UserNotInDatabaseException, SQLException, IOException {
        UserDAO userDAO = new UserDaoImpl();

        // check if all the fields have been filled
        if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() ||
                addressField.getText().isEmpty() || streetNumber.getText().isEmpty() ||
                ZIPCodeField.getText().isEmpty() || cityField.getText().isEmpty() ||
                phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() || passwordAgainField.getText().isEmpty()){

            Alert missingField = new Alert(Alert.AlertType.ERROR);

            missingField.setTitle("Fields Error");
            missingField.setHeaderText("You did not fill all the fields!");
            missingField.setContentText("To register successfully, please " +
                    "provide all the requested informations");

            missingField.showAndWait();

        }

        // check if password matches in both password fields
        else if(!passwordField.getText().equals(passwordAgainField.getText())){
            Alert notMatchingFields = new Alert(Alert.AlertType.ERROR);

            notMatchingFields.setTitle("Fields Error");
            notMatchingFields.setHeaderText("Password fields do not match!");
            notMatchingFields.setContentText("Try to spell the password again.");

            notMatchingFields.showAndWait();

        }

        // check if user is already in db
        else if(userDAO.getUser(emailField.getText()) != null ){
            Alert alreadyRegistred = new Alert(Alert.AlertType.ERROR);

            alreadyRegistred.setTitle("Registration Error");
            alreadyRegistred.setHeaderText("You are already registred!");
            alreadyRegistred.setContentText("Please, try to log in.");

            alreadyRegistred.showAndWait();
        }

        // everything is ok, i can add the user
        else {

            // add new user (LibroCard is added automatically inside addUser method)
            User newUser = new User(nameField.getText(), surnameField.getText(),
                    phoneField.getText(), emailField.getText(), passwordField.getText(),
                    addressField.getText(), streetNumber.getText(),
                    ZIPCodeField.getText(), cityField.getText());

            userDAO.addUser(newUser);

            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

            LibroCard userLibroCard = new LibroCard(emailField.getText());
            libroCardDAO.addLibroCard(userLibroCard);

            GeneralLoginController.setLoginInstance(emailField.getText());

            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.close();

            viewPage("../FXML/UserFXML/UserMainPageFX.fxml");
        }
    }

    @FXML
    public void handleGoBackButton() throws IOException {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        viewPage("../FXML/WelcomePageFX.fxml");
    }


    private void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
