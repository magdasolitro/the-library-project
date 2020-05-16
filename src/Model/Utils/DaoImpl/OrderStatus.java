package Model.Utils.DaoImpl;

public enum OrderStatus {
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
            case LOST:
                return "Your order has been lost";
        }

        return "";
    }
}
