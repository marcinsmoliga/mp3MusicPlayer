package com.example.mp3MusicPlayer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuPaneController {

	@FXML
	private MenuItem fileMenuItem;

	@FXML
	private MenuItem directoryMenuItem;

	@FXML
	private MenuItem closeMenuItem;

	@FXML
	private MenuItem aboutMenuItem;

	public void initialize() {
		System.out.println("MenuPane created");
	}

}
