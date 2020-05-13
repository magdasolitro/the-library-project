package Model.Utils.DAOs;

import Model.UserNotReg;
import java.sql.SQLException;

public interface UserNotRegDAO {
    public UserNotReg getUserNotReg(String email) throws SQLException;

    public void addUserNotReg(String name, String surname,
                              String phone, String email) throws SQLException;

    public void deleteUserNotReg(String email) throws SQLException;
}
