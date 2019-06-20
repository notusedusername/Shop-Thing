package shopthing.controller;

import static hibernate.H2Util.*;
import static shopthing.controller.util.ControllerUtil.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shopthing.model.Ware;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchDatabaseController {
    @FXML
    VBox list;

    @FXML
    TableView<Ware> table;

    @FXML
    TextField barcode;
    @FXML
    TextField name;
    @FXML
    TextField price;
    @FXML
    TextField onStorage;

    private List<TextField> textFieldList = new ArrayList<>();

    @FXML
    Button searchButton;
    @FXML
    Button clearButton;

    @FXML
    public void initialize() {
        textFieldList.add(barcode);
        textFieldList.add(name);
        textFieldList.add(price);
        textFieldList.add(onStorage);
        handleListing(new ActionEvent());
    }

    public void handleSearch(ActionEvent actionEvent) {
        ArrayList<TextField> searchConstraints = new ArrayList<>();
        for (TextField i : textFieldList) {
            if (!i.getText().equals("")) {
                searchConstraints.add(i);
            }
        }
        if (searchConstraints.isEmpty()) {
            handleListing(actionEvent);
        } else {
            setTableView(searchWithParameters(searchConstraints), table);
        }
    }

    public void handleFieldClear(ActionEvent actionEvent) {
        for (TextField i : textFieldList) {
            i.setText("");
        }
    }

    public void handleListing(ActionEvent actionEvent) {
        list.getChildren().removeAll();
        ObservableList<Ware> ListOfAllRecord = runQuery(null);
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

    protected ObservableList<Ware> searchWithParameters(ArrayList<TextField> searchConstraints) {
        StringBuilder queryCommand = new StringBuilder("FROM Ware WHERE ");
        for (TextField i : searchConstraints) {
            queryCommand.append(i.getId()).append(" = ");
            if (i.getId().equals("name")) {
                queryCommand.append("\'").append(i.getText().toUpperCase()).append("\'").append(" AND ");
            } else {
                queryCommand.append(i.getText()).append(" AND ");
            }
        }
        queryCommand.delete(queryCommand.length() - 5, queryCommand.length())
                .append("ORDER BY name ASC");
        return runQuery(queryCommand.toString());
    }
}
