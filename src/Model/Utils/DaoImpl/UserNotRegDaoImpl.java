package Model.Utils.DaoImpl;

import Model.UserNotReg;
import Model.Utils.DAOs.UserNotRegDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public class UserNotRegDaoImpl implements UserNotRegDAO {
    @Override
    public UserNotReg getUserNotReg(String email) throws SQLException {
        String sql = "SELECT * FROM user_notReg WHERE email = " + email;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.rs = connection.pstmt.executeQuery();

        UserNotReg user = new UserNotReg(connection.rs.getString("name"),
                connection.rs.getString("surname"),
                connection.rs.getString("phone"),
                connection.rs.getString("email"));

        connection.closeConnection();

        return user;
    }

    @Override
    public void addUserNotReg(String name, String surname, String phone, String email)
            throws SQLException {
        String sql = "INSERT INTO user_notReg(name, surname, phone, email)" +
                "VALUES(?,?,?,?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, name);
        connection.pstmt.setString(2, surname);
        connection.pstmt.setString(3, phone);
        connection.pstmt.setString(4, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void deleteUserNotReg(String email) throws SQLException {
        String sql = "DELETE FROM user_notReg WHERE email = " + email;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
