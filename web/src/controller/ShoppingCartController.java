package controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.ShoppingCart;
import service.ShoppingCartService;

public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;
    private static Gson gson = new Gson();

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public Boolean addShoppingCart(ShoppingCart shoppingCart, String username) throws JsonSyntaxException, IOException {
        return shoppingCartService.addShoppingCart(shoppingCart, username);
    }

    public ShoppingCart getById(String params) throws JsonSyntaxException, IOException {
        return shoppingCartService.getById(params);
    }

}
