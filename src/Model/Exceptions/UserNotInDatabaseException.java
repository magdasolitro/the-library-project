package Model.Exceptions;

public class UserNotInDatabaseException extends Exception {
    public UserNotInDatabaseException(){ super ("User is not registered in database"); }
}
