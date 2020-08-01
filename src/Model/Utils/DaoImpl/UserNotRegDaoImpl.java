package Model.Utils.DaoImpl;

import Model.Exceptions.InvalidStringException;
import Model.UserNotReg;
import Model.Utils.DAOs.UserNotRegDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserNotRegDaoImpl implements UserNotRegDAO {

    @Override
    public UserNotReg getUserNotReg(String email) throws SQLException,
            InvalidStringException {
        String sql = "SELECT * FROM userNotReg WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();

        UserNotReg user = new UserNotReg(rs.getString("name"),
                rs.getString("surname"),
                rs.getString("phone"),
                rs.getString("email"));

        rs.close();
        pstmt.close();

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

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setString(2, surname);
        pstmt.setString(3, phone);
        pstmt.setString(4, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public void deleteUserNotReg(String email) throws SQLException {
        String sql = "DELETE FROM userNotReg WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }
}
