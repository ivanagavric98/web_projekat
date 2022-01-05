package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.OrderDAO;
import model.Order;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Boolean add(Order order) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        Boolean result = false;
        if (orders == null) {
            orderDAO.create(order);
            result = true;
        } else {
            for (Order u : orders) {
                if (u.getID().equals(order.ID)) {
                    return result = false;
                }
            }
            orderDAO.create(order);
            result = true;
        }

        return result;
    }

    public ArrayList<Order> getAllOrders() throws JsonSyntaxException, IOException {
        return orderDAO.getAll();
    }
}
