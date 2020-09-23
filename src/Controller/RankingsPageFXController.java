package Controller;

import Model.GenresEnum;
import Model.Rankings;
import Model.Utils.DAOs.RankingsDAO;
import Model.Utils.DaoImpl.RakingsDaoImpl;
import View.RankingsView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RankingsPageFXController implements Initializable {

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

        genresChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Autobiography",
                "Crime Fiction", "Fantasy", "History", "Narrative", "Philosophy of Science",
                "Politics", "Science Fiction"));

        leftPane.getChildren().add(genresChoiceBox);

        genresChoiceBox.relocate(35, 300);
        genresChoiceBox.setPrefWidth(175);
        genresChoiceBox.setPrefHeight(37);

        genresChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!rightPane.getChildren().isEmpty()) {
                    rightPane.getChildren().clear();
                }

                ArrayList<Rankings> rankingsByGenre =
                        new ArrayList<>(rankingsDAO.getRankingByGenre(GenresEnum.values()[newValue.intValue()]));
                GridPane rankingsGP = RankingsView.buildRankingView(rankingsByGenre);

                VBox rankingsVB = new VBox(20);

                Label genreLabel = new Label(GenresEnum.values()[newValue.intValue()].toString());
                genreLabel.setFont(new Font("Avenir Next Bold", 35));

                rankingsVB.getChildren().addAll(genreLabel, rankingsGP);

                rightPane.getChildren().add(rankingsVB);
                rankingsVB.relocate(50, 50);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        // show all rankings
        ScrollPane rankingsSP = new ScrollPane();
        VBox allRankingsVB = new VBox(10);

        for (GenresEnum g : GenresEnum.values()){
            try {
                Label genreLabel = new Label(g.toString());
                genreLabel.setFont(new Font("Avenir Next Bold", 35));

                ArrayList<Rankings> rankingsByGenre = new ArrayList<>(rankingsDAO.getRankingByGenre(g));
                GridPane rankingsGP = RankingsView.buildRankingView(rankingsByGenre);

                //rankingsGP.prefWidth(900);

                allRankingsVB.getChildren().addAll(genreLabel, rankingsGP);
                allRankingsVB.setId("rankings-scrollpane");
                allRankingsVB.getStylesheets().add("/CSS/style.css");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        rankingsSP.setContent(allRankingsVB);
        rankingsSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rankingsSP.setId("rankings-scrollpane");
        rankingsSP.getStylesheets().add("/CSS/style.css");

        rankingsSP.setPrefHeight(600);
        rankingsSP.setPrefWidth(925);

        allRankingsVB.setPrefWidth(910);

        rightPane.getChildren().add(rankingsSP);
        rankingsSP.relocate(50, 50);

    }


    public void handleGoBackButton() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader();
            if(GeneralLoginController.getLoginInstance().substring(0,6).equals("NOTREG")) {
                loader.setLocation(getClass().getResource("/FXML/UserFXML/UserNotRegFXML/UserNRMainPageFX.fxml"));
            } else{
                loader.setLocation(getClass().getResource("/FXML/UserFXML/UserRegFXML/UserMainPageFX.fxml"));
            }

            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
