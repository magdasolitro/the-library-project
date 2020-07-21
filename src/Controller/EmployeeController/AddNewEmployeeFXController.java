package Controller.EmployeeController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewEmployeeFXController implements Initializable {
    @FXML
    Button goBackButton;

    @FXML
    RadioButton generalDirectorRB, localDirectorRB, bookSellerRB, cashierRB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();

        generalDirectorRB.setToggleGroup(group);
        localDirectorRB.setToggleGroup(group);
        bookSellerRB.setToggleGroup(group);
        cashierRB.setToggleGroup(group);

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    public void handleGoBackButton() {

    }
}
