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

        GridPane libroCardGP = new GridPane();

        int i = 0;

        for (LibroCard lc : libroCards) {
            VBox currentLibroCard = buildSingleLibroCardView(lc);
            libroCardGP.add(currentLibroCard, 0, i);
            GridPane.setMargin(currentLibroCard, new Insets(0, 0, 0, 30));
            i++;
        }

        libroCardGP.setVgap(40);
        libroCardGP.setHgap(20);

        scrollPane.setContent(libroCardGP);

        scrollPane.setId("librocard-scrollpane");
        scrollPane.getStylesheets().add("/CSS/style.css");

        return scrollPane;

    }

    private static VBox buildSingleLibroCardView(LibroCard libroCard){
        VBox singleLibroCardVB = new VBox(10);

        Label cardIDLabel;
        Label emailLabel;
        Label issueDateLabel;
        Label pointsLabel;

        cardIDLabel = new Label(libroCard.getCardID());
        cardIDLabel.setFont(new Font("Avenir Next Bold", 20));

        emailLabel = new Label(libroCard.getUser());
        emailLabel.setFont(new Font("Avenir Book", 20));

        issueDateLabel = new Label(libroCard.getIssueDate());
        issueDateLabel.setFont(new Font("Avenir Book", 20));

        pointsLabel = new Label("" + libroCard.getPoints());
        pointsLabel.setFont(new Font("Avenir Book", 20));

        singleLibroCardVB.getChildren().addAll(cardIDLabel, emailLabel, issueDateLabel, pointsLabel);

        singleLibroCardVB.setId("librocard-pane");
        singleLibroCardVB.getStylesheets().add("/CSS/style.css");

        return singleLibroCardVB;
    }

}
