package com.example.mp3MusicPlayer.controller;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import com.example.mp3MusicPlayer.mp3.Mp3Song;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContentPaneController {
	private static final String TITLE_COLUMN = "Title";
	private static final String AUTHOR_COLUMN = "Author";
	private static final String ALBUM_COLUMN = "Album";

	@FXML
	private TableView<Mp3Song> contentTable;

	public TableView<Mp3Song> getContentTable() {
		return contentTable;
	}

	public void initialize() {
		configureTableColumns();
		createTestData();
	}

	private void createTestData() {
		ObservableList<Mp3Song> items = contentTable.getItems();
		Mp3Song mp3SongFromPath = createMp3SongFromPath("song.mp3");
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
			return null;
		}

	}

	private void configureTableColumns() {
		TableColumn<Mp3Song, String> titleColumn = new TableColumn<>(TITLE_COLUMN);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Mp3Song, String> authorColumn = new TableColumn<>(AUTHOR_COLUMN);
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

		TableColumn<Mp3Song, String> albumColumn = new TableColumn<>(ALBUM_COLUMN);
		albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));

		contentTable.getColumns().add(titleColumn);
		contentTable.getColumns().add(authorColumn);
		contentTable.getColumns().add(albumColumn);
	}

}
