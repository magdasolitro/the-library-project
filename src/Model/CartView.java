package Model;

public class CartView extends Book {
    public int quantity;

    public CartView(String ISBN, String title, String authors, String genre, float price,
                    String description, String publishingHouse, int publishingYear,
                    float discount, int availableCopies, int quantity){
        super(ISBN, title, authors, genre, price, description, publishingHouse,
                publishingYear, discount, availableCopies);
        this.quantity = quantity;
    }

    public int getQuantity(){ return quantity; }

}
