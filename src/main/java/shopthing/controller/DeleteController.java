package shopthing.controller;

import static hibernate.H2Util.*;
import static shopthing.controller.util.ControllerUtil.setTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import shopthing.controller.util.Popup;
import shopthing.model.Ware;


public class DeleteController {
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
    @FXML
    Button deleteButton;
    @FXML
    CheckBox securePin;
    @FXML
    CheckBox barCodeLock;
    @FXML
    CheckBox nameLock;


    private Ware newKnownWare = null;

    public void initialize() {
        setTableView(runQuery(null), table);
        barCodeLock.setTooltip(new Tooltip("A vonalkód módosítását engedélyezi."));
        nameLock.setTooltip(new Tooltip("A név módosítását engedélyezi."));
        new Popup("Figyelem! Innen módosítani tudod az adatbázist.", Alert.AlertType.INFORMATION);
    }

    public void handleSelect(MouseEvent mouseEvent) {
        newKnownWare = table.getSelectionModel().getSelectedItem();
        if (newKnownWare != null) {
            barcode.setText(newKnownWare.getBarcode().toString());
            name.setText(newKnownWare.getName());
            price.setText(newKnownWare.getPrice().toString());
            onStorage.setText(newKnownWare.getOnStorage().toString());
        }
    }

    public void handleChangeValues(ActionEvent actionEvent) {
        if (newKnownWare != null) {
            if (new Popup("Biztosan módosítod az értékeket?", Alert.AlertType.CONFIRMATION).getResult()) {
                StringBuilder changeCommand = new StringBuilder();
                try {
                    if (barCodeLock.isSelected()) {
                        changeCommand.append("UPDATE Ware set name = \'")
                                .append(name.getText().toUpperCase())
                                .append("\' WHERE barcode = ")
                                .append(barcode.getText());
                        updateTable(changeCommand.toString());
                    } else if (nameLock.isSelected()) {
                        changeCommand.append("UPDATE Ware set barcode = ")
                                .append(barcode.getText())
                                .append(" WHERE name = \'")
                                .append(name.getText().toUpperCase())
                                .append("\'");
                        updateTable(changeCommand.toString());
                    }

                    changeCommand.delete(0, changeCommand.length())
                            .append("UPDATE Ware set price = ")
                            .append(price.getText())
                            .append("WHERE barcode = ")
                            .append(barcode.getText());
                    updateTable(changeCommand.toString());

                    changeCommand.delete(0, changeCommand.length())
                            .append("UPDATE Ware set onStorage = ")
                            .append(onStorage.getText())
                            .append("WHERE barcode = ")
                            .append(barcode.getText());
                    updateTable(changeCommand.toString());
                    setTableView(runQuery(null), table);
                } catch (Exception e) {
                    new Popup("A művelet nem sikerült, létező név/kód!", Alert.AlertType.WARNING);
                }


            }
        } else {
            new Popup("Hiányzó érték!", Alert.AlertType.WARNING);
        }
    }

    public void handleDeleteRecord(ActionEvent actionEvent) {
        if (newKnownWare != null) {
            if (new Popup("Biztosan törlöd? Nem visszavonható művelet!", Alert.AlertType.CONFIRMATION).getResult()) {
                StringBuilder changeCommand = new StringBuilder("DELETE FROM Ware WHERE barcode = ")
                        .append(barcode.getText());
                updateTable(changeCommand.toString());
                setTableView(runQuery(null), table);
            }
        } else {
            new Popup("Nem jelöltél ki semmit törlésre!", Alert.AlertType.WARNING);
        }
    }

    public void handleBack(ActionEvent actionEvent) {
        new SearchDatabaseController().handleBack(actionEvent);
    }

    public void handleSecurePin(MouseEvent mouseEvent) {
        if (securePin.isSelected()) {
            deleteButton.disableProperty().setValue(false);
        } else {
            deleteButton.disableProperty().setValue(true);

        }
    }

    public void handleBarCodeLock(MouseEvent mouseEvent) {
        barcode.disableProperty().setValue(!barcode.disableProperty().get());
        name.disableProperty().setValue(!name.disableProperty().get());
        nameLock.selectedProperty().setValue(!nameLock.isSelected());
    }

    public void handleNameLock(MouseEvent mouseEvent) {
        barcode.disableProperty().setValue(!barcode.disableProperty().get());
        name.disableProperty().setValue(!name.disableProperty().get());
        barCodeLock.selectedProperty().setValue(!barCodeLock.isSelected());
    }
}
