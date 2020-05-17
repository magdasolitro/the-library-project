package Model;

public class Cart {
    public String user;
    public String book;
    public int quantity;

    public Cart(String user, String book, int quantity){
        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }

    public String getUser(){ return user; }

    public String getBook(){ return book; }

    public int getQuantity(){ return quantity; }
}
