package Model.Utils.DAOs;

import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.User;
import java.sql.SQLException;

public interface UserDAO {

    public User getUser(String email) throws InvalidStringException;

    public void addUser(User user) throws SQLException, UserNotInDatabaseException,
            InvalidStringException;

    public void deleteUser(String email) throws SQLException;

    public void updatePhone(String email, String newPhone) throws SQLException;

    public void updateEmail(String email, String newEmail) throws SQLException;

    public void updatePassword(String email, String newPassword) throws
            SQLException;

    public void updateHomeAddress(String email, String newAddress)
            throws SQLException;

    public void updateStreetNumber(String email, String newStreetNumber)
            throws SQLException;

    public void updateZIPCode(String email, String newZIPCode)
            throws SQLException;

    public void updateHomeCity(String email, String newHomeCity)
            throws SQLException;
}
