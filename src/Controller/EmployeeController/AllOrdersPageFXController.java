package Controller.EmployeeController;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.OrderDaoImpl;
import View.EmployeeView.AllOrdersPageView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllOrdersPageFXController implements Initializable {


    @FXML
    private Button goBackButton;

    @FXML
    private AnchorPane rightPane, leftPane;

    private ScrollPane scrollPane;

    private ChoiceBox<Object> statusCB;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        OrderDAO orderDAO = new OrderDaoImpl();


        try {
            scrollPane = AllOrdersPageView.buildAllOrdersView(orderDAO.getAllOrders());
        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }

        rightPane.getChildren().add(scrollPane);

        AnchorPane.setTopAnchor(scrollPane, (double) 0);
        AnchorPane.setBottomAnchor(scrollPane, (double) 0);

        statusCB = new ChoiceBox<>(FXCollections.observableArrayList("Order Request Received", "In Preparation",
                "Shipped", "Delivered", "Unable to Deliver", "Lost"));

        statusCB.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {

            try {
                ArrayList<Order> ordersByStatus = orderDAO.getOrdersByStatus(OrderStatusEnum.values()[newValue.intValue()]);

                if(ordersByStatus.isEmpty()){
                    Alert noOrdersToDisplay = new Alert(Alert.AlertType.ERROR);

                    noOrdersToDisplay.setTitle("No Orders To Display");
                    noOrdersToDisplay.setHeaderText("There are no orders with the specified status.");

                    noOrdersToDisplay.showAndWait();
                } else {
                    rightPane.getChildren().remove(scrollPane);

                    scrollPane = AllOrdersPageView.buildAllOrdersView(ordersByStatus);

                    rightPane.getChildren().add(scrollPane);

                    AnchorPane.setTopAnchor(scrollPane, (double) 0);
                    AnchorPane.setBottomAnchor(scrollPane, (double) 0);
                }
            } catch (SQLException | InvalidStringException | IllegalValueException e) {
                e.printStackTrace();
            }
        });

        leftPane.getChildren().add(statusCB);
        statusCB.relocate(35, 245);
        statusCB.setPrefWidth(250);
        statusCB.setPrefHeight(36);
    }


    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try{
            viewPage("../../FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
        } catch(IOException ioe){
            ioe.printStackTrace();
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
