package Model.Utils.DAOs;

import java.sql.SQLException;

public interface UserNotRegDAO {

    void addNotRegistredUser(String name, String surname, String phone,
                             String email, String userNotRegID) throws SQLException;

}
