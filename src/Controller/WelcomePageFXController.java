package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageFXController implements Initializable {
    @FXML
    private void handleUserBottonClick(MouseEvent evt){
        System.out.println("You clicked me!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
