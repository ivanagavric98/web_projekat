package service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonSyntaxException;

import dao.MenagerDAO;
import dao.OrderDAO;
import dao.UserDAO;
import javaxt.utils.string;
import model.Article;
import model.Menager;
import model.Order;
import model.OrderStatus;
import model.ShoppingCart;
import model.ShoppingCartItem;

public class OrderService {
    private OrderDAO orderDAO;
    private MenagerDAO managerDAO;

    public OrderService(OrderDAO orderDAO, MenagerDAO managerDAO) {
        this.orderDAO = orderDAO;
        this.managerDAO = managerDAO;
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
    }

	public ArrayList<Order> getMyOwnOrders(String customerUsername) throws JsonSyntaxException, IOException {
		ArrayList<Order> allOrders = orderDAO.getAll();
		ArrayList<Order> result = new ArrayList<>();
		for (Order o : allOrders) {
			if (o.getCustomer().equals(customerUsername) && o.getOrderStatus().equals(OrderStatus.IN_TRANSPORT) || o.getOrderStatus().equals(OrderStatus.IN_PREPARATION) || o.getOrderStatus().equals(OrderStatus.WAITING_FOR_SUPPLIER)) {
				result.add(o);
			}
		}
		return result;
	}

	public ArrayList<Order> getOrdersBySupplier(String supplierUsername) throws JsonSyntaxException, IOException {
		ArrayList<Order> allOrders = orderDAO.getAll();
		ArrayList<Order> result = new ArrayList<>();
		for (Order o : allOrders) {
			if (o.getCustomer().equals(supplierUsername) && o.getOrderStatus().equals(OrderStatus.IN_TRANSPORT) || o.getOrderStatus().equals(OrderStatus.IN_PREPARATION) || o.getOrderStatus().equals(OrderStatus.WAITING_FOR_SUPPLIER)) {
				result.add(o);
			}
		}	
		return result;	
		}

	public ArrayList<Order> getOrdersByManager(String managerUsername) throws JsonSyntaxException, IOException {
		ArrayList<Order> allOrders = orderDAO.getAll();
		ArrayList<Menager> managers = managerDAO.getAll();
		ArrayList<Order> result = new ArrayList<>();
		for (Order o : allOrders) {
			for(Menager m : managers) {
				if (o.getCustomer().equals(managerUsername) && o.getRestaurant().equals(m.getRestaurant()) && m.getUsername().equals(managerUsername)) {
					result.add(o);
				}
			}
		}	
		return result;		}
}
