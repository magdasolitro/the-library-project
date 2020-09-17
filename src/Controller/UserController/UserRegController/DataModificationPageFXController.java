package Controller.UserController.UserRegController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class DataModificationPageFXController implements Initializable {

    @FXML
    Button confirmChangesButton, cancelChangesButton, goBackButton;

    @FXML
    TextField newAddressField, newStreetNumberField, newZIPCodeField, newCityField,
            newPhoneField, newPasswordField, retypePasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }

    public void confirmChanges(MouseEvent mouseEvent) {

        if(!newPasswordField.getText().isEmpty() &&
                !newPasswordField.getText().equals(retypePasswordField.getText())){
            Alert notMatchingFields = new Alert(Alert.AlertType.ERROR);

            notMatchingFields.setTitle("Fields Error");
            notMatchingFields.setHeaderText("Password fields do not match!");
            notMatchingFields.setContentText("Try to spell the password again.");

            notMatchingFields.showAndWait();
        } else {
            Alert confirmChanges = new Alert(Alert.AlertType.CONFIRMATION);

            confirmChanges.setTitle("Confirm Changes");
            confirmChanges.setHeaderText("Your data will be updated");
            confirmChanges.setContentText("Are you sure you want to confirm this?");

            Optional<ButtonType> response = confirmChanges.showAndWait();

            if (response.isPresent() && response.get() == ButtonType.OK) {
                UserDAO userDAO = new UserDaoImpl();

                if(!newAddressField.getText().isEmpty()){
                    try {
                        userDAO.updateHomeAddress(GeneralLoginController.getLoginInstance(),
                                newAddressField.getText());
                    } catch(SQLException sqle){
                        sqle.getStackTrace();
                    }
                }

                if(!newStreetNumberField.getText().isEmpty()){
                    try {
                        userDAO.updateStreetNumber(GeneralLoginController.getLoginInstance(),
                                newStreetNumberField.getText());
                    } catch(SQLException sqle){
                        sqle.getStackTrace();
                    }
                }

                if(!newZIPCodeField.getText().isEmpty()){
                    try {
                        userDAO.updateZIPCode(GeneralLoginController.getLoginInstance(),
                                newZIPCodeField.getText());
                    } catch(SQLException sqle){
                        sqle.getStackTrace();
                    }
                }

                if(!newCityField.getText().isEmpty()){
                    try {
                        userDAO.updateHomeCity(GeneralLoginController.getLoginInstance(),
                                newCityField.getText());
                    } catch(SQLException sqle){
                        sqle.getStackTrace();
                    }
                }

                if(!newPhoneField.getText().isEmpty()){
                    try {
                        userDAO.updatePhone(GeneralLoginController.getLoginInstance(),
                                newPhoneField.getText());
                    } catch(SQLException sqle){
                        sqle.getStackTrace();
                    }
                }

                if(!newPasswordField.getText().isEmpty()){
                    try {
                        userDAO.updatePassword(GeneralLoginController.getLoginInstance(),
                                newPasswordField.getText());
                    } catch(SQLException sqle){
                        sqle.getStackTrace();
                    }
                }

                Stage stage = (Stage) confirmChangesButton.getScene().getWindow();
                stage.close();

                try {
                    viewPage("/FXML/UserFXML/UserRegFXML/UserProfileFX.fxml");
                } catch (IOException ioe) {
                    ioe.getStackTrace();
                }
            } else {
                mouseEvent.consume();
                confirmChanges.close();
            }
        }
    }


    public void cancelChanges(MouseEvent mouseEvent) {
        Alert cancelChanges = new Alert(Alert.AlertType.CONFIRMATION);

        cancelChanges.setTitle("Cancel Changes");
        cancelChanges.setHeaderText("Your data will be left unchanged!");
        cancelChanges.setContentText("Are you sure you do not want to commit " +
                "your changes and go back to the main profile page?");

        Optional<ButtonType> response = cancelChanges.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelChangesButton.getScene().getWindow();
            stage.close();

            try {
                viewPage("/FXML/UserFXML/UserRegFXML/UserProfileFX.fxml");
            } catch (IOException ioe){
                ioe.getStackTrace();
            }
        }
    }

    public void handleGoBackButtonClick() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("/FXML/UserFXML/UserRegFXML/UserProfileFX.fxml");
        } catch (IOException ioe){
            ioe.getStackTrace();
        }
    }

    private void viewPage(String path) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("/FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

}
