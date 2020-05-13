package Model.Utils.DAOs;

import Model.LibroCard;
import Model.Order;
import Model.User;

import java.sql.SQLException;

public interface LibroCardDAO {

    public void addLibroCard(String cardID, String email, String issueDate)
            throws SQLException;

    public LibroCard getLibroCard(String cardID) throws SQLException;

    public LibroCard getLibroCard(User user) throws SQLException;

    public void deleteLibroCard(String cardID) throws SQLException;

    // utilizzato per aggiungere punti appena l'ordine viene effettuato
    public void addPoints(LibroCard libroCard, Order orderID) throws SQLException;
}
