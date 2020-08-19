package Model.Utils.DaoImpl;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DAOs.UserDAO;
import Model.User;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO {

    public User getUser(String email) throws InvalidStringException, SQLException {

        String sql = "SELECT * FROM user WHERE email = ?";

        User user = null;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        //pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, email);

        // fill the parameters in the SQL statement
        //pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        // execute query
        if(rs.next()) {
            user = new User(rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("homeAddress"),
                    rs.getString("streetNumber"),
                    rs.getString("ZIPcode"),
                    rs.getString("homeCity"));
        }

        pstmt.close();
        rs.close();

        connection.closeConnection();

        return user;
    }


    public void addUser(User user) throws SQLException, InvalidStringException{

        String sql = "INSERT INTO user(name, surname, phone, email," +
                "password, homeAddress, streetNumber, ZIPCode, homeCity)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getSurname());
        pstmt.setString(3, user.getPhone());
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getPassword());
        pstmt.setString(6, user.getHomeAddress());
        pstmt.setString(7, user.getStreetNumber());
        pstmt.setString(8, user.getZIPCode());
        pstmt.setString(9, user.getHomeCity());

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void deleteUser(String email) throws SQLException {
        String sql = "DELETE FROM user WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);
        pstmt.setString(1, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void updatePhone(String email, String newPhone) throws SQLException {
        String sql = "UPDATE user SET phone = ? WHERE email = ?" ;

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newPhone);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void updatePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newPassword);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void updateHomeAddress(String email, String newAddress) throws SQLException {
        String sql = "UPDATE user SET homeAddress = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newAddress);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void updateStreetNumber(String email, String newStreetNumber) throws SQLException {
        String sql = "UPDATE user SET streetNumber = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newStreetNumber);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void updateZIPCode(String email, String newZIPCode) throws SQLException {
        String sql = "UPDATE user SET ZIPCode = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newZIPCode);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public void updateHomeCity(String email, String newHomeCity) throws SQLException {
        String sql = "UPDATE user SET homeCity = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, newHomeCity);
        pstmt.setString(2, email);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

}
