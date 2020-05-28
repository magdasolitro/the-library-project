package Model.Utils.DaoImpl;

import Model.Exceptions.InvalidStringException;
import Model.UserNotReg;
import Model.Utils.DAOs.UserNotRegDAO;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;

public class UserNotRegDaoImpl implements UserNotRegDAO {
    @Override
    public UserNotReg getUserNotReg(String email) throws SQLException,
            InvalidStringException {
        String sql = "SELECT * FROM userNotReg WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);

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
        String sql = "INSERT INTO userNotReg(name, surname, phone, email)" +
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
        String sql = "DELETE FROM userNotReg WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
