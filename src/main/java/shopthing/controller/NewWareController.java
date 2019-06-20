package shopthing.controller;

import static hibernate.H2Util.*;
import static shopthing.controller.ControllerUtil.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import shopthing.model.Ware;


public class NewWareController {

    @FXML
    TextArea command;

    @FXML
    TableView<Ware> table;

    public void handleBack(ActionEvent actionEvent) {
        new SearchDatabaseController().handleBack(actionEvent);
    }

    public void handleDirectcommand(ActionEvent actionEvent) {
        String directCommand = command.getText();
        ObservableList<Ware> wares = selectRecords(directCommand);

        setTableView(wares, table);
    }
}
