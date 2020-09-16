package Controller.UserController.UserNotRegController;

import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.OrderDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserNROrderSuccessfulPageFXController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderDAO orderDAO = new OrderDaoImpl();

    }

    public void goToAllOrdersPage(ActionEvent event) {
    }

    public void handleGoBackToMainPage(ActionEvent event) {
    }
}
