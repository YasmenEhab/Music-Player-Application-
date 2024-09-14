/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

/**
 *
 * @author Ammar
 */
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.StackPane;

public class MediaPlayerController implements Initializable {

    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    static int songnumber=0;
    
    private File imgdirectory;
    private File[] imgfiles;
    private ArrayList<File> imgsongs;
    private int imgsongnumber;
    private Image img;
    
    private String[] snimg;
    
    private boolean next, prev, play;
    Media media;

    private String path;
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;


    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressBar;

    @FXML
    private StackPane pane;
    @FXML
    private Label songname;
    @FXML
    private ImageView bgimg;
    @FXML
    private ImageView playimg;
    @FXML
    private ImageView pauseimg;
    @FXML
    private ImageView sngimg;
    
    
    
    public void Set_sngNumber(int n){
        this.songnumber = n;
    }
    
    public int get_sngNumber(){
       return  this.songnumber ;
    }
    @Override
    //@SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println(songnumber);
        Path musicPath = Paths.get("music"); // Adjust this path based on your project structure

        // Initialize songs list and directory
        songs = new ArrayList<>();
        directory = musicPath.toFile(); // Update directory to use the new musicPath
        files = directory.listFiles();
        
        

        if (files != null) {
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(".mp3") || f.isFile() && f.getName().endsWith(".mp4")) { // Ensure only .mp3 files are added
                    songs.add(f);
                }
            }
            
        Collections.sort(songs, (file1, file2) -> file1.getName().compareToIgnoreCase(file2.getName()));


        pane.autosize();
        Path imgmusicPath = Paths.get("songimg"); // Adjust this path based on your project structure
        System.out.println(imgmusicPath.getFileName().toAbsolutePath());
        // Initialize songs list and directory
        imgsongs = new ArrayList<>();
        imgdirectory = imgmusicPath.toFile(); // Update directory to use the new musicPath
        imgfiles = imgdirectory.listFiles();
        //System.out.println(Arrays.toString(imgfiles));
        
        System.out.println("before img");
        if (imgfiles != null) {
            for (File f : imgfiles) {
                if (f.isFile() /*&& f.getName().endsWith(".jpg")*/) { // Ensure only .mp3 files are added
                    imgsongs.add(f);
                    //System.out.println(f.toURI().toString());
                }
            }
        }
        
       Collections.sort(imgsongs, (file1, file2) -> file1.getName().compareToIgnoreCase(file2.getName()));
        
            bgimg.setFitHeight(pane.getHeight());
            bgimg.setFitWidth(pane.getWidth());

            if (!songs.isEmpty()) {
                loadAndPlayNewMedia();

                // Bind mediaView size to scene size
                mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

                // Bind background image size to scene size
                bgimg.fitWidthProperty().bind(Bindings.selectDouble(bgimg.sceneProperty(), "width"));
                bgimg.fitHeightProperty().bind(Bindings.selectDouble(bgimg.sceneProperty(), "height"));

                // Set up volume control
                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                volumeSlider.valueProperty().addListener((Observable observable) -> {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                });

                mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) -> {
                progressBar.setValue(newValue.toSeconds());
                });

                // Set up keyboard controls
                pane.setOnKeyPressed((KeyEvent event) -> {
                    if (mediaPlayer != null) {
                        switch (event.getCode()) {
                            case O:
                                //if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED || mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                                playMedia();
                                // }
                                break;
                            case Q:
                                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                                    pauseMedia();
                                }
                                break;
                            case LEFT:
                                //increaseVolume();
                                rewindBy5Seconds();
                                break;
                            case RIGHT:
                                //decreaseVolume();
                                skipBy5Seconds();
                                break;
                            case UP:
                                increaseVolume();
                                //mediaPlayer.setVolume(Math.min(mediaPlayer.getVolume() + 0.1, 1.0));
                                //volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                                break;
                            case DOWN:
                                decreaseVolume();
                                //mediaPlayer.setVolume(Math.max(mediaPlayer.getVolume() - 0.1, 0.0));
                                //volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                                break;
                            case S:
                                stopMedia();
                                //stopvideo_priv();
                                break;
                            case N:
                                playNextMedia();
                                //nextvideo_priv();
                                //playvideo_priv();
                                break;
                            case P:
                                playPreviousMedia();
                                //prevvideo_priv();
                                //playvideo_priv();
                                break;
                            default:
                                break;
                        }
                    }
                });
                // Request focus on pane to capture keyboard events
                // pane.requestFocus();  // <--- Request focus here

                pane.setFocusTraversable(true); // Ensure pane can receive keyboard input
            }
        }
    }
//**************************************general functions********************************
    private void increaseVolume() {
        mediaPlayer.setVolume(Math.min(mediaPlayer.getVolume() + 0.1, 1.0));
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
    }

    private void decreaseVolume() {
        mediaPlayer.setVolume(Math.max(mediaPlayer.getVolume() - 0.1, 0.0));
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
    }

    private void stopMedia() {

        mediaPlayer.stop();

    }

    private void pauseMedia() {
        mediaPlayer.pause();
    }

    private void skipBy5Seconds() {

        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(5)));
        progressBar.setValue(mediaPlayer.getCurrentTime().toSeconds());
    }

    private void rewindBy5Seconds() {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(-5)));
        progressBar.setValue(mediaPlayer.getCurrentTime().toSeconds());
    }

    private void playNextMedia() {

        if (songnumber < songs.size() - 1) {
            songnumber++;
        } else {
            songnumber = 0; // Loop back to the first song
        }
        loadAndPlayNewMedia();
    }

    private void loadAndPlayNewMedia() {
        double currentVolume = mediaPlayer != null ? mediaPlayer.getVolume() : 1.0; // Store the current volume, default to 1.0 if mediaPlayer is null

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose(); // Clean up current MediaPlayer
        }

        media = new Media(songs.get(songnumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        songname.setText(songs.get(songnumber).getName()); // Update song name label

        // Reset progress bar
        progressBar.setValue(0);

        // Set the volume of the new media player to the stored volume
        mediaPlayer.setVolume(currentVolume);
        volumeSlider.setValue(currentVolume * 100); // Update the volume slider position

        // Set progress bar maximum when media is ready
        mediaPlayer.setOnReady(() -> {
            javafx.util.Duration total = media.getDuration();
            progressBar.setMax(total.toSeconds());
        });

        // Update progress bar as the media plays
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) -> {
            progressBar.setValue(newValue.toSeconds());
        });

        // Automatically play the next song when the current one finishes
        mediaPlayer.setOnEndOfMedia(() -> {
            playNextMedia();
        });
        
        if(songs.get(songnumber).isFile() && songs.get(songnumber).getName().endsWith(".mp3")){
            img = new Image(imgsongs.get(songnumber).toURI().toString());
            sngimg.setImage(img);
        }else {
            
        }

       //img = new Image("../songimg/yehmkfeeh.jpg");
       

        // Play the new media
        playMedia();
    }

    private void playPreviousMedia() {

        if (songnumber > 0) {
            songnumber--;
        } else {
            songnumber = songs.size() - 1; // Loop to the last song
        }
        loadAndPlayNewMedia();

    }

    private void playMedia() {
        mediaPlayer.setOnReady(() -> {
            javafx.util.Duration total = media.getDuration();
            progressBar.setMax(total.toSeconds());
        });
        mediaPlayer.play();
        

        //sngimg.setImage("C:\\Users\\tasbeeh\\Documents\\NetBeansProjects\\MediaPlayer\\src\\songimg\\yehmkfeeh.jpg");
        // mediaPlayer.setRate(1);

    }
    
//***************************************************************************************//
    
//*******************************Mouse Handling functions****************************************//
    @FXML
    private void playImgMouse(MouseEvent event) {
        playMedia();
    }

    @FXML
    private void playImgTouch(TouchEvent event) {
        playMedia();
    }
    
    @FXML
    private void pauseImgTouch(TouchEvent event) {
        pauseMedia();
    }

    @FXML
    private void pauseImgMouse(MouseEvent event) {
        pauseMedia();
    }
    
    @FXML
    private void resetImgMouse(MouseEvent event) {
        stopMedia();
    }

    @FXML
    private void resetImgTouch(TouchEvent event) {
        stopMedia();
    }
    
    @FXML
    private void previousImgTouch(TouchEvent event) {
        playPreviousMedia();
    }

    @FXML
    private void previousImgMouse(MouseEvent event) {
        playPreviousMedia();
    }
    
    @FXML
    private void back5ImgTouch(TouchEvent event) {
        rewindBy5Seconds();
    }

    @FXML
    private void back5ImgMouse(MouseEvent event) {
        rewindBy5Seconds();
    }

    @FXML
    private void skip5ImgTouch(TouchEvent event) {
        skipBy5Seconds();
    }

    @FXML
    private void skip5ImgMouse(MouseEvent event) {
        skipBy5Seconds();
    }
    
    @FXML
    private void nextImgTouch(TouchEvent event) {
        playNextMedia();
    }

    @FXML
    private void nextImgMouse(MouseEvent event) {
        playNextMedia();
    }  
    
    
}
