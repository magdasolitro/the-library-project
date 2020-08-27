package Controller.UserNotRegController;

import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.OrderDaoImpl;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserNROrderSuccessfulPageFXController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderDAO orderDAO = new OrderDaoImpl();

    }
}
