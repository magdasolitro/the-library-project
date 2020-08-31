package Model.Utils.DAOs;

import Model.GenresEnum;
import Model.Rankings;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RankingsDAO {

    ArrayList<Rankings> getRankingByGenre(GenresEnum genre) throws SQLException;

    void incrementSoldCopies(String ISBN, int quantity) throws SQLException;

    void setCopiesToZero(String ISBN) throws SQLException;

    int getSoldCopies(String ISBN) throws SQLException;
}
