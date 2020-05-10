package Model.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

// questa classe fornisce i metodi per interagire correttamente con il database
// in particolare, fornisce i metodi per inserire i dati nelle tabelle

public class DatabaseConnection {
    public Connection conn = null;    // a connection to a specific database
    public PreparedStatement pstmt = null;  // will store a precompiled SQL statement.
    public ResultSet rs = null;             // will store the table of data representing
                                            // the result of a query on the database

    /**
     * Connection to library.db database
     */
    public void openConnection(){
        try {
            // load JDBC driver for SQLite
            Class.forName("org.sqlite.JDBC");
            // establish connection providing the database URL
            conn = DriverManager.getConnection("jdbc:sqlite:library.db");
            System.out.println("Database connection successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Close connection to library.db database
     */
    public void closeConnection(){
        try{
            conn.close();
            System.out.println("Database connection closed");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
