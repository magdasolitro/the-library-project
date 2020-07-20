import Controller.LastOpenedPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalTime;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXML/WelcomePageFX.fxml"));
        Parent root = loader.load();

        LastOpenedPageController.setLastOpenedPage("../../FXML/WelcomePageFX.fxml");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        System.out.println(LocalTime.now());

    }

    public static void main(String[] args){
        launch(args);
    }
}