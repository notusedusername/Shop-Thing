package shopthing.controller;

import static hibernate.H2Util.*;
import static shopthing.controller.ControllerUtil.*;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shopthing.model.Ware;

import java.io.IOException;
import java.util.List;

public class SearchDatabaseController {
    @FXML
    VBox list;

    @FXML
    TableView<Ware> table;

    public void handleListing(ActionEvent actionEvent) {
        list.getChildren().removeAll();
        ObservableList<Ware> ListOfAllRecord = selectAllRecord(null);
        setTableView(ListOfAllRecord, table);


    }


    public void handleBack(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        setStylesheets(scene);
        Stage stage = MainApp.primaryStage;
        setFullscreen(stage, scene);
    }
}
