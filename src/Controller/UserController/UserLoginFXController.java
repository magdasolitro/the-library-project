package Controller.UserController;

import Model.Exceptions.InvalidStringException;
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

public class UserLoginFXController {
    @FXML
    TextField userEmailField;

    @FXML
    TextField userPasswordField;

    @FXML
    public void handleLoginInfos(MouseEvent evt) throws SQLException,
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
            Alert userNotRegistred = new Alert(Alert.AlertType.ERROR, "It looks " +
                    "like you are not registred!", ButtonType.OK);
            userNotRegistred.show();
        } else if(!userPassword.equals(currentUser.getPassword())){
            Alert wrongPassword = new Alert(Alert.AlertType.ERROR, "Your " +
                    "password is wrong!", ButtonType.OK);
            wrongPassword.show();
        } else {
            ((Button) evt.getSource()).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../FXML/UserFXML/UserMainPageFX.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }
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
