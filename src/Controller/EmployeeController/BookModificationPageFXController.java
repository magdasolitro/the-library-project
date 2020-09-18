package Controller.EmployeeController;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.ObjectNotInDatabaseException;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookModificationPageFXController implements Initializable {

    @FXML
    private TextField bookISBNTF, priceTF, discountTF, availableCopiesTF,
            libroCardPointsTF;

    @FXML
    private TextArea descriptionTA;

    @FXML
    private ChoiceBox<String> genresCB;

    @FXML
    private Button cancelChangesButton, goBackButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genresCB.getItems().addAll("Autobiography", "Crime Fiction",
                "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction");

        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");
    }


    public void confirmChanges(MouseEvent evt){
        if(bookISBNTF.getText().isEmpty()){
            Alert missingISBNAlert = new Alert(Alert.AlertType.ERROR);

            missingISBNAlert.setTitle("Missing ISBN");
            missingISBNAlert.setHeaderText("No book specified in the ISBN field");
            missingISBNAlert.setContentText("To perform some changes, you must specify a valid ISBN.");

            missingISBNAlert.showAndWait();
        } else if(priceTF.getText().isEmpty() &&
                discountTF.getText().isEmpty() && availableCopiesTF.getText().isEmpty() &&
                libroCardPointsTF.getText().isEmpty() && genresCB.getSelectionModel().isEmpty() &&
                descriptionTA.getText().isEmpty()) {

            Alert allEmptyFieldsAlert = new Alert(Alert.AlertType.WARNING);

            allEmptyFieldsAlert.setTitle("Empty Fields");
            allEmptyFieldsAlert.setContentText("All the fields are empty!");
            allEmptyFieldsAlert.setHeaderText("No changes will be performed on the book data.");

            allEmptyFieldsAlert.showAndWait();

        } else {

            String bookISBN = bookISBNTF.getText();

            BookDAO bookDAO = new BookDaoImpl();

            try {
                Book book = bookDAO.getBook(bookISBN);

                Alert confirmChangesAlert = new Alert(Alert.AlertType.CONFIRMATION);

                confirmChangesAlert.setTitle("Confirm Changes");
                confirmChangesAlert.setHeaderText("Are you sure you want to confirm these changes?");
                confirmChangesAlert.setContentText("If you click \"OK\", the changes you have made will become permanent");

                Optional<ButtonType> response = confirmChangesAlert.showAndWait();

                if (response.isPresent() && response.get() == ButtonType.OK) {
                    if (!priceTF.getText().isEmpty()) {
                        //try {
                            bookDAO.editPrice(bookISBN,
                                    BigDecimal.valueOf( Float.parseFloat( priceTF.getText()) ) );
                        /*} catch (NumberFormatException e) {
                            Alert wrongFormatAlert = new Alert(Alert.AlertType.ERROR);

                            wrongFormatAlert.setTitle("Wrong Format");
                            wrongFormatAlert.setHeaderText("Price value is not valid");
                            wrongFormatAlert.setContentText("Please, insert a numerical value");
                        }

                         */
                    }

                    if (!discountTF.getText().isEmpty()) {
                        try {
                            bookDAO.editDiscount(bookISBN,
                                    BigDecimal.valueOf(Float.parseFloat(discountTF.getText())));
                        } catch (NumberFormatException e) {
                            Alert wrongFormatAlert = new Alert(Alert.AlertType.ERROR);

                            wrongFormatAlert.setTitle("Wrong Format");
                            wrongFormatAlert.setHeaderText("Discount value is not valid");
                            wrongFormatAlert.setContentText("Please, insert a numerical value");
                        }
                    }

                    if (!availableCopiesTF.getText().isEmpty()) {
                        try {
                            bookDAO.editAvailableCopies(bookISBN,
                                    Integer.parseInt(availableCopiesTF.getText()));
                        } catch (NumberFormatException e) {
                            Alert wrongFormatAlert = new Alert(Alert.AlertType.ERROR);

                            wrongFormatAlert.setTitle("Wrong Format");
                            wrongFormatAlert.setHeaderText("The value for available copies is not valid");
                            wrongFormatAlert.setContentText("Please, insert an positive integer value");
                        }
                    }

                    if (!libroCardPointsTF.getText().isEmpty()) {
                        bookDAO.editLibroCardPoints(bookISBN,
                                Integer.parseInt(libroCardPointsTF.getText()));
                    }

                    /*if(!genresCB.getSelectionModel().isEmpty()){
                        //bookDAO.editGenre(bookISBN, GenresEnum.values()[newValue.intValue()]);
                    }

                     */

                    if (!descriptionTA.getText().isEmpty()) {
                        bookDAO.editDescription(bookISBN, descriptionTA.getText());
                    }

                    Alert modificationSuccessfulAlert = new Alert(Alert.AlertType.INFORMATION);

                    modificationSuccessfulAlert.setTitle("Modification Successful");
                    modificationSuccessfulAlert.setHeaderText("Your modification was successful");

                    modificationSuccessfulAlert.showAndWait();


                } else {
                    evt.consume();
                    confirmChangesAlert.close();
                }
            } catch (SQLException | InvalidStringException |
                    IllegalValueException | ObjectNotInDatabaseException e) {
                Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);

                exceptionAlert.setTitle("Exception Occurred");
                exceptionAlert.setHeaderText("An exception occurred while trying\nto complete this operation.");
                exceptionAlert.setContentText("Error: " + e.getMessage());

                exceptionAlert.showAndWait();
            }
        }
    }


    public void cancelChanges(MouseEvent evt) {
        Alert cancelChangesAlert = new Alert(Alert.AlertType.CONFIRMATION);

        cancelChangesAlert.setTitle("Cancel Changes");
        cancelChangesAlert.setHeaderText("The changes you specified will be ignored!");
        cancelChangesAlert.setContentText("Are you sure you want to cancel them?");

        Optional<ButtonType> response = cancelChangesAlert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelChangesButton.getScene().getWindow();
            stage.close();

            try {
                viewPage("/FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            evt.consume();
            cancelChangesAlert.close();
        }
    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("/FXML/EmployeeFXML/EmployeeMainPageFX.fxml");
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
