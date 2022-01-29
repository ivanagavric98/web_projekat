package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.google.gson.reflect.TypeToken;

import org.eclipse.jetty.util.UrlEncoded;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import model.CustomerType;

public class CustomerTypeDAO implements IDAO<CustomerType, String> {

    private String path;
    private ArrayList<CustomerType> types;

    public CustomerTypeDAO(String path) {
        super();
        this.path = path;
    }

    @Override
    public ArrayList<CustomerType> getAll() throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type token = new TypeToken<ArrayList<CustomerType>>() {
        }.getType();
        BufferedReader br = new BufferedReader(new FileReader("data/customerTypes.json"));
        this.types = gson.fromJson(br, token);
        return types;
    }

    @Override
    public CustomerType getByID(String street) throws JsonSyntaxException, IOException {
        /*
         * Address wantedAddress = null;
         * ArrayList<Address> addresses = (ArrayList<Address>) getAll();
         * if(addresses.size()!=0)
         * {
         * for(Address address : addresses) {
         * if(address.get.equals(id)) {
         * wantedRestaurant = restaurant;
         * break;
         * }
         * }
         * }
         * return wantedRestaurant;
         */return null;
    }

    @Override
    public void create(CustomerType entity) throws JsonSyntaxException, IOException {
        ArrayList<CustomerType> types = getAll();
        types.add(entity);
        saveAll(types);
    }

    @Override
    public void update(CustomerType entity) throws JsonSyntaxException, IOException {
        ArrayList<CustomerType> types = getAll();
        for (CustomerType type : types) {
            if (type.getType().equals(entity.getType())) {
                types.set(types.indexOf(type), entity);
                break;
            }
        }
        saveAll(types);
    }

    @Override
    public void delete(CustomerType entity) throws JsonSyntaxException, IOException {
        return;
    }

    @Override
    public void save(CustomerType entity) throws JsonSyntaxException, IOException {
        ArrayList<CustomerType> types = getAll();
        types.add(entity);
        saveAll(types);

    }

    @Override
    public void saveAll(ArrayList<CustomerType> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        String allEntities = new Gson().toJson(entities, new TypeToken<List<CustomerType>>() {
        }.getType());
        writer.println(allEntities);
        writer.close();

    }

    @Override
    public ArrayList<CustomerType> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public CustomerType getType(String entity) throws JsonSyntaxException, IOException {
        ArrayList<CustomerType> types = getAll();
        CustomerType result = new CustomerType();
        for (CustomerType ct : types) {
            if (ct.getType().toString().equals(entity)) {
                result.discount = ct.discount;
                result.requiredPoints = ct.requiredPoints;
                result.type = ct.type;
            }
        }
        return result;
    }
}
