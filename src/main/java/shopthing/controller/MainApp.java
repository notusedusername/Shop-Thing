package shopthing.controller;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static shopthing.controller.ControllerUtil.*;


public class MainApp extends Application {

    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));

        Scene scene = new Scene(root);
        setStylesheets(scene);
        setFullscreen(stage, scene);
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
