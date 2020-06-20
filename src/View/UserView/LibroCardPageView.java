package View.UserView;

import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DaoImpl.LibroCardDaoImpl;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class LibroCardPageView {

    public static GridPane buildLibroCardView(String email){
        GridPane libroCardInfos = new GridPane();

        Font infoFont = new Font("Avenir Book", 20);

        Label cardIDLabel;
        Label issueDateLabel;
        Label pointsLabel;

        LibroCard userLibroCard;

        LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

        try {
            userLibroCard = libroCardDAO.getUserLibroCard(email);

            cardIDLabel  = new Label(userLibroCard.getCardID());
            issueDateLabel = new Label(userLibroCard.getIssueDate());
            pointsLabel = new Label("" + userLibroCard.getPoints());

            RowConstraints cardIDRow = new RowConstraints();
            cardIDRow.setValignment(VPos.CENTER);
            cardIDRow.setPercentHeight(100.0 / 3);

            RowConstraints issueDateRow = new RowConstraints();
            issueDateRow.setValignment(VPos.CENTER);
            issueDateRow.setPercentHeight(100.0 / 3);

            RowConstraints pointsRow = new RowConstraints();
            pointsRow.setValignment(VPos.CENTER);
            pointsRow.setPercentHeight(100.0 / 3);

            cardIDLabel.setFont(infoFont);
            issueDateLabel.setFont(infoFont);
            pointsLabel.setFont(infoFont);

            libroCardInfos.add(cardIDLabel, 0,0);
            libroCardInfos.add(issueDateLabel, 0, 1);
            libroCardInfos.add(pointsLabel, 0, 2);

            libroCardInfos.setVgap(20);

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return libroCardInfos;
    }
}
