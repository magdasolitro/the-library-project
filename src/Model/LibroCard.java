package Model;

import Model.Exceptions.InvalidStringException;
import Model.Exceptions.UserNotInDatabaseException;
import Model.Utils.DAOs.UserDAO;
import Model.Utils.DaoImpl.UserDaoImpl;
import Model.Utils.DatabaseConnection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class LibroCard {
    private final String cardID;
    private final String email;
    private int points;
    private final String issueDate;

    // usato al ritorno da una query sul database, per memorizzare il risultato
    public LibroCard(String cardID, String email, int points,
                     String issueDate){
        this.cardID = cardID;
        this.email = email;
        this.points = points;
        this.issueDate = issueDate;
    }

    // usato al momento della creazione di una nuova LibroCard
    public LibroCard(String email) throws SQLException, UserNotInDatabaseException,
            InvalidStringException {

        UserDAO userDao = new UserDaoImpl();

        if( userDao.getUser(email) == null ){
            throw new UserNotInDatabaseException();
        }

        this.cardID = generateCardID(email);
        this.email = email;
        this.points = 0;
        this.issueDate = getCurrentDate();
    }

    public String getCardID(){ return cardID; }

    public String getUser(){ return email; }

    public int getPoints(){ return points; }

    public String getIssueDate(){ return issueDate; }



    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }

    // cardID format = CARD_<SURNAME><DATE+TIME>
    private String generateCardID(String email) throws SQLException {
        // date + time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String dateTimeToken  = dtf.format(now);

        // user's surname
        String sql = "SELECT surname FROM user WHERE email = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);

        connection.rs = connection.pstmt.executeQuery();

        String surname = connection.rs.getString("surname");

        connection.closeConnection();

        return surname + dateTimeToken;
    }
}
