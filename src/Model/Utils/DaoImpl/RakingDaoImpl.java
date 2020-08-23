package Model.Utils.DaoImpl;

import Model.Book;
import Model.GenresEnum;
import Model.Utils.DAOs.RankingDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RakingDaoImpl implements RankingDAO {

    public ArrayList<String> getAbsoluteRanking() throws SQLException{
        String sql = "SELECT * FROM ranking";

        ArrayList<String> absoluteRanking = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            //absoluteRanking.add()
        }

        return null;
    }

    public ArrayList<Book> getRankingByGenre(GenresEnum genre){
        return null;
    }

    public void incrementSoldCopies(String ISBN) throws SQLException {
        String sql = "UPDATE ranking SET soldCopies = soldCopies + 1 " +
                     "WHERE book = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();

    }


    public void setCopiesToZero(String ISBN) throws SQLException{
        String sql = "UPDATE ranking SET soldCopies = 0 " +
                     "WHERE book = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }


    public int getSoldCopies(String ISBN) throws SQLException{
        String sql = "SELECT soldCopies FROM ranking WHERE book = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, ISBN);

        ResultSet rs = pstmt.executeQuery();

        int soldCopies = rs.getInt("soldCopies");

        pstmt.close();
        rs.close();

        connection.closeConnection();

        return soldCopies;
    }

}
