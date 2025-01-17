package Model;

public enum EmployeeRoleEnum {
    GENERAL_DIRECTOR,
    LOCAL_DIRECTOR,
    BOOK_SELLER,
    CASHIER;

    public String getEmployeeRole() {
        switch (this) {
            case GENERAL_DIRECTOR:
                return "General Director";
            case LOCAL_DIRECTOR:
                return "Local Director";
            case BOOK_SELLER:
                return "Book Seller";
            default:
                return "Cashier";
        }
    }

}
