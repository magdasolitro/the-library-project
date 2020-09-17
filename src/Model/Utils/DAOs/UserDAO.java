package Model.Utils.DAOs;

import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.User;
import java.sql.SQLException;

public interface UserDAO {

    User getUser(String email) throws SQLException, InvalidStringException;

    void addUser(User user) throws SQLException, UserNotInDatabaseException,
            InvalidStringException;

    void deleteUser(String email) throws SQLException;

    void updatePhone(String email, String newPhone) throws SQLException;

    void updatePassword(String email, String newPassword) throws SQLException;

    void updateHomeAddress(String email, String newAddress) throws SQLException;

    void updateStreetNumber(String email, String newStreetNumber) throws SQLException;

    void updateZIPCode(String email, String newZIPCode) throws SQLException;

    void updateHomeCity(String email, String newHomeCity) throws SQLException;
}
