package com.example.mp3MusicPlayer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

public class ControlPaneController {

	@FXML
	private Button previousButton;

	@FXML
	private ToggleButton playButton;

	@FXML
	private Button nextButton;

	@FXML
	private Slider volumeSlider;

	@FXML
	private Slider progressSlider;

	@FXML
	private TextField messageTextField;

	public void initialize() {
		configureButtons();
		configureVolume();
		configureSliders();
	}

	public Button getPreviousButton() {
		return previousButton;
	}

	public ToggleButton getPlayButton() {
		return playButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public Slider getVolumeSlider() {
		return volumeSlider;
	}

	public Slider getProgressSlider() {
		return progressSlider;
	}

	public TextField getMessageTextField() {
		return messageTextField;
	}

	private void configureVolume() {
		volumeSlider.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->{
			System.out.println("Volume Slider works!");
		});
	}

	private void configureButtons() {
		previousButton.setOnAction(event -> {
			System.out.println("Previous song");
		});
		nextButton.setOnAction(event -> {
			System.out.println("Next song");
		});
		playButton.setOnAction(event -> {
			if(playButton.isSelected()) {
				System.out.println("Play");
			} else {
				System.out.println("Stop");
			}
		});
	}

	private void configureSliders() {
		volumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
				System.out.println("Change of volume " + newValue.doubleValue()));

		progressSlider.valueProperty().addListener(x ->
				System.out.println("Song shift"));
	}

}
