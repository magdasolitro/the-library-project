package Model.Utils.DaoImpl;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.GenresEnum;
import Model.Rankings;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DAOs.RankingsDAO;
import Model.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RakingsDaoImpl implements RankingsDAO {

    @Override
    public ArrayList<Rankings> getRankingByGenre(GenresEnum genre) throws SQLException{
        String sql = "SELECT * FROM rankings";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<Rankings> allRankings = new ArrayList<>();

        while(rs.next()){
            allRankings.add(new Rankings(rs.getString("book"),
                    rs.getInt("soldCopies"),
                    rs.getInt("currentPosition"),
                    rs.getInt("weeksInPosition")));
        }

        BookDAO bookDAO = new BookDaoImpl();

        ArrayList<Rankings> rankingsByGenre = new ArrayList<>();

        for(Rankings r : allRankings){
            try {
                Book currentBook = bookDAO.getBook(r.getBook());

                if(currentBook.getGenre().equals(genre.toString())){
                    rankingsByGenre.add(r);
                }
            } catch (InvalidStringException | IllegalValueException e) {
                e.printStackTrace();
            }
        }

        return rankingsByGenre;
    }

    @Override
    public void incrementSoldCopies(String ISBN, int quantity) throws SQLException {
        String sql = "UPDATE rankings SET soldCopies = soldCopies + ? " +
                     "WHERE book = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setInt(1, quantity);
        pstmt.setString(2, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();

    }

    @Override
    public void setCopiesToZero(String ISBN) throws SQLException{
        String sql = "UPDATE rankings SET soldCopies = 0 " +
                     "WHERE book = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        PreparedStatement pstmt = connection.conn.prepareStatement(sql);

        pstmt.setString(1, ISBN);

        pstmt.executeUpdate();

        pstmt.close();

        connection.closeConnection();
    }

    @Override
    public int getSoldCopies(String ISBN) throws SQLException{
        String sql = "SELECT soldCopies FROM rankings WHERE book = ?";

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
