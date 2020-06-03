package Model;

public enum OrderStatusEnum {
    ORDER_REQUEST_RECEIVED,
    IN_PREPARATION,
    SHIPPED,
    DELIVERED,
    UNABLE_TO_DELIVER,
    LOST;

    public String getStatusMessage() {
        switch (this) {
            case ORDER_REQUEST_RECEIVED:
                return "Your order request has been received";
            case IN_PREPARATION:
                return "We're preparing your order";
            case SHIPPED:
                return "Order shipped";
            case DELIVERED:
                return "Your order has been successfully delivered";
            case UNABLE_TO_DELIVER:
                return "Delivery has failed because of a problem";
            default:
                return "Your order has been lost";
        }
    }
}
