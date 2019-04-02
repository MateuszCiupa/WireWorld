package gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private Button playButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField sizeField;
    @FXML
    private TextField delayField;

    private GenController genController;
    private FXMLLoader loader;
    private VBox root;

    @FXML
    void onCancel(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onPlay(ActionEvent event) {
        genController = loader.getController();

        if(!sizeField.getText().trim().equals("") && !delayField.getText().trim().equals("")) {
            genController.init(Integer.parseInt(sizeField.getText()), Long.parseLong(delayField.getText()));
        } else if(sizeField.getText().trim().equals("") && !delayField.getText().trim().equals("")) {
            genController.setDelay(Long.parseLong(delayField.getText()));
        } else if(!sizeField.getText().trim().equals("") && delayField.getText().trim().equals("")) {
            genController.setBoardSize(Integer.parseInt(sizeField.getText()));
        }

        genController.createBoard();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loader = new FXMLLoader(getClass().getResource("../fxml/WireWorldGen.fxml"));
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
