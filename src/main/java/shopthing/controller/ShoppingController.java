package shopthing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shopthing.controller.util.Popup;
import shopthing.model.Ware;
import shopthing.model.Cart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static shopthing.controller.util.ControllerUtil.*;
import static hibernate.H2Util.runQuery;
import static hibernate.H2Util.updateTable;
import static shopthing.controller.util.ControllerUtil.*;

public class ShoppingController {

    @FXML
    Button deleteCartItem;
    @FXML
    TableView<Ware> table;
    @FXML
    TableView<Ware> cart;
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
        deleteCartItem.tooltipProperty().setValue(new Tooltip("A kosárból törli a kijelölt sort."));
        textFieldList.add(barcode);
        textFieldList.add(name);
        textFieldList.add(price);
        int cost = 0;
        for (Ware i : Cart.getCart()) {
            cost += i.getPrice() * i.getOnStorage();
        }
        totalCost.setText(Integer.toString(cost));
        setTableView(runQuery(null), table);
        setTableView(Cart.getCart(), cart);
        switchPayability();
    }

    public void handleSelection(MouseEvent mouseEvent) {
        selectedItem = new Ware(table.getSelectionModel().getSelectedItem());
        if (selectedItem != null) {
            barcode.setText(selectedItem.getBarcode().toString());
            name.setText(selectedItem.getName());
            price.setText(selectedItem.getPrice().toString());
            boughtPieces.setText("1");
            addToCart.disableProperty().setValue(false);
            deleteCartItem.disableProperty().setValue(true);
            boughtPieces.requestFocus();
        }

    }

    public void handleSelectionKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleSelection(null);
            boughtPieces.requestFocus();
        }
    }

    public void handleCartSelection(MouseEvent mouseEvent) {
        selectedItem = cart.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            barcode.setText(selectedItem.getBarcode().toString());
            name.setText(selectedItem.getName());
            price.setText(selectedItem.getPrice().toString());
            deleteCartItem.disableProperty().setValue(false);
        }
    }

    public void handleAddToCart(ActionEvent actionEvent) {
        if (takeFromStorage()) {
            try {
                selectedItem.setOnStorage(Integer.parseInt(boughtPieces.getText()));
                Cart.addToCart(selectedItem);
                setTableView(Cart.getCart(), cart);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    new Popup("Nincs kiválasztott termék!", Alert.AlertType.WARNING);
                } else if (e instanceof NumberFormatException) {
                    new Popup("Érvényes darabszámot adj meg!", Alert.AlertType.WARNING);
                }
            }
            addToCart.disableProperty().setValue(true);
            int cost = (Integer.parseInt(totalCost.getText()) + Integer.parseInt(price.getText()) * Integer.parseInt(boughtPieces.getText()));
            totalCost.setText(Integer.toString(cost));
            switchPayability();
            clearTexfields();
        } else {
            new Popup("Nincs raktáron " + boughtPieces.getText() + " a kívánt termékből!", Alert.AlertType.WARNING);
        }
        table.requestFocus();

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

    public void handleBack(ActionEvent actionEvent) {
        if (!Cart.getCart().isEmpty()) {
            if (new Popup("Biztosan kilépsz? Minden elvész a kosárból!", Alert.AlertType.CONFIRMATION).getResult()) {
                handleDeleteAllCartItem(actionEvent);
                Cart.setCart(new ArrayList<>());
                new SearchDatabaseController().handleBack(actionEvent);
            }
        } else {
            new SearchDatabaseController().handleBack(actionEvent);
        }

    }

    public void handleTyping(KeyEvent actionEvent) {
        if (search.getText().equals("")) {
            setTableView(runQuery(null), table);
        } else {
            StringBuilder searchParameter;
            try {
                searchParameter = new StringBuilder("FROM Ware WHERE barcode LIKE '%");
                searchParameter.append(Integer.parseInt(search.getText()))
                        .append("%' OR price = ")
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

    public void handleDeleteCartItem(ActionEvent actionEvent) {
        if (selectedItem != null) {
            if (new Popup("Biztosan kiveszed a kijelölt elemet a kosárból?", Alert.AlertType.CONFIRMATION).getResult()) {
                backToStorage(selectedItem);
                int newCost = Integer.parseInt(totalCost.getText()) - (selectedItem.getPrice() * selectedItem.getOnStorage());
                totalCost.setText(Integer.toString(newCost));
                Cart.getCart().remove(selectedItem);
                setTableView(Cart.getCart(), cart);
                deleteCartItem.disableProperty().setValue(true);
                switchPayability();
            }
        }
    }

    public void handleDeleteAllCartItem(ActionEvent actionEvent) {
        if (new Popup("Minden elemet törölni fogsz a kosárból. Folytatod?", Alert.AlertType.CONFIRMATION).getResult()) {
            try {
                if (!Cart.getCart().isEmpty()) {
                    Cart.getCart().stream().forEach(ware -> {
                        selectedItem = ware;
                        backToStorage(selectedItem);
                    });
                    Cart.setCart(new ArrayList<>());
                    totalCost.setText(Integer.toString(0));
                    setTableView(Cart.getCart(), cart);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void backToStorage(Ware selectedItem) {
        StringBuilder update = new StringBuilder("FROM Ware WHERE barcode = '");
        update.append(selectedItem.getBarcode())
                .append("'");
        int onStorage = runQuery(update.toString()).get(0).getOnStorage();
        update.delete(0, update.length());

        update.append("UPDATE Ware set onStorage = ");
        update.append(selectedItem.getOnStorage() + onStorage)
                .append("WHERE barcode = '")
                .append(selectedItem.getBarcode())
                .append("'");
        updateTable(update.toString());
        setTableView(runQuery(null), table);
    }

    private boolean takeFromStorage() {
        int amountToAdd = Integer.parseInt(boughtPieces.getText());
        StringBuilder update = new StringBuilder();
        update.append("FROM Ware WHERE barcode = '")
                .append(selectedItem.getBarcode())
                .append("'");
        int totalAmount = runQuery(update.toString()).get(0).getOnStorage();
        if (amountToAdd > 0 && totalAmount >= amountToAdd) {
            update.delete(0, update.length());
            update.append("UPDATE Ware set onStorage = ")
                    .append(totalAmount - amountToAdd)
                    .append("WHERE barcode ='")
                    .append(selectedItem.getBarcode())
                    .append("'");
            updateTable(update.toString());
            setTableView(runQuery(null), table);
            return true;
        } else {
            return false;
        }
    }

    private void clearTexfields() {
        for (TextField i : textFieldList) {
            i.setText("");
        }
    }

    private void switchPayability() {
        if (Integer.parseInt(totalCost.getText()) > 0) {
            endShopping.disableProperty().setValue(false);
        } else {
            endShopping.disableProperty().setValue(true);
        }
    }

    public void handleAddToCartKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleAddToCart(null);
        }
    }

    public void handleKeyBoard(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            handleBack(null);
        }
    }
}
