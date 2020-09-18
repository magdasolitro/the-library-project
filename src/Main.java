import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.math.BigDecimal;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXML/WelcomePageFX.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        System.out.println(BigDecimal.valueOf(Float.parseFloat("ciao")));

    }

    public static void main(String[] args){
        launch(args);
    }
}