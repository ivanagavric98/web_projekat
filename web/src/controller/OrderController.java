package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import model.Order;
import model.ShoppingCart;
import service.OrderService;

public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order addOrder(ShoppingCart shoppingCart) throws JsonSyntaxException, IOException {
        return orderService.add(shoppingCart);
    }

    public Order changeStatusToInPreparation(String params) throws JsonSyntaxException, IOException {
        return orderService.changeStatusToInPreparation(params);

    }

    public Order changeStatusToWaitingForSupplier(String params) throws JsonSyntaxException, IOException {
        return orderService.changeStatusToWaitingForSupplier(params);
    }

    public Order changeStatusToInTransport(String params) throws JsonSyntaxException, IOException {
        return orderService.changeStatusToInTransport(params);
    }

    public Order changeStatusToDelivered(String params) throws JsonSyntaxException, IOException {
        return orderService.changeStatusToDelivered(params);
    }

    public Order changeStatusToCanceled(String params) throws JsonSyntaxException, IOException {
        return orderService.changeStatusToCanceled(params);
    }

    public ArrayList<Order> getOrderWithStatusInPreparation() throws JsonSyntaxException, IOException {
        return orderService.getOrderWithStatusInPreparation();
    }

    public ArrayList<Order> getOrderWithStatusWaitingForSupplier() throws JsonSyntaxException, IOException {
        return orderService.getOrderWithStatusWaitingForSupplier();
    }

    public Order getByID(String params) throws JsonSyntaxException, IOException {
        return orderService.getById(params);
    }

    public void update(Order params) throws JsonSyntaxException, IOException {
        orderService.update(params);
    }

	public ArrayList<Order> getMyOwnOrders(String customerUsername) throws JsonSyntaxException, IOException {
		return orderService.getMyOwnOrders(customerUsername);
	}
}