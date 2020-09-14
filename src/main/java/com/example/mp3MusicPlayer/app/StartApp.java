package com.example.mp3MusicPlayer.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage) throws Exception {
        Pane mainPane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Mp3 Music Player");
        stage.show();
    }
}
