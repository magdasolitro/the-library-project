package View.EmployeeView;

import Model.Order;
import View.UserView.UserAllOrdersPageView;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class AllOrdersPageView {

    public static ScrollPane buildAllOrdersView(ArrayList<Order> orders){

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setPrefHeight(600);
        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane allOrdersGP = new GridPane();

        int i = 0;

        for(Order o : orders){
            GridPane singleOrder = UserAllOrdersPageView.singleOrderView(o, 200, 640);
            singleOrder.setVgap(10);
            allOrdersGP.add(singleOrder, 0, i);
            i++;
        }


        allOrdersGP.setPadding(new Insets(30, 0, 0, 30));

        allOrdersGP.setVgap(50);

        allOrdersGP.setId("allorders-gridpane");
        allOrdersGP.getStylesheets().add("/CSS/style.css");

        scrollPane.setContent(allOrdersGP);

        return scrollPane;
    }

}
