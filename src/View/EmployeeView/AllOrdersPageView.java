package View.EmployeeView;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Order;
import Model.Utils.DAOs.OrderDAO;
import Model.Utils.DaoImpl.OrderDaoImpl;
import View.UserView.UserAllOrdersPageView;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class AllOrdersPageView {

    public static GridPane buildAllOrdersView(){
        GridPane allOrdersGP = new GridPane();

        OrderDAO orderDAO = new OrderDaoImpl();

        try {
            ArrayList<Order> allOrders = new ArrayList<>(orderDAO.getAllOrders());

            int i = 0;

            for(Order o : allOrders){
                GridPane singleOrder = UserAllOrdersPageView.singleOrderView(o);
                singleOrder.setVgap(10);
                allOrdersGP.add(singleOrder, 0, i);
                i++;
            }

            allOrdersGP.setVgap(40);
            allOrdersGP.setId("allorders-gridpane");
            allOrdersGP.getStylesheets().add("/CSS/style.css");

        } catch (SQLException | InvalidStringException | IllegalValueException e) {
            e.printStackTrace();
        }

        return allOrdersGP;
    }
}
