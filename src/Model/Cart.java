package Model;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;

public class Cart {
    private String user;
    private String book;
    private int quantity;

    public Cart(String user, String book, int quantity) throws InvalidStringException,
            IllegalValueException {

        if(user.length() == 0 || book.length() == 0){
            throw new InvalidStringException();
        }

        if(quantity < 1){
            throw new IllegalValueException();
        }

        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }

    public String getUser(){ return user; }

    public String getBook(){ return book; }

    public int getQuantity(){ return quantity; }
}
