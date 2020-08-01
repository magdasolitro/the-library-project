package Controller.EmployeeController;

import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DaoImpl.LibroCardDaoImpl;
import View.EmployeeView.AllLibroCardsPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllLibroCardsPageFXController implements Initializable {
    @FXML
    private Button searchButton;

    @FXML
    private RadioButton userEmailRB, cardIDRB;

    @FXML
    private AnchorPane leftPane, rightPane;

    @FXML
    private Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();

        userEmailRB.setToggleGroup(group);
        cardIDRB.setToggleGroup(group);

        // set default radiobutton to "user email"
        group.selectToggle(userEmailRB);

        // set style for goBackButton
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        Label insertLabel = new Label("Insert");
        insertLabel.setFont(new Font("Avenir Book", 17));


        // build all LibroCards view
        try{
            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

            ArrayList<LibroCard> allLibroCards = new ArrayList<>(libroCardDAO.getAllLibroCards());

            ScrollPane scrollPane = AllLibroCardsPageView.buildLibroCardsView(allLibroCards);

            leftPane.getChildren().add(scrollPane);

            scrollPane.setId("librocards-scrollpane");
            scrollPane.getStylesheets().add("/CSS/style.css");

            AnchorPane.setTopAnchor(scrollPane, (double) 200);
            AnchorPane.setLeftAnchor(scrollPane, (double) 90);
            AnchorPane.setRightAnchor(scrollPane, (double) 0);
            AnchorPane.setBottomAnchor(scrollPane, (double)40);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleLibroCardSearch() {

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
