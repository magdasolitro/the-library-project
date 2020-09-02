package View.UserView;

import Model.LibroCard;
import Model.Utils.DAOs.LibroCardDAO;
import Model.Utils.DaoImpl.LibroCardDaoImpl;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class LibroCardPageView {

    public static Pane buildLibroCardView(String email){
        Pane libroCardPane = new Pane();
        GridPane gplibroCard = new GridPane();

        Font labelFont = new Font("Avenir Next Bold", 20);
        Font infoFont = new Font("Avenir Book", 20);

        Label cardIDLabel, cardIDInfo;
        Label issueDateLabel, issueDateInfo;
        Label pointsLabel, pointsInfo;

        cardIDLabel = new Label("CardID: ");
        issueDateLabel = new Label("Issue Date: ");
        pointsLabel = new Label("Points: ");

        cardIDLabel.setFont(labelFont);
        issueDateLabel.setFont(labelFont);
        pointsLabel.setFont(labelFont);

        gplibroCard.add(cardIDLabel, 0,1);
        gplibroCard.add(issueDateLabel, 0,2);
        gplibroCard.add(pointsLabel, 0,3);

        LibroCard userLibroCard;

        LibroCardDAO libroCardDAO = new LibroCardDaoImpl();

        try {
            userLibroCard = libroCardDAO.getUserLibroCard(email);

            cardIDInfo  = new Label(userLibroCard.getCardID());
            issueDateInfo = new Label(userLibroCard.getIssueDate());
            pointsInfo = new Label("" + userLibroCard.getPoints());

            cardIDInfo.setFont(infoFont);
            issueDateInfo.setFont(infoFont);
            pointsInfo.setFont(infoFont);

            RowConstraints voidRow = new RowConstraints();
            voidRow.setValignment(VPos.CENTER);
            voidRow.setPercentHeight(100.0 / 4);

            RowConstraints cardIDInfoRow = new RowConstraints();
            cardIDInfoRow.setValignment(VPos.CENTER);
            cardIDInfoRow.setPercentHeight(100.0 / 4);

            RowConstraints issueDateInfoRow = new RowConstraints();
            issueDateInfoRow.setValignment(VPos.CENTER);
            issueDateInfoRow.setPercentHeight(100.0 / 4);

            RowConstraints pointsInfoRow = new RowConstraints();
            pointsInfoRow.setValignment(VPos.CENTER);
            pointsInfoRow.setPercentHeight(100.0 / 4);

            gplibroCard.getRowConstraints().addAll(cardIDInfoRow, issueDateInfoRow, pointsInfoRow);

            gplibroCard.add(cardIDInfo, 1,1);
            gplibroCard.add(issueDateInfo, 1, 2);
            gplibroCard.add(pointsInfo, 1, 3);

            gplibroCard.setVgap(30);
            gplibroCard.setHgap(60);

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        libroCardPane.getChildren().add(gplibroCard);
        libroCardPane.setPadding(new Insets(10, 10, 10, 10));

        libroCardPane.setId("librocard-pane");
        libroCardPane.getStylesheets().add("/CSS/style.css");

        return libroCardPane;
    }
}
