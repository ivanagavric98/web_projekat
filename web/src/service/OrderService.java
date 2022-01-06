package service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonSyntaxException;

import dao.OrderDAO;
import javafx.scene.chart.PieChart.Data;
import javaxt.utils.string;
import model.Order;
import model.OrderStatus;
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
        String[] parts = LocalDateTime.now().toString().split("T");
        order.dateAndTime = parts[0] + " " + parts[1];
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
        ;
    }
}
