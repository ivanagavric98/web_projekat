package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import model.Address;
import model.ShoppingCart;

public class ShoppingCartDAO implements IDAO<ShoppingCart, String> {

    private String path;
    private ArrayList<ShoppingCart> shoppingCarts;

    public ShoppingCartDAO(String path) {
        super();
        this.path = path;
        try {
            getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ShoppingCart> getAll() throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type token = new TypeToken<ArrayList<ShoppingCart>>() {
        }.getType();
        BufferedReader br = new BufferedReader(new FileReader("data/shoppingCarts.json"));
        this.shoppingCarts = gson.fromJson(br, token);
        return shoppingCarts;
    }

    @Override
    public ShoppingCart getByID(String id) throws JsonSyntaxException, IOException {
        ShoppingCart wantedASCart = null;
        ArrayList<ShoppingCart> shoppingCarts = (ArrayList<ShoppingCart>) getAll();
        if (shoppingCarts.size() != 0) {
            for (ShoppingCart shoppingCart : shoppingCarts) {
                if (shoppingCart.getId() == Integer.parseInt(id)) {
                    wantedASCart = shoppingCart;
                    break;
                }
            }
        }
        return wantedASCart;
    }

    @Override
    public void create(ShoppingCart entity) throws JsonSyntaxException, IOException {
        ArrayList<ShoppingCart> shoppingCarts = getAll();
       
        if(shoppingCarts == null) {
        	shoppingCarts = new ArrayList<ShoppingCart>();
        }
        shoppingCarts.add(entity);
        saveAll(shoppingCarts);
    }

    @Override
    public void update(ShoppingCart entity) throws JsonSyntaxException, IOException {
        ArrayList<ShoppingCart> articles = getAll();
        for (ShoppingCart res : articles) {
            if (res.getId() == entity.getId()) {
                articles.set(articles.indexOf(res), entity);
                break;
            }
        }
        saveAll(articles);
    }

    @Override
    public void delete(ShoppingCart entity) throws JsonSyntaxException, IOException {
        return;
    }

    @Override
    public void save(ShoppingCart entity) throws JsonSyntaxException, IOException {
        ArrayList<ShoppingCart> shoppingCarts = getAll();
        shoppingCarts.add(entity);
        saveAll(shoppingCarts);

    }

    @Override
    public void saveAll(ArrayList<ShoppingCart> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        String allEntities = new Gson().toJson(entities, new TypeToken<List<Address>>() {
        }.getType());
        writer.println(allEntities);
        writer.close();

    }

    @Override
    public ArrayList<ShoppingCart> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }
}
