package Model;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;

public class Composition {
    public String book;
    public String order;
    public int quantity;

    public Composition(String book, String order, int quantity) throws
            InvalidStringException, IllegalValueException {

        if(book.length() == 0 || order.length() == 0){
            throw new InvalidStringException();
        }

        if(quantity < 1){
            throw new IllegalValueException();
        }

        this.book = book;
        this.order = order;
        this.quantity = quantity;
    }

    public String getBook(){ return book; }

    public String getOrder(){ return order; }

    public int getQuantity(){ return quantity; }
}
