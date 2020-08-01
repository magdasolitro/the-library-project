package Model.Utils.DAOs;

import Model.Exceptions.NotSameUserException;
import Model.LibroCard;
import Model.Order;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LibroCardDAO {

    // prima creo un oggetto LibroCard, poi lo passo in input al metodo
    public void addLibroCard(LibroCard newLibroCard)
            throws SQLException;

    public String getUserEmail(String cardID) throws SQLException;

    public LibroCard getLibroCard(String cardID) throws SQLException;

    public ArrayList<LibroCard> getAllLibroCards() throws SQLException;

    public LibroCard getUserLibroCard(String email) throws SQLException;

    public void deleteLibroCard(String cardID) throws SQLException;

    // utilizzato per aggiungere punti appena l'ordine viene effettuato
    public void addPoints(String cardID, String orderID) throws SQLException,
            NotSameUserException;
}
