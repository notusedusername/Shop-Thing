package shopthing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import shopthing.model.Cart;
import shopthing.model.Ware;

import java.util.ArrayList;

public class PayShoppingController {
    @FXML
    Label totalCost;

    @FXML
    public void initialize() {
        int cost = 0;
        for (Ware i : Cart.getCart()) {
            cost += i.getPrice() * i.getOnStorage();
        }
        totalCost.setText(cost + " Ft");
    }

    public void handlecommitTransaction(ActionEvent actionEvent) {
        Cart.setCart(new ArrayList<>());
        new MainMenuController().handleShopping(actionEvent);
    }

    public void handleBackToShopping(ActionEvent actionEvent) {
        new MainMenuController().handleShopping(actionEvent);
    }
}
