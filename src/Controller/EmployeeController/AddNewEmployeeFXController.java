package Controller.EmployeeController;

import Model.Employee;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DaoImpl.EmployeeDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddNewEmployeeFXController implements Initializable {
    @FXML
    Button goBackButton;

    @FXML
    ChoiceBox<String> roleChoiceBox;

    @FXML
    TextField emailField, nameField, surnameField, birthDateField, passwordField,
            repeatPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set possible choices in drop down menu
        roleChoiceBox.getItems().addAll("General Director", "Local Director",
                "Book Seller", "Cashier");

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("/FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleRegistrationRequest() {
        // check presence and validity of all fields
        if(emailField.getText().isEmpty() || nameField.getText().isEmpty()
                || surnameField.getText().isEmpty() || birthDateField.getText().isEmpty()
                || roleChoiceBox.getValue().isEmpty() || passwordField.getText().isEmpty()
                || repeatPasswordField.getText().isEmpty()){
            Alert missingFields = new Alert(Alert.AlertType.ERROR);

            missingFields.setTitle("Fields Error");
            missingFields.setHeaderText("You did not fill all the fields!");
            missingFields.setContentText("To register a new employee successfully, " +
                    "you must provide all the requested informations");

            Optional<ButtonType> result = missingFields.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        }

        if(!checkDateValidity(birthDateField.getText())){
            Alert invalidDateFormatAlert = new Alert(Alert.AlertType.ERROR);

            invalidDateFormatAlert.setTitle("Invalid Date Format");
            invalidDateFormatAlert.setHeaderText("The date you inserted is not " +
                    "in a valid format");
            invalidDateFormatAlert.setContentText("Please, be sure the date " +
                    "follows the format dd/mm/yyyy");

            Optional<ButtonType> result = invalidDateFormatAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        }

        if(!passwordField.getText().equals(repeatPasswordField.getText())){
            Alert notMatchingFields = new Alert(Alert.AlertType.ERROR);

            notMatchingFields.setTitle("Fields Error");
            notMatchingFields.setHeaderText("Password fields do not match!");
            notMatchingFields.setContentText("Try to spell the password again.");

            notMatchingFields.showAndWait();

            Optional<ButtonType> result = notMatchingFields.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        }

        Alert confirmRegistrationAlert = new Alert(Alert.AlertType.CONFIRMATION);

        confirmRegistrationAlert.setTitle("Confirm Registration");
        confirmRegistrationAlert.setHeaderText("Do you want to confirm the registration?");
        confirmRegistrationAlert.setContentText("If you confirm, a new employee will be added.");

        Optional<ButtonType> response = confirmRegistrationAlert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.OK) {
            EmployeeDAO employeeDAO = new EmployeeDaoImpl();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Employee newEmployee = new Employee(emailField.getText(),
                        nameField.getText(), surnameField.getText(),
                        birthDateField.getText(), roleChoiceBox.getValue(),
                        formatter.format(calendar.getTime()),
                        passwordField.getText());

                employeeDAO.addEmployee(newEmployee);
            } catch (InvalidStringException | SQLException e) {
                e.printStackTrace();
            } finally {
                Alert successfulRegistrationAlert = new Alert(Alert.AlertType.INFORMATION);

                successfulRegistrationAlert.setTitle("Successful Registration");
                successfulRegistrationAlert.setHeaderText("New employee added succesfully!");

                successfulRegistrationAlert.showAndWait();
            }
        }
    }


    private boolean checkDateValidity(String dateStr) {
        String dateFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }

        return true;

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
