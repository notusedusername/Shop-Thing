package shopthing.model;

import java.util.ArrayList;

public class Cart {

    private static ArrayList<Ware> cart = new ArrayList<>();

    public static ArrayList<Ware> getCart() {
        return cart;
    }

    public static void addToCart(Ware ware) {
        cart.add(ware);
    }

    public static void setCart(ArrayList<Ware> cart) {
        Cart.cart = cart;
    }
}
