package Controller;

public class OrderIDController {
    private static String currentOrderID = null;

    public static String getCurrentOrderID(){ return currentOrderID; }

    public static void setCurrentOrderID(String orderID){ currentOrderID = orderID; }
}
