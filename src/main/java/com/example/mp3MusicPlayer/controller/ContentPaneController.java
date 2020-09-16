package com.example.mp3MusicPlayer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ContentPaneController {

	@FXML
	private TableView<?> contentTable;

	public void initialize() {
		System.out.println("ContentPane created");
	}

}
