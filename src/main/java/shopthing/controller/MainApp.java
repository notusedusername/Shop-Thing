package shopthing.controller;

import javafx.application.Application;

import static hibernate.H2Util.runQuery;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import shopthing.controller.util.Popup;

import java.io.File;
import java.util.Optional;

import static shopthing.controller.util.ControllerUtil.*;


public class MainApp extends Application {

    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        File propsFile = new File(System.getProperty("user.home") + "/ShopThing/db.properties");
        if (!propsFile.exists()) {
            new Popup("Hiányzó " + System.getProperty("user.home") + "/ShopThing/db.properties fájl!", Alert.AlertType.ERROR);
            if (new File(System.getProperty("user.home") + "/ShopThing").mkdir()) {
                new Popup("A fájllal kapcsolatban segítség: https://github.com/notusedusername/Shop-Thing", Alert.AlertType.INFORMATION);
            } else {
                new Popup("A fájllal kapcsolatban segítség: https://github.com/notusedusername/Shop-Thing", Alert.AlertType.ERROR);
            }
        } else {
            setTableView(runQuery(null), new TableView<>());
            primaryStage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));

            Scene scene = new Scene(root);
            setStylesheets(scene);
            setFullscreen(stage, scene);
        }
    }

    /**
     * The hibernate() method is ignored in correctly deployed JavaFX application.
     * hibernate() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores hibernate().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
