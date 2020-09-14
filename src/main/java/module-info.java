module mp3MusicPlayer {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.mp3MusicPlayer.app to javafx.graphics;
    opens com.example.mp3MusicPlayer.controller to javafx.fxml;
}