package Controller.UserController;

import Controller.GeneralLoginController;
import Controller.LastOpenedPageController;
import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.NotSameUserException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Order;
import Model.OrderStatusEnum;
import Model.User;
import Model.Utils.DAOs.*;
import Model.Utils.DaoImpl.*;
import Model.Utils.DatabaseConnection;
import View.UserView.UserOrderConfirmationPageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserOrderConfirmationPageFXController implements Initializable {
    @FXML
    private AnchorPane whiteAnchorPane;

    @FXML
    private Button goBackButton;

    @FXML
    ChoiceBox<String> paymentMethodCB;

    @FXML
    RadioButton homeAddressRB, otherRB;

    @FXML
    Button checkOutButton;

    @FXML
    TextField alternativeAddressTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        ToggleGroup group = new ToggleGroup();

        homeAddressRB.setToggleGroup(group);
        homeAddressRB.setSelected(true);
        otherRB.setToggleGroup(group);

        paymentMethodCB.getItems().addAll("Credit Card", "PayPal", "Cash on delivery");

        CartDAO cartDAO = new CartDaoImpl();
        BigDecimal totalCost = new BigDecimal(0);

        try {
            totalCost = cartDAO.totalCost(GeneralLoginController.getLoginInstance()).setScale(2, RoundingMode.FLOOR);
        } catch (SQLException | IllegalValueException | InvalidStringException e) {
            e.printStackTrace();
        }

        Label totalCostLabel = new Label("$ " + totalCost);
        totalCostLabel.setFont(new Font("Avenir Next", 25));

        whiteAnchorPane.getChildren().add(totalCostLabel);
        totalCostLabel.relocate(600,220);

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


    public void handleCheckOut(ActionEvent event) {
        if(paymentMethodCB.getSelectionModel().isEmpty()){
            Alert missingFields = new Alert(Alert.AlertType.ERROR);

            missingFields.setTitle("Fields Error");
            missingFields.setHeaderText("Please, select a payment method!");
            missingFields.setContentText("To complete the order successfully, " +
                    "please provide all the requested informations");

            missingFields.showAndWait();

        } else if (otherRB.isSelected() && alternativeAddressTF.getText().isEmpty()){
            Alert missingFields = new Alert(Alert.AlertType.ERROR);

            missingFields.setTitle("Fields Error");
            missingFields.setHeaderText("Please, select an alternative address!");
            missingFields.setContentText("To complete the order successfully, " +
                    "please provide another address to which we can send your articles");

            missingFields.showAndWait();
        } else {
            Alert checkOutAlert = new Alert(Alert.AlertType.CONFIRMATION);

            checkOutAlert.setTitle("Check Out Confirmation");
            checkOutAlert.setHeaderText("Your order will be confirmed.");
            checkOutAlert.setContentText("Are you sure you want to proceed with the check out?");

            Optional<ButtonType> response = checkOutAlert.showAndWait();

            if (response.isPresent() && response.get() == ButtonType.OK) {
                try {
                    CartDAO cartDAO = new CartDaoImpl();
                    String currentUserEmail = GeneralLoginController.getLoginInstance();

                    // calculate all parameters needed to checkout
                    String orderID = cartDAO.generateOrderID(currentUserEmail);

                    BigDecimal orderPrice = cartDAO.totalCost(currentUserEmail);

                    DatabaseConnection connection = new DatabaseConnection();
                    connection.openConnection();

                    // select all the books and relative quantities from the user cart
                    String booksInCartQuery = "SELECT book, quantity " +
                            "FROM cart " +
                            "WHERE user = ?";

                    PreparedStatement pstmt1 = connection.conn.prepareStatement(booksInCartQuery);
                    pstmt1.setString(1, currentUserEmail);

                    ResultSet rs1 = pstmt1.executeQuery();

                    Map<String, Integer> bookAndQuantities = new HashMap<>();

                    while (rs1.next()) {
                        bookAndQuantities.put(rs1.getString("book"), rs1.getInt("quantity"));
                    }

                    rs1.close();
                    pstmt1.close();

                    connection.closeConnection();


                    ArrayList<Book> booksInCartAttributes = new ArrayList<>();

                    BookDAO bookDAO = new BookDaoImpl();
                    CompositionDAO compositionDAO = new CompositionDaoImpl();

                    for (String bookISBN : bookAndQuantities.keySet()) {
                        booksInCartAttributes.add(bookDAO.getBook(bookISBN));

                        compositionDAO.addBookToOrder(bookISBN, orderID, bookAndQuantities.get(bookISBN));
                        bookDAO.decreaseAvailableCopies(bookISBN, bookAndQuantities.get(bookISBN));
                    }


                    // current date
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String currentDate = formatter.format(calendar.getTime());


                    // calculate total libroCard points
                    int totalPoints = 0;

                    for (Book b : booksInCartAttributes) {
                        totalPoints += b.getLibroCardPoints();// * bookAndQuantities.get(b);
                    }

                    // add new row in Order table
                    OrderDAO orderDAO = new OrderDaoImpl();
                    Order newOrder;

                    if (otherRB.isSelected()) {
                        newOrder = new Order(orderID, currentDate,
                                OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                                paymentMethodCB.getValue(), orderPrice, totalPoints,
                                alternativeAddressTF.getText(), currentUserEmail, 1);
                    } else {
                        UserDAO userDAO = new UserDaoImpl();
                        User currentUser = userDAO.getUser(currentUserEmail);

                        newOrder = new Order(orderID, currentDate,
                                OrderStatusEnum.ORDER_REQUEST_RECEIVED.toString(),
                                paymentMethodCB.getValue(), orderPrice, totalPoints,
                                currentUser.getHomeAddress() + currentUser.getStreetNumber(),
                                currentUserEmail, 1);
                    }

                    orderDAO.addOrder(newOrder);


                    DatabaseConnection connection1 = new DatabaseConnection();
                    connection1.openConnection();

                    // retrieve user's LibroCard ID
                    LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

                    String cardIDQuery = "SELECT cardID " +
                            "FROM LibroCard " +
                            "WHERE email = ?";

                    PreparedStatement pstmt2 = connection1.conn.prepareStatement(cardIDQuery);

                    pstmt2.setString(1, currentUserEmail);

                    ResultSet rs2 = pstmt2.executeQuery();

                    String cardID = rs2.getString("cardID");

                    rs2.close();
                    pstmt2.close();

                    connection1.closeConnection();


                    // add points to user's LibroCard
                    libroCardDAO.addPoints(cardID, orderID);


                    // remove the books from user's cart
                    DatabaseConnection connection2 = new DatabaseConnection();
                    connection2.openConnection();

                    String clearCartQuery = "DELETE FROM cart " +
                            "WHERE user = ?";

                    PreparedStatement pstmt3 = connection2.conn.prepareStatement(clearCartQuery);

                    pstmt3.setString(1, currentUserEmail);

                    pstmt3.executeUpdate();

                    connection2.closeConnection();

                    Stage closingStage = (Stage) checkOutButton.getScene().getWindow();
                    closingStage.close();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(UserOrderConfirmationPageView.class.getResource("../../FXML/UserFXML/UserOrderSuccessfulPageFX.fxml"));
                    Parent root = loader.load();

                    LastOpenedPageController.setLastOpenedPage("../../FXML/UserFXML/UserMainPageFX.fxml");

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();

                } catch (SQLException | UserNotInDatabaseException | InvalidStringException |
                        IllegalValueException | NotSameUserException | IOException ex) {
                    Alert orderFailure = new Alert(Alert.AlertType.ERROR);

                    orderFailure.setTitle("Order Failure");
                    orderFailure.setHeaderText("Something went wrong: ");
                    orderFailure.setContentText("ERROR MESSAGE: " + ex.getMessage());

                    orderFailure.showAndWait();
                    ex.printStackTrace();
                }
            } else {
                event.consume();
                checkOutAlert.close();
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
