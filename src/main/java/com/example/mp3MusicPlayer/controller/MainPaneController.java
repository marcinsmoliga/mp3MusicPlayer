package com.example.mp3MusicPlayer.controller;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import com.example.mp3MusicPlayer.mp3.Mp3Parser;
import com.example.mp3MusicPlayer.mp3.Mp3Song;
import com.example.mp3MusicPlayer.player.Mp3Player;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPaneController {
    @FXML
    private ControlPaneController controlPaneController;
    @FXML
    private ContentPaneController contentPaneController;
    @FXML
    private MenuPaneController menuPaneController;
    private Mp3Player player;

    public void initialize() {
        createPlayer();
        configureTableClick();
        configureButtons();
        configureMenu();
    }

    private void configureMenu() {
        MenuItem openFile = menuPaneController.getFileMenuItem();
        MenuItem openDir = menuPaneController.getDirectoryMenuItem();

        openFile.setOnAction(event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mp3","*.mp3"));
            File file = fc.showOpenDialog(new Stage());
            try {
                contentPaneController.getContentTable().getItems().add(Mp3Parser.createMp3Song(file));
                showMessage("The file " + file.getName() + " was successfully loaded");
            } catch (Exception e) {
                showMessage("The file " + file.getName() + " cannot be opened");
            }
        });

        openDir.setOnAction(event -> {
            DirectoryChooser dc = new DirectoryChooser();
            File dir = dc.showDialog(new Stage());
            try {
                contentPaneController.getContentTable().getItems().addAll(Mp3Parser.createMp3List(dir));
                showMessage("Files have been loaded from a directory " + dir.getName());
            } catch (Exception e) {
                showMessage("An error occurred while reading files from the directory " + dir.getName());

            }
        });
    }

    private void showMessage(String message) {
        controlPaneController.getMessageTextField().setText(message);
    }

    private void createPlayer() {
        ObservableList<Mp3Song> items = contentPaneController.getContentTable().getItems();
        player = new Mp3Player(items);
    }

    private void configureTableClick() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        contentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = contentTable.getSelectionModel().getSelectedIndex();
                playSelectedSong(selectedIndex);
            }
        });
    }

    private void playSelectedSong(int selectedIndex) {
        player.loadSong(selectedIndex);
        configureProgressBar();
        configureVolume();
        controlPaneController.getPlayButton().setSelected(true);
    }

    private void configureProgressBar() {
        Slider progressSlider = controlPaneController.getProgressSlider();
        player.getMediaPlayer().setOnReady(() -> progressSlider.setMax(player.getLoadedSongLength()));
        player.getMediaPlayer().currentTimeProperty().addListener((arg, oldVal, newVal) ->
                progressSlider.setValue(newVal.toSeconds()));
        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressSlider.isValueChanging()) {
                player.getMediaPlayer().seek(Duration.seconds(newValue.doubleValue()));
            }
        });
    }

    private void configureVolume() {
        double maxValue = 1.0;
        Slider volumeSlider = controlPaneController.getVolumeSlider();
        volumeSlider.valueProperty().unbind();
        volumeSlider.setMax(maxValue);
        volumeSlider.valueProperty().bindBidirectional(player.getMediaPlayer().volumeProperty());
    }

    private void configureButtons() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        ToggleButton playButton = controlPaneController.getPlayButton();
        Button prevButton = controlPaneController.getPreviousButton();
        Button nextButton = controlPaneController.getNextButton();

        playButton.setOnAction(event -> {
            if (playButton.isSelected()) {
                player.play();
            } else {
                player.stop();
            }
        });

        nextButton.setOnAction(event -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() + 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });

        prevButton.setOnAction(event -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() - 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });
    }

    private void addTestMp3() {
        ObservableList<Mp3Song> items = contentPaneController.getContentTable().getItems();
        Mp3Song mp3SongFromPath = createMp3SongFromPath("song.mp3");
        items.add(mp3SongFromPath);
        items.add(mp3SongFromPath);
        items.add(mp3SongFromPath);
    }

    private Mp3Song createMp3SongFromPath(String filePath) {
        File file = new File(filePath);
        try {
            MP3File mp3File = new MP3File(file);
            String absolutePath = file.getAbsolutePath();
            String title = mp3File.getID3v2Tag().getSongTitle();
            String author = mp3File.getID3v2Tag().getLeadArtist();
            String album = mp3File.getID3v2Tag().getAlbumTitle();
            return new Mp3Song(title, author, album, absolutePath);
        } catch (IOException | TagException e) {
            e.printStackTrace();
            return null; //ignore
        }
    }
}
