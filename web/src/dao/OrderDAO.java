package dao;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

import model.Customer;
import model.Order;
import model.OrderStatus;

public class OrderDAO implements IDAO<Order, String> {
    private String path;
    private ArrayList<Order> orders;

    public OrderDAO(String path) {
        super();
        this.path = path;
    }

    @Override
    public ArrayList<Order> getAll() throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type token = new TypeToken<ArrayList<Order>>() {
        }.getType();
        BufferedReader br = new BufferedReader(new FileReader("data/orders.json"));
        this.orders = gson.fromJson(br, token);
        return orders;
    }

    @Override
    public void create(Order entity) throws JsonSyntaxException, IOException {
        ArrayList<Order> customers = getAll();
        if (customers == null) {
            customers = new ArrayList<>();
        }
        customers.add(entity);
        saveAll(customers);
    }

    @Override
    public ArrayList<Order> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order getByID(String id) throws JsonSyntaxException, IOException {
        Order wantedOrder = null;
        ArrayList<Order> orders = (ArrayList<Order>) getAll();
        if (orders.size() != 0) {
            for (Order order : orders) {
                if (order.getID().equals(id)) {
                    wantedOrder = order;
                    break;
                }
            }
        }
        return wantedOrder;
    }

    @Override
    public void update(Order entity) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAll();
        for (Order order : orders) {
            if (order.getID().equals(entity.getID())) {
                orders.set(orders.indexOf(order), entity);
                break;
            }
        }
        saveAll(orders);

    }

    @Override
    public void delete(Order entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(Order entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveAll(ArrayList<Order> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        String allEntities = new Gson().toJson(entities, new TypeToken<List<Customer>>() {
        }.getType());
        writer.println(allEntities);
        writer.close();

    }

    public Order changeStatusToInPreparation(String params) throws JsonSyntaxException, IOException {
        Order order = getByID(params);
        order.setOrderStatus(OrderStatus.IN_PREPARATION);
        update(order);
        return order;
    }

    public Order changeStatusToWaitingForSupplier(String params)
            throws JsonSyntaxException, IOException {
        Order order = getByID(params);
        order.setOrderStatus(OrderStatus.WAITING_FOR_SUPPLIER);
        update(order);
        return order;
    }

    public Order changeStatusToInTransport(String params) throws JsonSyntaxException, IOException {
        Order order = getByID(params);
        order.setOrderStatus(OrderStatus.IN_TRANSPORT);
        update(order);
        return order;
    }

    public Order changeStatusToDelivered(String params) throws JsonSyntaxException, IOException {
        Order order = getByID(params);
        order.setOrderStatus(OrderStatus.DELIVERED);
        update(order);
        return order;
    }

    public Order changeStatusToCanceled(String params) throws JsonSyntaxException, IOException {
        Order order = getByID(params);
        order.setOrderStatus(OrderStatus.CANCELED);
        update(order);
        return order;
    }

    public ArrayList<Order> getOrderWithStatusInPreparation() throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAll();
        ArrayList<Order> resultOrder = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.IN_PREPARATION)) {
                resultOrder.add(order);
            }
        }
        return resultOrder;
    }

    public ArrayList<Order> getOrderWithStatusWaitingForSupplier() throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAll();
        ArrayList<Order> resultOrder = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.WAITING_FOR_SUPPLIER)) {
                resultOrder.add(order);
            }
        }
        return resultOrder;
    }

}
