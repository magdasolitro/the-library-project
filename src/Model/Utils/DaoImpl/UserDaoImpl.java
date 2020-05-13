package Model.Utils.DaoImpl;
import Model.Utils.DAOs.UserDAO;
import Model.User;
import Model.Utils.DatabaseConnection;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO {
    public User getUser(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = " + email;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        // fill pstmt with the SQL statement, still to be parametrized
        connection.pstmt = connection.conn.prepareStatement(sql);

        // fill the parameters in the SQL statement
        connection.pstmt.setString(1, email);

        // execute query
        connection.rs = connection.pstmt.executeQuery();

        User user = new User(connection.rs.getString("name"),
                connection.rs.getString("surname"),
                connection.rs.getString("phone"),
                connection.rs.getString("email"),
                connection.rs.getString("password"),
                connection.rs.getString("homeAddress"),
                connection.rs.getString("streetNumber"),
                connection.rs.getString("ZIPcode"),
                connection.rs.getString("homeCity"));

        connection.closeConnection();

        return user;
    }

    public void addUser(User user) throws SQLException {

        String sql = "INSERT INTO user(name, surname, phone, email," +
                "password, homeAddress, streetNumber, ZIPCode, homeCity)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(2, user.getName());
        connection.pstmt.setString(3, user.getSurname());
        connection.pstmt.setString(4, user.getPhone());
        connection.pstmt.setString(5, user.getEmail());
        connection.pstmt.setString(6, user.getPassword());
        connection.pstmt.setString(7, user.getHomeAddress());
        connection.pstmt.setString(8, user.getStreetNumber());
        connection.pstmt.setString(9, user.getZIPCode());
        connection.pstmt.setString(10, user.getHomeCity());


        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    public void deleteUser(String email) throws SQLException {
        String sql = "DELETE FROM user WHERE email = " + email;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.executeUpdate();
    }

    public void updatePhone(String email, String newPhone) throws SQLException {
        String sql = "UPDATE user SET phone = ? WHERE email = ?" ;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newPhone);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    public void updateEmail(String email, String newEmail) throws SQLException {
        String sql = "UPDATE user SET email = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newEmail);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    public void updatePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newPassword);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    public void updateAddress(String email, String addressField, String newValue)
            throws SQLException {

        String sql = "UPDATE user SET ? = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, addressField);
        connection.pstmt.setString(2, newValue);
        connection.pstmt.setString(3, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }
}
