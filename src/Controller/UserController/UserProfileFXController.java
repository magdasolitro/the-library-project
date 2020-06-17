package Controller.UserController;

import Controller.GeneralLoginController;
import View.UserView.UserProfileView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserProfileFXController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @FXML
    Button deleteAccountButton, logoutButton, modifyDataButton, myOrdersButton,
            myLibroCardButton;

    GridPane gpUserInfos;

    Label userNameSurnameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gpUserInfos = UserProfileView.buildUserInfos(GeneralLoginController.getLoginInstance());
        userNameSurnameLabel = UserProfileView.buildUserNameSurnameLabel((GeneralLoginController.getLoginInstance()));

        anchorPane.getChildren().addAll(gpUserInfos, userNameSurnameLabel);

        AnchorPane.setLeftAnchor(gpUserInfos, (double) 150);
        AnchorPane.setTopAnchor(gpUserInfos, (double) 290);

        AnchorPane.setLeftAnchor(userNameSurnameLabel, (double) 90);
        AnchorPane.setTopAnchor(userNameSurnameLabel, (double) 125);

    }


    public void goToLibroCardPage(MouseEvent mouseEvent) {
        try{
            Stage stage = (Stage) myLibroCardButton.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/LibroCardPageFX.fxml");
        } catch(IOException ioe){
            System.out.println("IOException: " + ioe.getMessage());
        }

    }


    public void goToOrdersPage(MouseEvent mouseEvent) {
        try{
            Stage stage = (Stage) myOrdersButton.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/UserFXML/OrdersPageFX.fxml");
        } catch(IOException ioe){
            System.out.println("IOException: " + ioe.getMessage());
        }
    }


    public void handleModificationRequest(MouseEvent mouseEvent) {

    }


    public void handleLogOutRequest(MouseEvent mouseEvent) {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Log Out");
        confirmLogOut.setHeaderText("You will exit the program");
        confirmLogOut.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();

            GeneralLoginController.logout();

            try {
                viewPage("../../FXML/WelcomePageFX.fxml");
            } catch(IOException ioe){
                System.out.println("IOException: " + ioe.getMessage());
            }
        } else {
            mouseEvent.consume();
            confirmLogOut.close();
        }
    }


    public void handleDeleteAccountRequest(MouseEvent mouseEvent) {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Delete Account");
        confirmLogOut.setHeaderText("All you your informations will be deleted permanently and" +
                "you will never be able to access your account again.");
        confirmLogOut.setContentText("Are you sure you want to delete your account?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            ((Button) mouseEvent.getSource()).getScene().getWindow().hide();

            GeneralLoginController.logout();

            try {
                Stage stage = (Stage) deleteAccountButton.getScene().getWindow();
                stage.close();

                viewPage("../../FXML/WelcomePageFX.fxml");
            } catch(IOException ioe){
                System.out.println("IOException: " + ioe.getMessage());
            }

        } else {
            mouseEvent.consume();
            confirmLogOut.close();
        }
    }


    private void viewPage(String path) throws IOException{
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
