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
    private TextField searchField;

    @FXML
    private RadioButton userEmailRB, cardIDRB;

    @FXML
    private AnchorPane leftPane, rightPane;

    @FXML
    private Button goBackButton;

    private Label insertInformationLabel;

    private ToggleGroup group = new ToggleGroup();

    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userEmailRB.setToggleGroup(group);
        cardIDRB.setToggleGroup(group);

        // set default radiobutton to "user email"
        group.selectToggle(userEmailRB);

        insertInformationLabel = new Label("Insert user's e-mail:");

        Font labelFont = new Font("Avenir Book", 20);
        insertInformationLabel.setFont(labelFont);

        rightPane.getChildren().add(insertInformationLabel);
        insertInformationLabel.relocate(50, 390);

        group.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {

            rightPane.getChildren().remove(insertInformationLabel);

            if(newValue.equals(userEmailRB)){
                insertInformationLabel = new Label("Insert user's e-mail:");
            } else {
                insertInformationLabel = new Label("Insert cardID:");
            }

            insertInformationLabel.setFont(labelFont);

            rightPane.getChildren().add(insertInformationLabel);
            insertInformationLabel.relocate(50, 390);

        });


        // set style for goBackButton
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");


        Label insertLabel = new Label("Insert");
        insertLabel.setFont(new Font("Avenir Book", 17));


        // build all LibroCards view
        try{
            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

            ArrayList<LibroCard> allLibroCards = new ArrayList<>(libroCardDAO.getAllLibroCards());

            scrollPane = AllLibroCardsPageView.buildLibroCardsView(allLibroCards);

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
        LibroCardDAO libroCardDAO = new LibroCardDaoImpl();
        LibroCard libroCard;
        ArrayList<LibroCard> libroCardList = new ArrayList<>();

        try {
            if(searchField.getText().isEmpty()){
                Alert emptyFieldAlert = new Alert(Alert.AlertType.WARNING);

                emptyFieldAlert.setTitle("Empty Field");
                emptyFieldAlert.setHeaderText("Search field seems to be empty!");
                emptyFieldAlert.setContentText("To perform a research, type a cardID or an e-mail.");

                emptyFieldAlert.showAndWait();

            } else {
                if (group.getSelectedToggle().equals(userEmailRB)) {
                    String userEmail = searchField.getText();

                    libroCard = libroCardDAO.getUserLibroCard(userEmail);
                } else {
                    String cardID = searchField.getText();

                    libroCard = libroCardDAO.getLibroCard(cardID);
                }

                if (libroCard == null) {
                    Alert cardNotFound = new Alert(Alert.AlertType.ERROR);

                    cardNotFound.setTitle("LibroCard Not Found");
                    cardNotFound.setHeaderText("The LibroCard you searched for does not exist!");
                    cardNotFound.setContentText("Maybe you misspelled the user's email or cardID.");

                    cardNotFound.showAndWait();
                } else {
                    leftPane.getChildren().remove(scrollPane);

                    libroCardList.add(libroCard);

                    scrollPane = AllLibroCardsPageView.buildLibroCardsView(libroCardList);
                    leftPane.getChildren().add(scrollPane);

                    scrollPane.setId("librocards-scrollpane");
                    scrollPane.getStylesheets().add("/CSS/style.css");

                    AnchorPane.setTopAnchor(scrollPane, (double) 200);
                    AnchorPane.setLeftAnchor(scrollPane, (double) 90);
                    AnchorPane.setRightAnchor(scrollPane, (double) 0);
                    AnchorPane.setBottomAnchor(scrollPane, (double) 0);

                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void retrieveAllLibroCards() {
        // build all LibroCards view
        try{
            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

            ArrayList<LibroCard> allLibroCards = new ArrayList<>(libroCardDAO.getAllLibroCards());

            scrollPane = AllLibroCardsPageView.buildLibroCardsView(allLibroCards);

            leftPane.getChildren().add(scrollPane);

            scrollPane.setId("librocards-scrollpane");
            scrollPane.getStylesheets().add("/CSS/style.css");

            AnchorPane.setTopAnchor(scrollPane, (double) 200);
            AnchorPane.setLeftAnchor(scrollPane, (double) 90);
            AnchorPane.setRightAnchor(scrollPane, (double) 0);
            AnchorPane.setBottomAnchor(scrollPane, (double) 0);

        } catch (SQLException e) {
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
