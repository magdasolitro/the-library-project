package Controller.EmployeeController;

import Model.Exceptions.InvalidStringException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewBookFXController implements Initializable {

    @FXML
    Button goBackButton, addBookButton;

    @FXML
    TextField titleField, authorsField, priceField, publishingHouseField,
            publishingYearField, discountField, ISBNField, copiesField,
            libroCardPointsField;

    @FXML
    TextArea descriptionField;

    @FXML
    ChoiceBox<String> genresChoiceBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set possible choices in drop down menu
        genresChoiceBox.getItems().addAll("All", "Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    public void handleGoBackButton() {
        try {
            viewPage("../../FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleAddBookRequest() {
        BigDecimal price = new BigDecimal(0), discount = new BigDecimal(0);
        int publishingYear = 0, copies = 0, libroCardPoints = 0;

        // check if all the fields have been filled
        if(titleField.getText().isEmpty() || authorsField.getText().isEmpty()
                || priceField.getText().isEmpty() || publishingHouseField.getText().isEmpty()
                || publishingYearField.getText().isEmpty() || discountField.getText().isEmpty()
                || ISBNField.getText().isEmpty() || copiesField.getText().isEmpty()
                || libroCardPointsField.getText().isEmpty() || genresChoiceBox.getValue().isEmpty()){
            Alert missingField = new Alert(Alert.AlertType.ERROR);

            missingField.setTitle("Fields Error");
            missingField.setHeaderText("You did not fill all the fields!");
            missingField.setContentText("To register a new book successfully, " +
                    "please provide all the requested informations");

            missingField.showAndWait();
        }

        // check validity of numeric fields
        try{
            String priceStr = priceField.getText();
            String publishingYearStr = publishingYearField.getText();
            String discountStr = discountField.getText();
            String copiesStr = copiesField.getText();
            String libroCardPointsStr = libroCardPointsField.getText();

            price = BigDecimal.valueOf(Float.parseFloat(priceStr));
            publishingYear = Integer.parseInt(publishingYearStr);
            discount = BigDecimal.valueOf(Float.parseFloat(discountStr));
            copies = Integer.parseInt(copiesStr);
            libroCardPoints = Integer.parseInt(libroCardPointsStr);

        } catch(NumberFormatException nfe){
            Alert nonValidNumericFieldsAlert = new Alert(Alert.AlertType.ERROR);

            nonValidNumericFieldsAlert.setTitle("Non Valid Fields");
            nonValidNumericFieldsAlert.setHeaderText("Some fields do not contain" +
                    " acceptable values!");
            nonValidNumericFieldsAlert.setContentText("Note that the fields Price, " +
                    "Publishing Year, Discount, Number of Copies and LibroCard " +
                    "Points should contain numeric values.");
        } finally {
            BookDAO bookDAO = new BookDaoImpl();

            try {
                bookDAO.addBook(ISBNField.getText(), titleField.getText(),
                        authorsField.getText(), genresChoiceBox.getValue(), price,
                        descriptionField.getText(), publishingHouseField.getText(),
                        publishingYear, discount, copies, libroCardPoints);

            } catch (SQLException | InvalidStringException e) {
                e.printStackTrace();
            }
            System.out.println(genresChoiceBox.getValue());
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
