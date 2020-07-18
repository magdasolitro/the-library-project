package Model.Utils;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DatabaseConnection {
    /* a connection to a specific database */
    public Connection conn;

    /* will store a precompiled SQL statement. */
    public PreparedStatement pstmt;

    /* will store the table of data representing the result of a query on the database */
    public ResultSet rs;


    /**
     * Connection to library.db database
     */
    public void openConnection(){
        try {
            // load JDBC driver for SQLite
            Class.forName("org.sqlite.JDBC");

            // establish connection providing the database URL
            conn = DriverManager.getConnection("jdbc:sqlite:src/Database/library2.db");

            System.out.println("Database connection successful");
        } catch (SQLException e) {
            System.out.println("An error occurred trying to establish a " +
                    "connection with the database: " + e.getMessage());
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
