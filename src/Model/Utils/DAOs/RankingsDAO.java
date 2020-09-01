package Model.Utils.DAOs;

import Model.GenresEnum;
import Model.Rankings;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RankingsDAO {

    ArrayList<Rankings> getRankingByGenre(GenresEnum genre) throws SQLException;

    void incrementSoldCopies(String ISBN, int quantity) throws SQLException;

    void incrementWeeksInPosition(String ISBN) throws SQLException;

    void resetWeeksInPosition(String ISBN) throws SQLException;

    void resetSoldCopies(String ISBN) throws SQLException;

    void setCurrentPosition(String ISBN, int newPosition) throws SQLException;

    int getSoldCopies(String ISBN) throws SQLException;
}
