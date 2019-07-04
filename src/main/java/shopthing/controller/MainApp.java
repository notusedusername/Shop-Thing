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
import java.io.IOException;

import static shopthing.controller.util.ControllerUtil.*;


public class MainApp extends Application {

    public static Stage primaryStage;
    public static File propsFile;

    @Override
    public void start(Stage stage) throws Exception {
        File propsFile = new File(System.getProperty("user.home") + "/ShopThing/db.properties");
        primaryStage = stage;
        MainApp.propsFile = propsFile;

        if (!propsFile.exists()) {
            configDatabase();
        } else {
            setTableView(runQuery(null), new TableView<>());

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));

            Scene scene = new Scene(root);
            setStylesheets(scene);
            setFullscreen(stage, scene);

        }
    }


    private void configDatabase() throws IOException {
        if (propsFile.createNewFile()) {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/H2Settings.fxml"));
            Scene scene = new Scene(root);
            setStylesheets(scene);
            setFullscreen(primaryStage, scene);

        } else {
            new Popup("Hiba új db.properties fájl létrehozása közben!", Alert.AlertType.ERROR);
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
