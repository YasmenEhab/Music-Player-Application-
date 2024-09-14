/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author tasbeeh
 */
public class StartSceneController implements Initializable {

    @FXML
    private ImageView startbg;
    @FXML
    private AnchorPane startpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //startbg.fitWidthProperty().bind(Bindings.selectDouble(startbg.sceneProperty(), "width"));
        //startbg.fitHeightProperty().bind(Bindings.selectDouble(startbg.sceneProperty(), "height"));
        startpane.autosize();
          PauseTransition delay = new PauseTransition(Duration.seconds(3));
          delay.setOnFinished(event -> {
                       try {
              MediaPlayer.switchScene("IntroScene1.fxml"); // Switch to Scene2.fxml
             } catch (Exception e) {
                e.printStackTrace();
            }
           
          });
          delay.play();
    }    
    
}
