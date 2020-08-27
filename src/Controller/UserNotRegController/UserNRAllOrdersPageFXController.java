package Controller.UserNotRegController;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.OrderDaoImpl;
import View.UserView.UserAllOrdersPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserNRAllOrdersPageFXController implements Initializable {

    @FXML
    AnchorPane rightPane;

    @FXML
    Button goBackButton, searchButton;

    @FXML
    TextField orderIdTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    public void handleOrderSearch() {
        if(orderIdTF.getText().isEmpty()){
            Alert missingFieldAlert = new Alert(Alert.AlertType.ERROR);

            missingFieldAlert.setTitle("Missing Field");
            missingFieldAlert.setHeaderText("No orderID provided!");
            missingFieldAlert.setContentText("To track your order, please provide an orderID");

            missingFieldAlert.showAndWait();
        } else {
            OrderDAO orderDAO = new OrderDaoImpl();

            try {
                Order targetOrder = orderDAO.getOrder(orderIdTF.getText());

                if(targetOrder == null){
                    Alert orderNotFoundAlert = new Alert(Alert.AlertType.ERROR);

                    orderNotFoundAlert.setTitle("Order Not Found");
                    orderNotFoundAlert.setHeaderText("OrderID doesn't match with any order!");
                    orderNotFoundAlert.setContentText("Be sure that the orderID you provided is correct.");

                    orderNotFoundAlert.showAndWait();
                } else {
                    GridPane targetOrderGP;

                    targetOrderGP = UserAllOrdersPageView.singleOrderView(targetOrder);

                    rightPane.getChildren().add(targetOrderGP);

                    AnchorPane.setTopAnchor(targetOrderGP, (double) 40);
                    AnchorPane.setLeftAnchor(targetOrderGP, (double) 40);
                }
            } catch (SQLException | InvalidStringException | IllegalValueException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserNotRegFXML/UserNRMainPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
