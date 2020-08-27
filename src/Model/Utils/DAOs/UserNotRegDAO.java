package Model.Utils.DAOs;

import Model.UserNotReg;

import java.sql.SQLException;

public interface UserNotRegDAO {

    public void addNotRegistredUser(String name, String surname, String phone,
                                    String email, String userNotRegID) throws SQLException;

}
