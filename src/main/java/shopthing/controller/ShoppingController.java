package shopthing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shopthing.controller.util.Popup;
import shopthing.model.Ware;
import shopthing.model.Cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hibernate.H2Util.runQuery;
import static shopthing.controller.util.ControllerUtil.*;

public class ShoppingController {

    @FXML
    TableView<Ware> table;
    @FXML
    TextField search;
    @FXML
    TextField barcode;
    @FXML
    TextField name;
    @FXML
    TextField price;
    @FXML
    TextField boughtPieces;
    @FXML
    Button endShopping;
    @FXML
    Button addToCart;
    @FXML
    Label totalCost;

    private Ware selectedItem;
    private List<TextField> textFieldList = new ArrayList<>();

    @FXML
    public void initialize() {
        textFieldList.add(barcode);
        textFieldList.add(name);
        textFieldList.add(price);
        setTableView(runQuery(null), table);
    }

    public void handleSelection(MouseEvent mouseEvent) {
        selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            barcode.setText(selectedItem.getBarcode().toString());
            name.setText(selectedItem.getName());
            price.setText(selectedItem.getPrice().toString());
            boughtPieces.setText("1");
            addToCart.disableProperty().setValue(false);
        }

    }

    public void handleAddToCart(ActionEvent actionEvent) {
        try {
            Cart.addToCart(selectedItem);
        } catch (NullPointerException e) {
            new Popup("Nincs kiválasztott termék!", "warning");
        }
        addToCart.disableProperty().setValue(true);
        if (Integer.parseInt(totalCost.getText()) > 0) {
            endShopping.disableProperty().setValue(false);
        }
        int cost = (Integer.parseInt(totalCost.getText()) + Integer.parseInt(price.getText()) * Integer.parseInt(boughtPieces.getText()));
        totalCost.setText(Integer.toString(cost));
        for (TextField i : textFieldList) {
            i.setText("");
        }
    }

    public void handlePaying(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/PayShopping.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        setStylesheets(scene);
        Stage stage = MainApp.primaryStage;
        setFullscreen(stage, scene);
    }

    public void handleBackToMain(ActionEvent actionEvent) {
        new SearchDatabaseController().handleBack(actionEvent);
    }

    public void handleTyping(KeyEvent actionEvent) {
        if (search.getText().equals("")) {
            setTableView(runQuery(null), table);
        } else {
            StringBuilder searchParameter;
            try {
                searchParameter = new StringBuilder("FROM Ware WHERE barcode =");
                searchParameter.append(Integer.parseInt(search.getText()))
                        .append(" OR price = ")
                        .append(Integer.parseInt(search.getText()));
                setTableView(runQuery(searchParameter.toString()), table);
            } catch (Exception e) {
                if (e instanceof NumberFormatException) {
                    searchParameter = new StringBuilder("FROM Ware WHERE name LIKE \'%")
                            .append(search.getText().toUpperCase())
                            .append("%\'");
                    setTableView(runQuery(searchParameter.toString()), table);
                }
            }

        }
    }
}
