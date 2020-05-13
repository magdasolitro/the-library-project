package Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {
    public String orderID;
    public String date;
    public String status;
    public String paymentMethod;
    public float price;
    public int points;
    public String shippingAddress;
    public String user;
    public String userNotReg;

    public Order(String orderID, String date, String status, String paymentMethod,
                 float price, int points, String shippingAddress, String user,
                 String userNotReg){
        this.orderID = orderID;
        this.date = date;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.points = points;
        this.shippingAddress = shippingAddress;
        this.user = user;
        this.userNotReg = userNotReg;
    }

    public String getOrderID(){ return orderID; }

    public String getDate(){ return date; }

    public String getStatus(){ return status; }

    public String getPaymentMethod(){ return paymentMethod; }

    public float getPrice(){ return price; }

    public int getPoints(){ return points; }

    public String getShippingAddress(){ return shippingAddress; }

    public String getUser(){
        if(user != null) {
            return user;
        } else {
            return userNotReg;
        }
    }
}
