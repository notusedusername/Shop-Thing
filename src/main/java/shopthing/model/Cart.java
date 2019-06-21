package shopthing.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Cart {

    private static ArrayList<Ware> cart = new ArrayList<>();

    public static ObservableList<Ware> getCart() {
        return FXCollections.observableList(cart);
    }

    public static void addToCart(Ware ware) {
        cart.add(ware);
    }

    public static void setCart(ArrayList<Ware> cart) {
        Cart.cart = cart;
    }
}
