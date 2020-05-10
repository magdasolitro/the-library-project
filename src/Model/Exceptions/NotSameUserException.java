package Model.Exceptions;

public class NotSameUserException extends Exception {
    public NotSameUserException(){
        super("Card user and Order user are not the same");
    }
}
