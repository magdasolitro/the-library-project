package Model;

import java.text.SimpleDateFormat;
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
    public LibroCard(String user){
        // this.cardID = how to generate cardID?
        this.user = user;
        this.points = 0;
        this.issueDate = getCurrentDate();
    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }
}
