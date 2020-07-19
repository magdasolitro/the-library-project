package Controller.UserController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Exceptions.InvalidStringException;
import Model.User;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.UserDaoImpl;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserLoginFXController implements Initializable {

    @FXML
    TextField userEmailField, userPasswordField;

    @FXML
    Button loginButton, goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    @FXML
    public void handleLoginRequest(MouseEvent evt) throws SQLException,
            InvalidStringException, IOException {

        if(userEmailField.getText().isEmpty() || userPasswordField.getText().isEmpty()){
            Alert missingFields = new Alert(Alert.AlertType.ERROR, "You " +
                    "did not fill all the fields!", ButtonType.OK);

            missingFields.showAndWait();

            return;
        }

        String userEmail = userEmailField.getText();
        String userPassword = userPasswordField.getText();

        UserDAO userDAO = new UserDaoImpl();

        User currentUser = userDAO.getUser(userEmail);

        if(currentUser == null){
            Alert userNotRegistred = new Alert(Alert.AlertType.ERROR);

            userNotRegistred.setTitle("User Not Registered");
            userNotRegistred.setHeaderText("It looks like you are not registered!");
            userNotRegistred.setContentText("Try to sign in instead.");

            userNotRegistred.show();
        } else if(!userPassword.equals(currentUser.getPassword())){
            Alert wrongPassword = new Alert(Alert.AlertType.ERROR);

            wrongPassword.setTitle("Wrong Password");
            wrongPassword.setHeaderText("Your password is wrong!");
            wrongPassword.setContentText("Try to re-type it.");

            wrongPassword.show();
        } else {
            GeneralLoginController.setLoginInstance(userEmail);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/UserMainPageFX.fxml");
        }
    }


    @FXML
    public void handleGoBackButtonClick() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        viewPage("../../FXML/WelcomePageFX.fxml");
    }


    private void viewPage(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserLoginFX.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
