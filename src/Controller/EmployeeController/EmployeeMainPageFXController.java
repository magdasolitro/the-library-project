package Controller.EmployeeController;

import Controller.GeneralLoginController;
import Model.Employee;
import Model.EmployeeRoleEnum;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DaoImpl.EmployeeDaoImpl;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeMainPageFXController implements Initializable {

    @FXML Button checkOrdersButton, checkLibroCardsButton, addBookButton,
            modifyBooksButton, registerEmployeeButton, updateRankingsButton;

    @FXML
    private Pane simplePane;

    @FXML
    private Label logoutLabel;

    @FXML
    private ImageView profileIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // add greeting message customised on the employee
        Label greetingMessageLabel = buildGreetingLabel();

        simplePane.getChildren().add(greetingMessageLabel);
        greetingMessageLabel.relocate(60, 40);

    }


    public void handleCheckOrdersRequest() {
        Stage stage = (Stage) checkOrdersButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/EmployeeFXML/AllOrdersPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleCheckLibroCardsRequest() {
        Stage stage = (Stage) checkLibroCardsButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/EmployeeFXML/AllLibroCardsFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleAddBookRequest() {
        Stage stage = (Stage) addBookButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/EmployeeFXML/AddNewBookFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleBookModificationRequest() {
        Stage stage = (Stage) modifyBooksButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/EmployeeFXML/BookModificationPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleRegisterEmployeeRequest() {
        try {
            // check if current employee has the neccessary privilegies to add a new employee
            EmployeeDAO employeeDAO = new EmployeeDaoImpl();

            Employee currentEmployee = employeeDAO.getEmployee(GeneralLoginController.getLoginInstance());

            if(!currentEmployee.getRole().equals(EmployeeRoleEnum.GENERAL_DIRECTOR.toString())
                    && !currentEmployee.getRole().equals(EmployeeRoleEnum.LOCAL_DIRECTOR.toString())){
                Alert notDirectorAlert = new Alert(Alert.AlertType.ERROR);

                notDirectorAlert.setTitle("Not A Director");
                notDirectorAlert.setHeaderText("You can't access this option!");
                notDirectorAlert.setContentText("In order to add a new employee, " +
                        "you must be either a General Director or a Local Director");

                notDirectorAlert.showAndWait();
            } else {
                viewPage("../../FXML/EmployeeFXML/AddNewEmployeeFX.fxml");
            }
        } catch (SQLException | InvalidStringException | IOException e) {
            e.printStackTrace();
        }
    }


    public void handleUpdateRankingsRequest() {
        Stage stage = (Stage) updateRankingsButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/EmployeeFXML/UpdateRankingsPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogOutRequest(MouseEvent evt) {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);

        confirmLogOut.setTitle("Log Out");
        confirmLogOut.setHeaderText("You will exit the program");
        confirmLogOut.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> response = confirmLogOut.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            Stage stage = (Stage) logoutLabel.getScene().getWindow();
            stage.close();

            GeneralLoginController.logout();

            try {
                viewPage("../../FXML/WelcomePageFX.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            evt.consume();
            confirmLogOut.close();
        }
    }


    public void goToProfilePage() {
        try{
            Stage stage = (Stage) profileIcon.getScene().getWindow();
            stage.close();

            viewPage("../../FXML/EmployeeFXML/EmployeeProfilePageFX.fxml");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


    private Label buildGreetingLabel(){
        // add greeting message customised on the employee
        Label greetingMessageLabel;
        String employeeName = "";

        EmployeeDAO employeeDAO = new EmployeeDaoImpl();

        try {
            Employee currentEmployee = employeeDAO.getEmployee(GeneralLoginController.getLoginInstance());

            employeeName = currentEmployee.getName();
        } catch (SQLException | InvalidStringException e) {
            e.printStackTrace();
        } finally{
            if(LocalTime.now().compareTo(LocalTime.NOON) < 0){
                greetingMessageLabel = new Label("Good Morning, " + employeeName + "!");
                greetingMessageLabel.setFont(new Font("Avenir Next Bold", 60));
            } else {
                greetingMessageLabel = new Label("Good Afternoon, " + employeeName + "!");
                greetingMessageLabel.setFont(new Font("Avenir Next Bold", 60));
            }
        }

        return greetingMessageLabel;
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
