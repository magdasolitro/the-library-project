package Model;

public class Composition {
    public String book;
    public String order;
    public int quantity;

    public Composition(String book, String order, int quantity){
        this.book = book;
        this.order = order;
        this.quantity = quantity;
    }

    public String getBook(){ return book; }

    public String getOrder(){ return order; }

    public int getQuantity(){ return quantity; }
}
