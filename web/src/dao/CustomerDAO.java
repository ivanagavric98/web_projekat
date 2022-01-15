package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeElement;
import javax.swing.text.AbstractDocument.BranchElement;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import javafx.css.CssMetaData;
import model.Customer;
import model.CustomerType;
import model.User;

public class CustomerDAO implements IDAO<Customer, String> {
    private String path;
    private ArrayList<Customer> customers;

    public CustomerDAO(String path) {
        super();
        this.path = path;
    }

    @Override
    public ArrayList<Customer> getAll() throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type token = new TypeToken<ArrayList<Customer>>() {
        }.getType();
        BufferedReader br = new BufferedReader(new FileReader("web/data/customers.json"));
        this.customers = gson.fromJson(br, token);
        return customers;
    }

    @Override
    public void create(Customer entity) throws JsonSyntaxException, IOException {
        ArrayList<Customer> customers = getAll();
        customers.add(entity);
        saveAll(customers);
    }

    @Override
    public ArrayList<Customer> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Customer getByID(String id) throws JsonSyntaxException, IOException {
        Customer wantedUser = null;
        ArrayList<Customer> users = (ArrayList<Customer>) getAll();
        if (users.size() != 0) {
            for (Customer user : users) {
                if (user.getUsername().equals(id)) {
                    wantedUser = user;
                    break;
                }
            }
        }
        return wantedUser;
    }

    @Override
    public void update(Customer entity) throws JsonSyntaxException, IOException {
        ArrayList<Customer> users = getAll();
        for (Customer user : users) {
            if (user.getUsername().equals(entity.getUsername())) {
                users.set(users.indexOf(user), entity);
                break;
            }
        }
        saveAll(users);
    }

    @Override
    public void delete(Customer entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(Customer entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveAll(ArrayList<Customer> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        String allEntities = new Gson().toJson(entities, new TypeToken<List<Customer>>() {
        }.getType());
        writer.println(allEntities);
        writer.close();

    }

    public List<Customer> userSortByUserPointAsc() throws JsonSyntaxException, IOException {
        ArrayList<Customer> users = getAll();
        Set<Customer> toSort = new HashSet<>();

        for (Customer object : users) {
            toSort.add(object);
        }

        List<Customer> resultList = toSort.stream()
                .sorted((e1, e2) -> Double.valueOf(e1.getPoints()).compareTo(Double.valueOf(e2.getPoints())))
                .collect(Collectors.toList());

        return resultList;
    }

    public List<Customer> userSortByUserPointsDesc() throws JsonSyntaxException, IOException {
        ArrayList<Customer> users = getAll();
        Set<Customer> toSort = new HashSet<>();

        for (Customer object : users) {
            toSort.add(object);
        }

        List<Customer> resultList = toSort.stream()
                .sorted((e1, e2) -> Double.valueOf(e1.getPoints()).compareTo(Double.valueOf(e2.getPoints())))
                .collect(Collectors.toList());
        Collections.reverse(resultList);
        return resultList;
    }

    public List<Customer> customerFiltrateByType(String type) throws JsonSyntaxException, IOException {
        ArrayList<Customer> customers = getAll();
        ArrayList<Customer> resultList = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getType().type.toString().toLowerCase().equals(type.toLowerCase())) {
                resultList.add(customer);
            }
        }
        return resultList;
    }

    public Customer updateUsersPoints(String customerName, double price, ArrayList<CustomerType> allTypes)
            throws JsonSyntaxException, IOException {
        Customer customer = getByID(customerName);
        Double points = price / 1000 * 133;
        Double userPoints = customer.getPoints();
        customer.setPoints(userPoints + points);
        CustomerType bronze = new CustomerType();
        CustomerType silver = new CustomerType();
        CustomerType golden = new CustomerType();

        for (CustomerType ct : allTypes) {
            if (ct.getType().toString().equals("BRONZE")) {
                bronze = ct;
            }
            if (ct.getType().toString().equals("SILVER")) {
                silver = ct;
            }
            if (ct.getType().toString().equals("GOLDEN")) {
                golden = ct;
            }
        }
        if (customer.getPoints() <= bronze.getRequiredPoints()) {
            customer.setType(bronze);
        }
        if (customer.getPoints() > bronze.getRequiredPoints() && customer.getPoints() <= golden.getRequiredPoints()) {
            customer.setType(silver);
        }
        if (customer.getPoints() > silver.getRequiredPoints()) {
            customer.setType(golden);
        }

        update(customer);
        return customer;
    }

    public Customer updateUsersPointsAferCancellation(String customerName, Double price)
            throws JsonSyntaxException, IOException {
        Customer customer = getByID(customerName);
        Double points = price / 1000 * 133 * 4;
        Double userPoints = customer.getPoints();
        customer.setPoints(userPoints - points);
        update(customer);
        return customer;
    }
}
