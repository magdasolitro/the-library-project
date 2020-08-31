package Controller.EmployeeController;

import Model.GenresEnum;
import Model.Rankings;
import Model.Utils.DAOs.RankingsDAO;
import Model.Utils.DaoImpl.RakingsDaoImpl;
import View.EmployeeView.RankingsView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateRakingsPageFXController implements Initializable {

    @FXML
    private Button goBackButton;

    @FXML
    AnchorPane leftPane, rightPane;

    private ChoiceBox<Object> genresChoiceBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setId("goback-button");
        goBackButton.getStylesheets().add("/CSS/style.css");

        RankingsDAO rankingsDAO = new RakingsDaoImpl();

        genresChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("All",
                "Autobiography", "Crime Fiction", "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction"));

        leftPane.getChildren().add(genresChoiceBox);

        genresChoiceBox.relocate(35, 300);
        genresChoiceBox.setPrefWidth(175);
        genresChoiceBox.setPrefHeight(37);

        genresChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {

            try {
                ArrayList<Rankings> rankingsByGenre =
                        new ArrayList<>(rankingsDAO.getRankingByGenre(GenresEnum.values()[newValue.intValue()]));
                GridPane rankingsGP = RankingsView.buildRankingView(rankingsByGenre);

                rightPane.getChildren().add(rankingsGP);
                rankingsGP.relocate(50, 50);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    public void handleGoBackButton() {

    }


    public void updateRankings() {

    }
}
