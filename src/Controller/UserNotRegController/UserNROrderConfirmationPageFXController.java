package Controller.UserNotRegController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DAOs.CartDAO;
import Model.Utils.DAOs.CompositionDAO;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import Model.Utils.DaoImpl.CartDaoImpl;
import Model.Utils.DaoImpl.CompositionDaoImpl;
import Model.Utils.DaoImpl.OrderDaoImpl;
import Model.Utils.DatabaseConnection;
import View.UserView.UserOrderConfirmationPageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserNROrderConfirmationPageFXController implements Initializable {

    @FXML
    private AnchorPane whiteAnchorPane;

    @FXML
    private Button goBackButton, checkOutButton;

    @FXML
    private TextField nameTF, surnameTF, shippingAddressTF, phoneTF, emailTF;

    @FXML
    private ChoiceBox<String> paymentMethodCB;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        try {
            CartDAO cartDAO = new CartDaoImpl();

            Label totalPriceLabel = new Label("" + cartDAO.totalCost(GeneralLoginController.getLoginInstance()));
            totalPriceLabel.setFont(new Font("Avenir Book", 25));

            whiteAnchorPane.getChildren().add(totalPriceLabel);
            totalPriceLabel.relocate(780, 370);

        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }

        paymentMethodCB.getItems().addAll("Credit Card", "PayPal", "Cash on delivery");
    }

    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            viewPage("../../FXML/UserFXML/UserCartPageFX.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCheckOutRequest(MouseEvent event) {
        if(nameTF.getText().isEmpty() || surnameTF.getText().isEmpty()
                || paymentMethodCB.getSelectionModel().isEmpty()
                || shippingAddressTF.getText().isEmpty()
                || phoneTF.getText().isEmpty() || emailTF.getText().isEmpty()) {

            Alert missingFields = new Alert(Alert.AlertType.ERROR);

            missingFields.setTitle("Fields Error");
            missingFields.setHeaderText("Some fields are missing!");
            missingFields.setContentText("To complete the order successfully, " +
                    "please provide all the requested informations");

            missingFields.showAndWait();
        } else {
            Alert checkOutAlert = new Alert(Alert.AlertType.CONFIRMATION);

            checkOutAlert.setTitle("Check Out Confirmation");
            checkOutAlert.setHeaderText("Your order will be confirmed.");
            checkOutAlert.setContentText("Are you sure you want to proceed with the check out?");

            Optional<ButtonType> response = checkOutAlert.showAndWait();

            if (response.isPresent() && response.get() == ButtonType.OK) {
                CartDAO cartDAO = new CartDaoImpl();

                String currentUser = GeneralLoginController.getLoginInstance();

                try {
                    // calculate all parameters needed to checkout
                    String orderID = cartDAO.generateOrderID(currentUser);
                    BigDecimal orderPrice = cartDAO.totalCost(currentUser);

                    DatabaseConnection connection = new DatabaseConnection();
                    connection.openConnection();

                    // select all the books and relative quantities from the user cart
                    String booksInCartQuery = "SELECT book, quantity " +
                            "FROM cart " +
                            "WHERE user = ?";

                    PreparedStatement pstmt1 = connection.conn.prepareStatement(booksInCartQuery);
                    pstmt1.setString(1, currentUser);

                    ResultSet rs1 = pstmt1.executeQuery();

                    Map<String, Integer> bookAndQuantities = new HashMap<>();

                    while (rs1.next()) {
                        bookAndQuantities.put(rs1.getString("book"), rs1.getInt("quantity"));
                    }

                    rs1.close();
                    pstmt1.close();

                    connection.closeConnection();


                    BookDAO bookDAO = new BookDaoImpl();
                    CompositionDAO compositionDAO = new CompositionDaoImpl();

                    for (String bookISBN : bookAndQuantities.keySet()) {
                        compositionDAO.addBookToOrder(bookISBN, orderID, bookAndQuantities.get(bookISBN));
                        bookDAO.decreaseAvailableCopies(bookISBN, bookAndQuantities.get(bookISBN));
                    }


                    // current date
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String currentDate = formatter.format(calendar.getTime());


                    // add new row in Order table
                    OrderDAO orderDAO = new OrderDaoImpl();
                    Order newOrder;

                    newOrder = new Order(orderID, currentDate,
                            OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                            paymentMethodCB.getValue(), orderPrice, null,
                            shippingAddressTF.getText(), currentUser, 0);

                    orderDAO.addOrder(newOrder);


                    DatabaseConnection connection2 = new DatabaseConnection();
                    connection2.openConnection();

                    String clearCartQuery = "DELETE FROM cart " +
                            "WHERE user = ?";

                    PreparedStatement pstmt3 = connection2.conn.prepareStatement(clearCartQuery);

                    pstmt3.setString(1, currentUser);

                    pstmt3.executeUpdate();

                    connection2.closeConnection();


                    Stage closingStage = (Stage) checkOutButton.getScene().getWindow();
                    closingStage.close();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(UserOrderConfirmationPageView.class.getResource("../../FXML/UserFXML/UserOrderSuccessfulPageFX.fxml"));
                    Parent root = loader.load();

                    LastOpenedPageController.setLastOpenedPage("../../FXML/UserNotRegFXML/UserNRMainPageFX.fxml");

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();

                } catch (SQLException | UserNotInDatabaseException | InvalidStringException |
                        IllegalValueException | IOException e) {
                    e.printStackTrace();
                }

            } else {
                event.consume();
            }
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
