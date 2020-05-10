package Model.Utils.DAOs;

import Model.UserNotReg;
import java.sql.SQLException;

public interface UserNotRegDAO {
    public UserNotReg getUserNotReg(String username) throws SQLException;

    public void addUserNotReg(String username, String name, String surname,
                              String phone, String email) throws SQLException;

    public void deleteUserNotReg(UserNotReg userNotReg) throws SQLException;
}
