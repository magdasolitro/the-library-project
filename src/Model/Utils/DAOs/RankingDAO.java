package Model.Utils.DAOs;

import Model.Book;
import Model.GenresEnum;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RankingDAO {

    ArrayList<String> getAbsoluteRanking() throws SQLException;

    ArrayList<Book> getRankingByGenre(GenresEnum genre) throws SQLException;

    void incrementSoldCopies(String ISBN) throws SQLException;

    void setCopiesToZero(String ISBN) throws SQLException;

    int getSoldCopies(String ISBN) throws SQLException;
}
