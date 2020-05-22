package Model.Utils.DAOs;

import Model.Exceptions.NullStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.User;
import java.sql.SQLException;

public interface UserDAO {

    public User getUser(String email) throws SQLException, NullStringException;

    public void addUser(User user) throws SQLException, UserNotInDatabaseException;

    public void deleteUser(String email) throws SQLException;

    public void updatePhone(String email, String newPhone) throws SQLException;

    public void updateEmail(String email, String newEmail) throws SQLException;

    public void updatePassword(String email, String newPassword) throws
            SQLException;

    public void updateAddress(String email, String addressField, String newValue)
            throws SQLException;
}
