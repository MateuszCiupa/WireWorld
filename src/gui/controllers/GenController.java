package gui.controllers;

import base.Grid;
import base.WireWorld;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import gui.JavaFXDisplayDriver;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenController implements Initializable {

    private final int DEFAULT_DISPLAY_HEIGHT = 500;
    private int boardSize = 35;
    private WireWorld wireWorld;
    private JavaFXDisplayDriver displayDriver;
    private int chosenColor = 0;
    private boolean isPaused = true;
    private long delay = 70;
    private Service<Void> backgroundThread;

    @FXML
    private Button stopButton;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button playButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button blackButton;
    @FXML
    private Button blueButton;
    @FXML
    private Button RedButton;
    @FXML
    private Button YellowButton;

    @FXML
    private void onBlack(ActionEvent event) {
        chosenColor = 0;
    }

    @FXML
    private void onBlue(ActionEvent event) {
        chosenColor = 1;
    }

    @FXML
    private void onRed(ActionEvent event) {
        chosenColor = 2;
    }

    @FXML
    private void onYellow(ActionEvent event) {
        chosenColor = 3;
    }

    @FXML
    private void onReset(ActionEvent event) {
        isPaused = true;
        createBoard();
    }

    @FXML
    private void onClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onClicked(MouseEvent event) {
        int i = (int) Math.floor(event.getY() * boardSize / DEFAULT_DISPLAY_HEIGHT);
        int j = (int) Math.floor(event.getX() * boardSize / DEFAULT_DISPLAY_HEIGHT);
        System.out.println("X: " + event.getX() + ", Y: " + event.getY());
        System.out.println("I: " + i + ", J: " + j);
        displayDriver.colorTile(i, j, chosenColor);
        wireWorld.getGridOfTiles().setCurrent(i, j, chosenColor);
    }

    @FXML
    private void onPlay(ActionEvent event) {
        isPaused = false;
        playButton.setDisable(true);
        backgroundThread.restart();
        playButton.setDisable(false);
    }

    @FXML
    private void onStop(ActionEvent event) {
        isPaused = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        wireWorld.calculateWireWorld();
                        return null;
                    }
                };
            }
        };

        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                displayDriver.displayGrid(wireWorld.getGridOfTiles());

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!isPaused) {
                    backgroundThread.restart();
                }
            }
        });
    }

    private double calcRecSize() {
        return (double) DEFAULT_DISPLAY_HEIGHT / boardSize;
    }

    public void createBoard() {
        wireWorld = new WireWorld(boardSize, boardSize);
        createDisplay();
    }

    private void createDisplay() {
        displayDriver = new JavaFXDisplayDriver(boardSize, calcRecSize(), wireWorld.getGridOfTiles());
        flowPane.getChildren().clear();
        flowPane.getChildren().add(displayDriver.getTilePane());
    }

    public void init(int boardSize, long delay) {
        this.boardSize = boardSize;
        this.delay = delay;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}


