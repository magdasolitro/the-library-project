package Controller.EmployeeController;

import Controller.GeneralLoginController;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.UserDaoImpl;
import View.EmployeeView.EmployeeProfileView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeProfilePageFXController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label logoutLabel, employeeNameSurnameLabel;

    @FXML
    private ImageView userIcon;

    @FXML
    private Button goBackButton, deleteAccountButton;

    private GridPane gpEmployeeInfos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gpEmployeeInfos = EmployeeProfileView.buildEmployeeInfosGrid(GeneralLoginController.getLoginInstance());
        employeeNameSurnameLabel = EmployeeProfileView.buildEmployeeNameSurnameLabel((GeneralLoginController.getLoginInstance()));

        anchorPane.getChildren().addAll(gpEmployeeInfos, employeeNameSurnameLabel);

        AnchorPane.setLeftAnchor(gpEmployeeInfos, (double) 280);
        AnchorPane.setTopAnchor(gpEmployeeInfos, (double) 250);

        AnchorPane.setLeftAnchor(employeeNameSurnameLabel, (double) 90);
        AnchorPane.setTopAnchor(employeeNameSurnameLabel, (double) 100);

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    public void handleDeleteAccountRequest(MouseEvent mouseEvent) {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Delete Account");
        confirmLogOut.setHeaderText("All you your informations will be deleted permanently");
        confirmLogOut.setContentText("Are you sure you want to delete your account?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            try {
                UserDAO userDAO = new UserDaoImpl();

                userDAO.deleteUser(GeneralLoginController.getLoginInstance());

                GeneralLoginController.logout();

                try {
                    Stage stage = (Stage) deleteAccountButton.getScene().getWindow();
                    stage.close();

                    viewPage("/FXML/WelcomePageFX.fxml");
                } catch (IOException ioe) {
                    System.out.println("IOException: " + ioe.getMessage());
                }
            } catch(SQLException sqle){
                sqle.getStackTrace();
            }
        } else {
            mouseEvent.consume();
            confirmLogOut.close();
        }
    }


    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try{
            viewPage("/FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    public void handleLogOutRequest(MouseEvent mouseEvent) {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Log Out");
        confirmLogOut.setHeaderText("You will exit the program");
        confirmLogOut.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {

            GeneralLoginController.logout();

            Stage stage = (Stage) logoutLabel.getScene().getWindow();
            stage.close();

            try {
                viewPage("/FXML/WelcomePageFX.fxml");
            } catch (IOException ioe) {
                ioe.getStackTrace();
            }
        } else {
            mouseEvent.consume();
            confirmLogOut.close();
        }
    }


    public void goToProfilePage() {
        try{
            Stage stage = (Stage) userIcon.getScene().getWindow();
            stage.close();

            viewPage("/FXML/EmployeeFXML/EmployeeProfilePageFX.fxml");
        } catch(IOException ioe){
            ioe.getStackTrace();
        }
    }


    private void viewPage(String path) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        //LastOpenedPageController.setLastOpenedPage("/FXML/EmployeeFXML/EmployeeProfilePageFX.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
}
