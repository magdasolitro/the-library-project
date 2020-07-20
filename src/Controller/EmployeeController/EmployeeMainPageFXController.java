package Controller.EmployeeController;

import Controller.GeneralLoginController;
import Model.Employee;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.EmployeeDAO;
import Model.Utils.DaoImpl.EmployeeDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EmployeeMainPageFXController implements Initializable {

    @FXML
    public AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // add greeting message customised on the employee
        Label greetingMessageLabel = buildGreetingLabel();

        mainPane.getChildren().add(greetingMessageLabel);
        AnchorPane.setTopAnchor(greetingMessageLabel, (double) 40);
        AnchorPane.setLeftAnchor(greetingMessageLabel, (double) 80);
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
                greetingMessageLabel = new Label("Good Morning, " + employeeName);
                greetingMessageLabel.setFont(new Font("Avenir Next Bold", 60));
            } else {
                greetingMessageLabel = new Label("Good Afternoon, " + employeeName);
                greetingMessageLabel.setFont(new Font("Avenir Next Bold", 60));
            }
        }

        return greetingMessageLabel;
    }
}
