package shopthing.controller;

import static hibernate.H2Util.*;
import static shopthing.controller.util.ControllerUtil.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import shopthing.controller.util.Popup;
import shopthing.model.Ware;

import javax.persistence.PersistenceException;


public class NewWareController {

    @FXML
    TableView<Ware> table;

    @FXML
    TextField barcode;
    @FXML
    TextField name;
    @FXML
    TextField price;
    @FXML
    TextField newAmount;


    public void initialize() {
        setTableView(runQuery(null), table);
    }

    public void handleBack(ActionEvent actionEvent) {
        new SearchDatabaseController().handleBack(actionEvent);
    }

    public void handleAdding(ActionEvent actionEvent) {
        if (newAmount.getText().equals("")) {
            amountNotSet();
        } else if (barcode.getText().equals("") || name.getText().equals("") || price.getText().equals("")) {
            new Popup("Hiányzó mező!", "warning");
        } else {
            insertProduct();
            setTableView(runQuery(null), table);
        }
    }


    public void handleSelection(MouseEvent mouseEvent) {
        Ware newKnownWare = table.getSelectionModel().getSelectedItem();
        if (newKnownWare != null) {
            barcode.setText(newKnownWare.getBarcode().toString());
            name.setText(newKnownWare.getName());
            price.setText(newKnownWare.getPrice().toString());
            newAmount.setText("");
        }
    }

    private void amountNotSet() {
        new Popup("Nem adtad meg a beszerzett mennyiséget!", "warning");
    }

    private void insertProduct() {
        Ware unknownProduct = null;
        try {
            unknownProduct = new Ware(Integer.parseInt(barcode.getText()),
                    name.getText().toUpperCase(), Integer.parseInt(price.getText()), Integer.parseInt(newAmount.getText()));
        } catch (NumberFormatException e) {
            new Popup("Valamelyik érték érvénytelen!", "warning");
            return;
        }
        try {
            persist(unknownProduct);
        } catch (PersistenceException e) {
            updateKnownProduct();
        }

    }

    private void updateKnownProduct() {
        StringBuilder previousValueQuery = new StringBuilder("FROM Ware WHERE barcode = ")
                .append(barcode.getText())
                .append(" AND name = \'")
                .append(name.getText().toUpperCase())
                .append("\' AND price = ")
                .append(price.getText());

        Integer previousStorage = 0;
        try {
            previousStorage = runQuery(previousValueQuery.toString()).get(0).getOnStorage();
        } catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException) {
                new Popup("Raktáron lévő terméket akarsz megadni más adatokkal!", "warning");
            }
            return;
        }

        StringBuilder updateQuery = new StringBuilder("UPDATE Ware set onStorage = ")
                .append(previousStorage + Integer.parseInt(newAmount.getText()))
                .append("WHERE barcode = ")
                .append(barcode.getText());
        updateTable(updateQuery.toString());
    }


}
