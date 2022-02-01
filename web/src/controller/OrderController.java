package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import dto.OrderFiltrateSortSearchDTO;
import model.Customer;
import model.Order;
import model.Restaurant;
import model.ShoppingCart;
import service.CustomerService;
import service.OrderService;
import service.RestaurantService;

public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order addOrder(ShoppingCart shoppingCart, Customer customer, Double newPrice) throws JsonSyntaxException, IOException {
        return orderService.add(shoppingCart, customer, newPrice);
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

    public Order changeStatusToDelivered(String params, String username) throws JsonSyntaxException, IOException {
        return orderService.changeStatusToDelivered(params, username);
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

    public ArrayList<Order> usersOrderByRestaurantName(String name, OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO) throws JsonSyntaxException, IOException {
        return orderService.usersOrderByRestaurantName(name, orderFiltrateSortSearchDTO);
    }

    public ArrayList<Order> getOrderByPriceRange(Double from, Double to, OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO) throws JsonSyntaxException, IOException {
        return orderService.getOrderByPriceRange(from, to, orderFiltrateSortSearchDTO);
    }

    public ArrayList<Order> getOrderByDateRange(String from, String to)
            throws JsonSyntaxException, IOException, ParseException {
        return orderService.getOrderByDateRange(from, to);

    }

    // public List<Order> sortOrderByRestaurantName() throws JsonSyntaxException,
    // IOException {
    // return orderService.sortOrderByRestaurantName();
    // }

    // public List<Order> sortOrderByRestaurantNameDesc() throws
    // JsonSyntaxException, IOException {
    // return orderService.sortOrderByRestaurantNameDesc();
    // }

    // public List<Order> sortOrderByPriceAsc() throws JsonSyntaxException,
    // IOException {
    // return orderService.sortOrderByPriceAsc();
    // }

    // public List<Order> sortOrderByPriceDesc() throws JsonSyntaxException,
    // IOException {
    // return orderService.sortOrderByPriceDesc();
    // }

    // public List<Order> sortOrderByDateDesc() throws JsonSyntaxException,
    // IOException, ParseException {
    // return orderService.sortOrderByDateDesc();
    // }

    // public List<Order> sortOrderByDateAsc() throws JsonSyntaxException,
    // IOException, ParseException {
    // return orderService.sortOrderByDateAsc();
    // }

    // public List<Order> filtrateOrderByStatus(String params) throws
    // JsonSyntaxException, IOException {
    // return orderService.filtrateOrderByStatus(params);
    // }

    public List<Order> filtrateOrderByRestoranType(String params, ArrayList<Restaurant> restaurants, List<Order> orders)
            throws JsonSyntaxException, IOException {
        return orderService.filtrateOrderByRestoranType(params, restaurants, orders);
    }

    public List<Order> searchFiltreteSortOrders(OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO,
            ArrayList<Restaurant> restaurants)
            throws JsonSyntaxException, IOException, ParseException {
        return orderService.searchFiltreteSortOrders(orderFiltrateSortSearchDTO, restaurants);
    }

    public ArrayList<Order> getOrdersByUser(OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO) throws JsonSyntaxException, IOException {
        return orderService.getOrdersByUser(orderFiltrateSortSearchDTO);
    }

    public ArrayList<Order> getOrdersByRestaurant(String params) throws JsonSyntaxException, IOException {
        return orderService.getOrdersByRestaurant(params);

    }
    public ArrayList<Order> getOrdersByRestaurantWithStatusDelivered() throws JsonSyntaxException, IOException {
        return orderService.getOrdersByRestaurantWithStatusDelivered();

    }
    public ArrayList<Order> getMyOwnOrders(String customerUsername) throws JsonSyntaxException, IOException {
		return orderService.getMyOwnOrders(customerUsername);
	}

	public ArrayList<Order> getOrdersBySupplier(String supplierUsername) throws JsonSyntaxException, IOException {
		return orderService.getOrdersBySupplier(supplierUsername);
	}

	public ArrayList<Order> getOrdersByManager() throws JsonSyntaxException, IOException {
		return orderService.getOrdersByManager();
	}
	
	public ArrayList<Order> getAll() throws JsonSyntaxException, IOException {
		return orderService.getAllOrders();
	}
}