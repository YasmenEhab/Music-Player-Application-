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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author tasbeeh
 */
public class IntroScene1Controller implements Initializable {

    @FXML
    private ImageView introbg;
    private AnchorPane anchorp;
    private MediaPlayerController mpc;
    @FXML
    private AnchorPane intropane;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mpc = new MediaPlayerController();
        
                        // Bind background image size to scene size
        //introbg.fitWidthProperty().bind(Bindings.selectDouble(introbg.sceneProperty(), "width"));
        //introbg.fitHeightProperty().bind(Bindings.selectDouble(introbg.sceneProperty(), "height"));
        
        intropane.autosize();
        
        intropane.setOnKeyPressed((KeyEvent event) -> {
                    
        switch (event.getCode()) {
                            case DIGIT1:
                             mpc.Set_sngNumber(0);
                             switchHandle();
                                break;
                            case DIGIT2:
                             mpc.Set_sngNumber(1);
                             switchHandle();
                                break;
                            case DIGIT3:
                             mpc.Set_sngNumber(2);
                             switchHandle();
                                break;
                            case DIGIT4:
                             mpc.Set_sngNumber(3);
                             switchHandle();
                                break;

                            default:
                                break;
                        }
                    }
                );
        intropane.setFocusTraversable(true);

        
        
    }
    
    private void switchHandle(){
         try {
              MediaPlayer.switchScene("MediaPlayer.fxml"); // Switch to Scene2.fxml
             } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void firstHandle(ActionEvent event) {
        mpc.Set_sngNumber(0);
        switchHandle();
    }

    @FXML
    private void secondHandle(ActionEvent event) {
        mpc.Set_sngNumber(1);
        switchHandle();

    }

    @FXML
    private void thirdHandle(ActionEvent event) {
        mpc.Set_sngNumber(2);
        System.out.println(mpc.get_sngNumber());
        switchHandle();

    }

    @FXML
    private void fourthHandle(ActionEvent event) {
        mpc.Set_sngNumber(3);
        switchHandle();

    }
    }    
    

