package Model.Utils.DAOs;

import Model.User;
import java.sql.SQLException;

public interface UserDAO {

    public User getUser(String username) throws SQLException;

    public void addUser(User user) throws SQLException;

    public void deleteUser(String username) throws SQLException;

    public void updateUsername(User user, String newUsername) throws
            SQLException;

    public void updatePhone(User user, String newPhone) throws SQLException;

    public void updateEmail(User user, String newEmail) throws SQLException;

    public void updatePassword(User user, String newPassword) throws
            SQLException;

    public void updateAddress(User user, String addressField, String newValue)
            throws SQLException;
}
