package View.EmployeeView;

import Model.LibroCard;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class AllLibroCardsPageView {

    public static ScrollPane buildLibroCardsView(ArrayList<LibroCard> libroCards){

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.isResizable();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane libroCardContainer = new GridPane();

        int i = 0;

        for (LibroCard lc : libroCards) {
            VBox currentLibroCard = buildSingleLibroCardView(lc);
            libroCardContainer.add(currentLibroCard, 0, i);
            GridPane.setMargin(currentLibroCard, new Insets(20, 0, 20, 30));
            i++;
        }

        libroCardContainer.setVgap(20);
        libroCardContainer.setHgap(20);

        scrollPane.setContent(libroCardContainer);

        return scrollPane;

    }

    private static VBox buildSingleLibroCardView(LibroCard libroCard){
        VBox singleLibroCard = new VBox(10);

        Label cardIDLabel;
        Label emailLabel;
        Label issueDateLabel;
        Label pointsLabel;

        cardIDLabel = new Label(libroCard.getCardID());
        cardIDLabel.setFont(new Font("Avenir Next Bold", 17));

        emailLabel = new Label(libroCard.getUser());
        emailLabel.setFont(new Font("Avenir Book", 17));

        issueDateLabel = new Label(libroCard.getIssueDate());
        issueDateLabel.setFont(new Font("Avenir Book", 17));

        pointsLabel = new Label("" + libroCard.getPoints());
        pointsLabel.setFont(new Font("Avenir Book", 17));

        singleLibroCard.getChildren().addAll(cardIDLabel, emailLabel, issueDateLabel, pointsLabel);

        return singleLibroCard;
    }

}
