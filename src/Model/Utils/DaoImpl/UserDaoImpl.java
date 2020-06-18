package Model.Utils.DaoImpl;
import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DAOs.UserDAO;
import Model.User;
import Model.Utils.DatabaseConnection;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO {

    public User getUser(String email) throws InvalidStringException {

        String sql = "SELECT * FROM user WHERE email = ?";

        User user;

        try{
            DatabaseConnection connection = new DatabaseConnection();
            connection.openConnection();

            connection.pstmt = connection.conn.prepareStatement(sql);

            // fill the parameters in the SQL statement
            connection.pstmt.setString(1, email);

            // execute query
            connection.rs = connection.pstmt.executeQuery();

            user = new User(connection.rs.getString("name"),
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

        } catch (SQLException sqle){
            System.out.println( sqle.getMessage());
        } catch(NullPointerException npe){
            System.out.println(npe.getMessage());
        }

        return null;
    }

    public void addUser(User user) throws SQLException, UserNotInDatabaseException,
            InvalidStringException{

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

        // add LibroCard for user
        try {
            LibroCard newLibroCard = new LibroCard(user.getEmail());

            LibroCardDAO libroCardDAO = new LibroCardDaoImpl();
            libroCardDAO.addLibroCard(newLibroCard);
        } catch (UserNotInDatabaseException unidb){
            System.out.println("Database update was not successful: " +
                    unidb.getMessage());
        }

        connection.closeConnection();
    }

    public void deleteUser(String email) throws SQLException {
        String sql = "DELETE FROM user WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);

        connection.pstmt.executeUpdate();

        // delete LibroCard associated to user
        String cardIDQuery = "SELECT cardID FROM LibroCard WHERE email = ?";

        connection.pstmt = connection.conn.prepareStatement(cardIDQuery);
        connection.pstmt.setString(1, email);

        connection.pstmt.executeQuery();

        LibroCardDAO libroCardDAO = new LibroCardDaoImpl();
        libroCardDAO.deleteLibroCard(connection.rs.getString("cardID"));

        connection.closeConnection();
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

    @Override
    public void updateHomeAddress(String email, String newAddress) throws SQLException {
        String sql = "UPDATE user SET homeAddress = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newAddress);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void updateStreetNumber(String email, String newStreetNumber) throws SQLException {
        String sql = "UPDATE user SET streetNumber = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newStreetNumber);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void updateZIPCode(String email, String newZIPCode) throws SQLException {
        String sql = "UPDATE user SET ZIPCode = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newZIPCode);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

    @Override
    public void updateHomeCity(String email, String newHomeCity) throws SQLException {
        String sql = "UPDATE user SET homeCity = ? WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);

        connection.pstmt.setString(1, newHomeCity);
        connection.pstmt.setString(2, email);

        connection.pstmt.executeUpdate();

        connection.closeConnection();
    }

}
