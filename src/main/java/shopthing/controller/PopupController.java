package shopthing.controller;

import static shopthing.controller.util.PopupUtil.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class PopupController {

    @FXML
    Label messageLabel;


    public void initialize() {
        messageLabel.setText(getMessageText());
        //todo képek i/!
        setMessageText("");
        setMessageType("");
    }


    public void handleOK(ActionEvent actionEvent) {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}
