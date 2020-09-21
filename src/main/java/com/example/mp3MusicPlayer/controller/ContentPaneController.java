package com.example.mp3MusicPlayer.controller;

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

	public void initialize() {
		configureTableColumns();
		createTestData();
	}

	private void createTestData() {
		ObservableList<Mp3Song> items = contentTable.getItems();
		items.add(new Mp3Song("a", "a", "a", "a"));
		items.add(new Mp3Song("b", "b", "b", "b"));
		items.add(new Mp3Song("c", "c", "c", "c"));
		items.add(new Mp3Song("d", "d", "d", "d"));
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
