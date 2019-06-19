package shopthing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static shopthing.controller.ControllerUtil.*;

public class MainMenuController implements Initializable {

    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void handleNewWare(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/NewWare.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        setStylesheets(scene);
        Stage stage = MainApp.primaryStage;
        setFullscreen(stage, scene);
    }

    public void handleShopping(ActionEvent actionEvent) {
    }

    public void handleSearch(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/SearchDatabase.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        setStylesheets(scene);
        Stage stage = MainApp.primaryStage;
        setFullscreen(stage, scene);
    }

    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

}
