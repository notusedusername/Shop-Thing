package shopthing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import shopthing.controller.util.Popup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class H2SettingsController {
    public Button browse;
    public TextField path;
    public TextField username;
    public PasswordField password;

    private File propsFile;

    @FXML
    public void initialize() {
        this.propsFile = MainApp.propsFile;
    }

    public void handleBrowsing(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        File dbFile = fileChooser.showOpenDialog(MainApp.primaryStage);
        if (dbFile != null) {

            path.setText(removeDBExtension(dbFile.getPath()));
        } else {
            new Popup("Nem választottál ki semmit!", Alert.AlertType.WARNING);
        }
    }

    private String removeDBExtension(String pathWithExtension) {
        StringBuilder stringBuilder = new StringBuilder(pathWithExtension);
        return stringBuilder.delete(stringBuilder.length() - 6, stringBuilder.length()).toString();
    }

    public void handleSaveSettings(ActionEvent actionEvent) {
        if (!path.getText().equals("") &&
                !username.getText().equals("") &&
                !password.getText().equals("")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hibernate.connection.url=jdbc:h2:")
                    .append(path.getText())
                    .append("\n")
                    .append("hibernate.connection.username=")
                    .append(username.getText())
                    .append("\n")
                    .append("hibernate.connection.password=")
                    .append(password.getText());
            writeStringToFile(stringBuilder.toString(), propsFile);
        } else {
            new Popup("Tölts ki minden mezőt!", Alert.AlertType.WARNING);
        }
        handleBack(actionEvent);
    }

    public void handleBack(ActionEvent actionEvent) {
        try {
            new MainApp().start(MainApp.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeStringToFile(String string, File writableFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(writableFile));
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
