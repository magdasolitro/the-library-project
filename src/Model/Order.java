package Model;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;

import java.math.BigDecimal;

public class Order {
    private String orderID;
    private String date;
    private String status;
    private String paymentMethod;
    private BigDecimal price;
    private Integer points;
    private String shippingAddress;
    private String user;
    private String userNotReg;

    // usato per memorizzare il risultato di una query
    public Order(String orderID, String date, String status, String paymentMethod,
                 BigDecimal price, Integer points, String shippingAddress, String user,
                 String userNotReg) throws InvalidStringException, IllegalValueException {

        if(orderID.length() == 0 || date.length() == 0 || status.length() == 0
                || paymentMethod.length() == 0 || shippingAddress.length() == 0){
            throw new InvalidStringException();
        }

        if(price.compareTo(new BigDecimal(0)) == -1 || points < 0){
            throw new IllegalValueException();
        }

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

    public BigDecimal getPrice(){ return price; }

    public Integer getPoints(){ return points; }

    public String getShippingAddress(){ return shippingAddress; }

    public String getUser(){
        if(user != null) {
            return user;
        } else {
            return userNotReg;
        }
    }
}
