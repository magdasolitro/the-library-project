package View.EmployeeView;

import Model.Book;
import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Rankings;
import Model.Utils.DAOs.BookDAO;
import Model.Utils.DaoImpl.BookDaoImpl;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class RankingsView {

    public static GridPane buildRankingView(ArrayList<Rankings> ranking){

        // order ranking array by currentPosition
        ranking.sort(Comparator.comparing(Rankings::getCurrentPosition));

        GridPane rankingGP = new GridPane();

        Font labelsFont = new Font("Avenir Next Bold", 20);
        Font infoFont = new Font("Avenir Book", 20);

        Label rankLabel = new Label("Rank");
        rankLabel.setFont(labelsFont);

        Label bookTitleLabel = new Label("Title");
        bookTitleLabel.setFont(labelsFont);

        Label weeksInPositionLabel = new Label(" # weeks in this placement");
        weeksInPositionLabel.setFont(labelsFont);

        rankingGP.add(rankLabel, 0, 0);
        rankingGP.add(bookTitleLabel, 1, 0);
        rankingGP.add(weeksInPositionLabel, 3, 0);

        BookDAO bookDAO = new BookDaoImpl();

        int i = 1;

        for(Rankings r : ranking){
            try {
                Book currentBook = bookDAO.getBook(r.getBook());

                Label currentPosition = new Label("" + r.getCurrentPosition());
                currentPosition.setFont(infoFont);

                Label bookTitle = new Label(currentBook.getTitle());
                bookTitle.setFont(infoFont);

                Label weeksInPosition = new Label("" + r.getWeeksInPosition());
                weeksInPosition.setFont(infoFont);

                rankingGP.add(currentPosition, 0, i);
                rankingGP.add(bookTitle, 1, i);
                rankingGP.add(weeksInPosition, 3, i);

                i++;

            } catch (InvalidStringException | IllegalValueException | SQLException e) {
                e.printStackTrace();
            }
        }

        rankingGP.setHgap(30);
        rankingGP.setVgap(10);

        return rankingGP;
    }
}
