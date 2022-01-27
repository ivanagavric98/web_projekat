package service;

import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.JsonSyntaxException;
import dao.ShoppingCartDAO;
import model.ShoppingCart;
import model.ShoppingCartItem;

public class ShoppingCartService {
    private ShoppingCartDAO shoppingCartDAO;

    public ShoppingCartService(ShoppingCartDAO shoppingCartDAO) {
        this.shoppingCartDAO = shoppingCartDAO;
    }

    public Boolean addShoppingCart(ShoppingCart shoppingCart) throws JsonSyntaxException, IOException {
        ArrayList<ShoppingCart> sc = getAllCarts();
        Boolean result = false;
        if (sc == null) {
            shoppingCartDAO.create(shoppingCart);
            result = true;
        } else {
            for (ShoppingCart u : sc) {
                if (u.id == shoppingCart.getId()) {
                    return result = false;
                }
            }
            shoppingCartDAO.create(shoppingCart);
            result = true;
        }

        return result;
    }

    public ArrayList<ShoppingCart> getAllCarts() throws JsonSyntaxException, IOException {
        return shoppingCartDAO.getAll();
    }

    public ShoppingCart addArticleToShoppingCart(Double price, String shoppingCartId, ShoppingCartItem shoppingCartItem)
            throws JsonSyntaxException, IOException {
        ShoppingCart shoppingCart = shoppingCartDAO.getByID(shoppingCartId);
        ArrayList<ShoppingCartItem> items = new ArrayList<>();

        if (shoppingCart.getItems() != null) {
            for (ShoppingCartItem sci : shoppingCart.getItems()) {
                items.add(sci);
            }
        }
        shoppingCart.setItems(items);

        Double priceToUpdate = shoppingCart.getPrice() + price;
        shoppingCart.setPrice(priceToUpdate);
        shoppingCartDAO.update(shoppingCart);

        return shoppingCart;
    }

    public ShoppingCart getById(String params) throws JsonSyntaxException, IOException {
        return shoppingCartDAO.getByID(params);
    }

    public ShoppingCart deteleArticlesFromCart(Double priceForArticle, String shoppingCartId,
            ShoppingCartItem shoppingCartItem) throws JsonSyntaxException, IOException {
        ShoppingCart shoppingCart = shoppingCartDAO.getByID(shoppingCartId);

        if (shoppingCart.getItems().size() == 0) {
            return shoppingCart;
        } else {
            ArrayList<ShoppingCartItem> items = shoppingCart.getItems();
            ArrayList<ShoppingCartItem> itemsToRemove = new ArrayList<>();

            for (ShoppingCartItem shi : items) {
                if (shi.getArticleName().equals(shoppingCartItem.articleName)) {
                    itemsToRemove.add(shi);
                }
            }

            items.removeAll(itemsToRemove);
            shoppingCart.setItems(items);

            Double priceToUpdate = shoppingCart.getPrice() - priceForArticle;
            shoppingCart.setPrice(priceToUpdate);
            shoppingCartDAO.update(shoppingCart);

            return shoppingCart;
        }

    }

    public ShoppingCart updateArticleFromCart(Double priceForArticle, Double priceToSub, String shoppingCartId,
            ShoppingCartItem shoppingCartItem) throws JsonSyntaxException, IOException {
        ShoppingCart shoppingCart = shoppingCartDAO.getByID(shoppingCartId);

        if (shoppingCart.getItems().size() == 0) {
            return shoppingCart;
        } else {
            ArrayList<ShoppingCartItem> items = shoppingCart.getItems();
            ArrayList<ShoppingCartItem> itemsToRemove = new ArrayList<>();
            ;

            for (ShoppingCartItem shi : items) {
                if (shi.getArticleName().equals(shoppingCartItem.articleName)) {
                    itemsToRemove.add(shi);
                }
            }
            items.removeAll(itemsToRemove);
            Double price = shoppingCart.getPrice() - priceToSub + priceForArticle;
            items.add(shoppingCartItem);

            shoppingCart.setItems(items);

            shoppingCart.setPrice(price);
            shoppingCartDAO.update(shoppingCart);

            return shoppingCart;
        }
    }
}
