package mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MediaPlayer extends Application {

    private static Stage primarystage;
    private Thread remoteControlThread;

    @Override
    public void start(Stage primarystage) throws Exception {
        MediaPlayer.primarystage = primarystage;
        Parent root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        Scene scene = new Scene(root);
        primarystage.setScene(scene);
        primarystage.setTitle("Media Player");
        primarystage.show();

        // Start the remote control thread

    }

    public static void switchScene(String fxmlFile) throws Exception {
        Parent newRoot = FXMLLoader.load(MediaPlayer.class.getResource(fxmlFile));
        Scene newScene = new Scene(newRoot);
        primarystage.setScene(newScene);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // Ensure the remote control thread is stopped when the application exits
        if (remoteControlThread != null && remoteControlThread.isAlive()) {
            remoteControlThread.interrupt();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Inner class RemoteControlRunnable

}

