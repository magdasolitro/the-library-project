package Controller.UserController.UserRegController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.LibroCardDaoImpl;
import Model.Utils.DaoImpl.UserDaoImpl;
import View.UserView.LibroCardPageView;
import View.UserView.UserProfileView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserProfileFXController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @FXML
    Button deleteAccountButton, modifyDataButton, myOrdersButton,
            myLibroCardButton, goBackButton;

    @FXML
    ImageView cartIcon;

    @FXML
    Label logoutLabel, userNameSurnameLabel;

    GridPane gpUserInfos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gpUserInfos = UserProfileView.buildUserInfosGrid(GeneralLoginController.getLoginInstance());
        userNameSurnameLabel = UserProfileView.buildUserNameSurnameLabel((GeneralLoginController.getLoginInstance()));

        anchorPane.getChildren().addAll(gpUserInfos, userNameSurnameLabel);

        AnchorPane.setLeftAnchor(gpUserInfos, (double) 260);
        AnchorPane.setTopAnchor(gpUserInfos, (double) 250);

        AnchorPane.setLeftAnchor(userNameSurnameLabel, (double) 90);
        AnchorPane.setTopAnchor(userNameSurnameLabel, (double) 100);

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

    }


    public void viewLibroCard() {
        DialogPane libroCardDialog = new DialogPane();

        Pane libroCardPane = LibroCardPageView.buildLibroCardView(GeneralLoginController.getLoginInstance());

        Label headerText = new Label("Your LibroCard");
        headerText.setFont(new Font("Avenir Next Bold", 50));

        libroCardDialog.setHeader(headerText);
        libroCardDialog.setContent(libroCardPane);

        libroCardDialog.setPadding(new Insets(40, 0, 0, 70));

        libroCardDialog.isResizable();

        libroCardDialog.setId("librocard-pane");
        libroCardDialog.getStylesheets().add("/CSS/style.css");

        Scene scene = new Scene(libroCardDialog);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(600);
        stage.show();

    }


    public void goToOrdersPage() {
        try{
            Stage stage = (Stage) myOrdersButton.getScene().getWindow();
            stage.close();

            viewPage("../../../FXML/UserFXML/UserRegFXML/UserAllOrdersPageFX.fxml");
        } catch(IOException ioe){
            ioe.getStackTrace();
        }
    }

    public void goToCartPage() {
        try{
            Stage stage = (Stage) cartIcon.getScene().getWindow();
            stage.close();

            viewPage("../../../FXML/UserFXML/CartPageFX.fxml");
        } catch(IOException ioe){
            ioe.getStackTrace();
        }
    }


    public void handleModificationRequest() {
        try{
            Stage stage = (Stage) modifyDataButton.getScene().getWindow();
            stage.close();

            viewPage("../../../FXML/UserFXML/UserRegFXML/DataModificationPageFX.fxml");
        } catch(IOException ioe){
            ioe.getStackTrace();
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
                viewPage("../../../FXML/WelcomePageFX.fxml");
            } catch (IOException ioe) {
                ioe.getStackTrace();
            }
        } else {
            mouseEvent.consume();
            confirmLogOut.close();
        }
    }


    public void handleDeleteAccountRequest(MouseEvent mouseEvent) {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Delete Account");
        confirmLogOut.setHeaderText("Are you sure you want to delete your account?");
        confirmLogOut.setContentText("All of your informations will we deleted");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            try {
                UserDAO userDAO = new UserDaoImpl();
                LibroCardDAO libroCardDAO = new LibroCardDaoImpl();
                String currentUser = GeneralLoginController.getLoginInstance();

                LibroCard userLibroCard = libroCardDAO.getUserLibroCard(currentUser);

                userDAO.deleteUser(currentUser);
                libroCardDAO.deleteLibroCard(userLibroCard.getCardID());

                GeneralLoginController.logout();

                Stage stage = (Stage) deleteAccountButton.getScene().getWindow();
                stage.close();

                viewPage("../../../FXML/WelcomePageFX.fxml");

            } catch(SQLException | IOException e){
                e.getStackTrace();
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
            viewPage(LastOpenedPageController.getLastOpenedPage());
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    private void viewPage(String path) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        if(GeneralLoginController.getLoginInstance().substring(0,6).equals("NOTREG")) {
            LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml");
        } else {
            LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml");
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

}
