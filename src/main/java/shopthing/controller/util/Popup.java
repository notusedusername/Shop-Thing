package shopthing.controller.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static shopthing.controller.util.ControllerUtil.setStylesheets;
import static shopthing.controller.util.PopupUtil.*;

public class Popup {

    public Popup(String message, String type) {
        setMessageText(message);
        setMessageType(type);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Popup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        setStylesheets(scene);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(scene.getWindow());
        stage.setScene(scene);
        stage.setTitle(getMessageType());
        stage.show();
    }
}
