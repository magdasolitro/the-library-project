package Model.Utils.DAOs;

import Model.Exceptions.InvalidStringException;
import Model.UserNotReg;
import java.sql.SQLException;

public interface UserNotRegDAO {
    public UserNotReg getUserNotReg(String email) throws SQLException,
            InvalidStringException;

    public void addUserNotReg(String name, String surname,
                              String phone, String email) throws SQLException;

    public void deleteUserNotReg(String email) throws SQLException;
}
