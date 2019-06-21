package shopthing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import shopthing.model.Cart;
import shopthing.model.Ware;
import java.util.ArrayList;

import static shopthing.controller.util.ControllerUtil.*;

public class PayShoppingController {
    @FXML
    Label totalCost;

    @FXML
    TableView<Ware> cart;

    @FXML
    public void initialize() {
        int cost = 0;
        for (Ware i : Cart.getCart()) {
            cost += i.getPrice() * i.getOnStorage();
        }
        totalCost.setText(cost + " Ft");
        setTableView(Cart.getCart(), cart);
    }

    public void handlecommitTransaction(ActionEvent actionEvent) {
        Cart.setCart(new ArrayList<>());
        new MainMenuController().handleShopping(actionEvent);
    }

    public void handleBackToShopping(ActionEvent actionEvent) {
        new MainMenuController().handleShopping(actionEvent);
    }
}
