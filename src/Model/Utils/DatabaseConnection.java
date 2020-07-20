package Model.Utils;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DatabaseConnection {
    /* a connection to a specific database */
    public Connection conn = null;

    /* will store a precompiled SQL statement. */
    public PreparedStatement pstmt;

    /* will store the table of data representing the result of a query on the database */
    public ResultSet rs;


    /**
     * Connection to library.db database
     */
    public void openConnection() {
        try {
            if(conn == null) {
                // load JDBC driver for SQLite
                Class.forName("org.sqlite.JDBC");

                // establish connection providing the database URL
                conn = DriverManager.getConnection("jdbc:sqlite:src/Database/library2.db");
            }

            System.out.println("Database connection successful");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Close connection to library.db database
     */
    public void closeConnection(){
        try{
            /*if(rs != null){
                rs.close();
            }
            if(pstmt != null){
                pstmt.close();
            }*/
            conn.close();
            System.out.println("Database connection closed");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
