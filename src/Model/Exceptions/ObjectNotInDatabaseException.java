package Model.Exceptions;

public class ObjectNotInDatabaseException extends Exception {

    public ObjectNotInDatabaseException(){ super("Object not registered in the database."); }

}
