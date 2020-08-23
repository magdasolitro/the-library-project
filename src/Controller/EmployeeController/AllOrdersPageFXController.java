package Controller.EmployeeController;

import Controller.LastOpenedPageController;
import View.EmployeeView.AllOrdersPageView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllOrdersPageFXController implements Initializable {

    @FXML
    private ChoiceBox<String> statusCB;

    @FXML
    private Button goBackButton;

    @FXML
    private AnchorPane rightPane;

    private ScrollPane scrollPane = new ScrollPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        statusCB.getItems().addAll(FXCollections.observableArrayList("Order Request Received", "In Preparation",
                "Shipped", "Delivered", "Unable to Deliver", "Lost"));

        //statusCB.getSelectionModel().selectedIndexProperty().addListener();
        GridPane allOrdersGP = AllOrdersPageView.buildAllOrdersView();

        allOrdersGP.setVgap(70);

        scrollPane.setContent(allOrdersGP);
        scrollPane.setId("ordersummary-scrollpane");
        scrollPane.getStylesheets().add("/CSS/style.css");

        scrollPane.prefWidthProperty().bind(rightPane.widthProperty());
        allOrdersGP.prefWidthProperty().bind(scrollPane.widthProperty());

        scrollPane.setPadding(new Insets(20, 0, 0, 20));
        AnchorPane.setTopAnchor(scrollPane, (double) 0);
        AnchorPane.setBottomAnchor(scrollPane, (double) 0);
        AnchorPane.setRightAnchor(scrollPane, (double) 0);
        AnchorPane.setLeftAnchor(scrollPane, (double) 0);

        rightPane.getChildren().add(scrollPane);
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
