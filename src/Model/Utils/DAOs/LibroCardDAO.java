package Model.Utils.DAOs;

import Model.Exceptions.NotSameUserException;
import Model.LibroCard;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LibroCardDAO {

    // prima creo un oggetto LibroCard, poi lo passo in input al metodo
    void addLibroCard(LibroCard newLibroCard)
            throws SQLException;

    String getUserEmail(String cardID) throws SQLException;

    LibroCard getLibroCard(String cardID) throws SQLException;

    ArrayList<LibroCard> getAllLibroCards() throws SQLException;

    LibroCard getUserLibroCard(String email) throws SQLException;

    void deleteLibroCard(String cardID) throws SQLException;

    // utilizzato per aggiungere punti appena l'ordine viene effettuato
    void addPoints(String cardID, String orderID) throws SQLException,
            NotSameUserException;
}
