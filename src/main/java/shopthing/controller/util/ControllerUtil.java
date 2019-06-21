package shopthing.controller.util;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import shopthing.model.Ware;

public class ControllerUtil {

    public static void setFullscreen(Stage stage, Scene scene) {
        stage.setTitle("ShopThing");
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.show();
    }

    public static void setStylesheets(Scene scene) {
        scene.getStylesheets().add("/styles/Styles.css");
    }

    public static void setTableView(ObservableList<Ware> listOfItems, TableView<Ware> table) {
        table.setItems(listOfItems);

        TableColumn<Ware, Integer> productBarCode = new TableColumn<Ware, Integer>("Vonalkód");
        productBarCode.setCellValueFactory(new PropertyValueFactory("barcode"));

        TableColumn<Ware, String> productName = new TableColumn<Ware, String>("Név");
        productName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Ware, Integer> productPrice = new TableColumn<Ware, Integer>("Ár");
        productPrice.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn<Ware, Integer> productOnStorage = new TableColumn<Ware, Integer>("Darabszám");
        productOnStorage.setCellValueFactory(new PropertyValueFactory("onStorage"));

        table.getColumns().setAll(productBarCode, productName, productPrice, productOnStorage);

    }


}
