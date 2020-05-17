package Model;

import Model.Utils.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class LibroCard {
    public String cardID;
    public String user;
    public int points;
    public String issueDate;

    // usato al ritorno da una query sul database, per memorizzare il risultato
    public LibroCard(String cardID, String user, int points,
                     String issueDate){
        this.cardID = cardID;
        this.user = user;
        this.points = points;
        this.issueDate = issueDate;
    }

    // usato al momento della creazione di una nuova LibroCard
    public LibroCard(String user) throws SQLException {
        this.cardID = generateCardID(user);
        this.user = user;
        this.points = 0;
        this.issueDate = getCurrentDate();
    }

    public String getCardID(){ return cardID; }

    public String getUser(){ return user; }

    public int getPoints(){ return points; }

    public String getIssueDate(){ return issueDate; }



    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }

    // cardID format = CARD_<DATE+TIME><SURNAME HASHCODE>
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

        connection.pstmt.executeQuery();

        String surname = connection.rs.getString("surname");

        connection.closeConnection();

        return "CARD_" + dateTimeToken + surname.hashCode();
    }
}
