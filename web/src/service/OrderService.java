package service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.JsonSyntaxException;

import dao.OrderDAO;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.input.DragEvent;
import javaxt.utils.string;
import model.Order;
import model.OrderStatus;
import model.Restaurant;
import model.ShoppingCart;
import model.ShoppingCartItem;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order add(ShoppingCart shoppingCart) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        String uniqString = UUID.randomUUID().toString().substring(0, 10);

        ArrayList<String> itemsToAdd = new ArrayList<>();
        Order order = new Order();
        for (ShoppingCartItem sci : shoppingCart.items) {
            itemsToAdd.add(sci.articleName);
        }
        order.articles = itemsToAdd;
        order.ID = uniqString;
        String date = LocalDate.now().toString();
        order.dateAndTime = date;
        order.customer = shoppingCart.getCustomer();
        order.price = shoppingCart.getPrice();
        order.restaurant = shoppingCart.getRestaurantName();
        order.orderStatus = OrderStatus.PROCESSING;

        if (orders == null) {
            orderDAO.create(order);
        } else {
            for (Order u : orders) {
                if (u.getID().equals(order.ID)) {
                    return null;
                }
            }
            orderDAO.create(order);
        }
        return order;
    }

    public ArrayList<Order> getAllOrders() throws JsonSyntaxException, IOException {
        return orderDAO.getAll();
    }

    public Order changeStatusToInPreparation(String params) throws JsonSyntaxException, IOException {
        return orderDAO.changeStatusToInPreparation(params);
    }

    public Order changeStatusToWaitingForSupplier(String params) throws JsonSyntaxException, IOException {
        return orderDAO.changeStatusToWaitingForSupplier(params);
    }

    public Order changeStatusToInTransport(String params) throws JsonSyntaxException, IOException {
        return orderDAO.changeStatusToInTransport(params);
    }

    public Order changeStatusToDelivered(String params) throws JsonSyntaxException, IOException {
        return orderDAO.changeStatusToDelivered(params);
    }

    public Order changeStatusToCanceled(String params) throws JsonSyntaxException, IOException {
        return orderDAO.changeStatusToCanceled(params);
    }

    public ArrayList<Order> getOrderWithStatusInPreparation() throws JsonSyntaxException, IOException {
        return orderDAO.getOrderWithStatusInPreparation();
    }

    public ArrayList<Order> getOrderWithStatusWaitingForSupplier() throws JsonSyntaxException, IOException {
        return orderDAO.getOrderWithStatusWaitingForSupplier();
    }

    public Order getById(String params) throws JsonSyntaxException, IOException {
        return orderDAO.getByID(params);
    }

    public void update(Order params) throws JsonSyntaxException, IOException {
        orderDAO.update(params);
    }

    public ArrayList<Order> usersOrderByRestaurantName(String name) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = orderDAO.getAll();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getRestaurant().toLowerCase().equals(name.trim().toLowerCase())) {
                resultList.add(o);
            }
        }
        return resultList;
    }

    public ArrayList<Order> getOrderByPriceRange(Double from, Double to) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = orderDAO.getAll();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getPrice() >= from && o.getPrice() <= to) {
                resultList.add(o);
            }
        }
        return resultList;
    }

    public ArrayList<Order> getOrderByDateRange(String from, String to)
            throws JsonSyntaxException, IOException, ParseException {
        ArrayList<Order> orders = orderDAO.getAll();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
            Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(o.getDateAndTime());
            if (dateFrom.compareTo(orderDate) <= 0 && dateTo.compareTo(orderDate) >= 0) {
                resultList.add(o);
            }

        }
        return resultList;
    }

    public List<Order> sortOrderByRestaurantName() throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        Set<Order> toSort = new HashSet<>();

        for (Order object : orders) {
            toSort.add(object);
        }

        List<Order> resultList = toSort.stream().sorted((e1, e2) -> e1.getRestaurant().compareTo(e2.getRestaurant()))
                .collect(Collectors.toList());

        return resultList;
    }

    public List<Order> sortOrderByRestaurantNameDesc() throws JsonSyntaxException, IOException {
        List<Order> orders = sortOrderByRestaurantName();
        Collections.reverse(orders);
        return orders;

    }

    public List<Order> sortOrderByPriceAsc() throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        Set<Order> toSort = new HashSet<>();

        for (Order object : orders) {
            toSort.add(object);
        }

        List<Order> resultList = toSort.stream().sorted((e1, e2) -> e1.getPrice().compareTo(e2.getPrice()))
                .collect(Collectors.toList());

        return resultList;
    }

    public List<Order> sortOrderByPriceDesc() throws JsonSyntaxException, IOException {
        List<Order> orders = sortOrderByPriceAsc();
        Collections.reverse(orders);
        return orders;
    }

    public List<Order> sortOrderByDateAsc() throws JsonSyntaxException, IOException, ParseException {
        ArrayList<Order> orders = getAllOrders();
        Set<Order> toSort = new HashSet<>();
        Map<String, Date> newMap = new HashMap<>();

        for (Order o : orders) {
            newMap.put(o.getID(), new SimpleDateFormat("yyyy-MM-dd").parse(o.getDateAndTime()));
        }
        List<Map.Entry<String, Date>> list = new LinkedList<Map.Entry<String, Date>>(newMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Date>>() {
            public int compare(Map.Entry<String, Date> o1,
                    Map.Entry<String, Date> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        HashMap<String, Date> temp = new LinkedHashMap<String, Date>();
        for (Map.Entry<String, Date> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        List<String> restaurants = new ArrayList<>();
        for (Map.Entry<String, Date> entry : temp.entrySet()) {
            restaurants.add(entry.getKey());
        }

        List<Order> resultList = new ArrayList<>();
        for (Map.Entry<String, Date> entry : temp.entrySet()) {
            resultList.add(getById(entry.getKey()));
        }
        return resultList;
    }

    public List<Order> sortOrderByDateDesc() throws JsonSyntaxException, IOException, ParseException {
        List<Order> orders = sortOrderByDateAsc();
        Collections.reverse(orders);
        return orders;
    }

    public List<Order> filtrateOrderByStatus(String params) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = orderDAO.getAll();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getOrderStatus().toString().equals(params)) {
                resultList.add(o);
            }
        }
        return resultList;
    }

    public List<Order> filtrateOrderByRestoranType(String params, ArrayList<Restaurant> restaurants)
            throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = orderDAO.getAll();
        Map<String, Restaurant> newMap = new HashMap<>();

        for (Order o : orders) {
            for (Restaurant res : restaurants) {
                if (o.getRestaurant().equals(res.getName())) {
                    newMap.put(o.getID(), res);
                    break;
                }
            }
        }
        List<String> resultList = new ArrayList<>();
        for (Map.Entry<String, Restaurant> entry : newMap.entrySet()) {
            if (entry.getValue().type.toString().equals(params)) {
                resultList.add(entry.getKey());
            }
        }

        List<Order> result = new ArrayList<>();
        for (String s : resultList) {
            result.add(orderDAO.getByID(s));
        }

        return result;
    }
}
