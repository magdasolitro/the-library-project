package Model.Utils.DaoImpl;

import Model.Utils.DAOs.UserNotRegDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserNotRegDaoImpl implements UserNotRegDAO {

    @Override
    public void addNotRegistredUser(String name, String surname, String phone,
                                    String email, String userNotRegID) throws SQLException {

        String sql = "INSERT INTO userNotReg VALUES (?,?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setString(2, surname);
        pstmt.setString(3, phone);
        pstmt.setString(4, email);
        pstmt.setString(5, userNotRegID);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();

    }
}
