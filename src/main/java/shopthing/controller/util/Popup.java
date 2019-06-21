package shopthing.controller.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import shopthing.controller.MainApp;

import java.util.Optional;


public class Popup {

    private boolean confirmationResult = false;

    public Popup(String message, Alert.AlertType type) {

        Alert alert = new Alert(type);
        alert.setTitle("ShopThing");

        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(MainApp.primaryStage);


        if (type == Alert.AlertType.CONFIRMATION) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                confirmationResult = true;
            } else {
                confirmationResult = false;
            }
        } else {
            alert.showAndWait();
        }
    }

    public boolean getResult() {
        return confirmationResult;
    }

}
