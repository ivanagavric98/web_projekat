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

import dao.CustomerDAO;
import dao.MenagerDAO;
import dao.OrderDAO;
import dao.SupplierDAO;
import dto.OrderFiltrateSortSearchDTO;
import javaxt.utils.string;
import model.Customer;
import dao.UserDAO;
import javaxt.utils.string;
import model.Article;
import model.Menager;
import model.Order;
import model.OrderStatus;
import model.Restaurant;
import model.ShoppingCart;
import model.ShoppingCartItem;
import model.Supplier;

public class OrderService {
    private OrderDAO orderDAO;
    private MenagerDAO managerDAO;
    private SupplierDAO supplierDAO;
    private CustomerDAO customerDAO;

    public OrderService(OrderDAO orderDAO, MenagerDAO managerDAO, SupplierDAO supplierDAO, CustomerDAO customerDAO) {
        this.orderDAO = orderDAO;
        this.managerDAO = managerDAO;
        this.supplierDAO = supplierDAO;
        this.customerDAO = customerDAO;
    }

    public Order add(ShoppingCart shoppingCart, Customer customer, Double newPrice)
            throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        String uniqString = UUID.randomUUID().toString().substring(0, 10);

        ArrayList<Order> customerOrders = customer.getOrders();
        
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
        order.price = newPrice;
        order.restaurant = shoppingCart.getRestaurantName();
        order.orderStatus = OrderStatus.PROCESSING;
        
        if(customerOrders != null) {
        	customerOrders.add(order);
        }else {
        	customerOrders = new ArrayList<Order>();
        	customerOrders.add(order);
        }
        
        ArrayList<Customer> allCustomers = customerDAO.getAll();
        for(Customer c: allCustomers) {
        	if(c.getUsername().equals(customer.getUsername()))
        		c.setOrders(customerOrders);
        }
        
        if (orders == null) {
            orderDAO.create(order);
        } else {
            for (Order u : orders) {
                if (u.getID().equals(order.ID)) {
                    return null;
                }
            }
         orderDAO.create(order);
         customerDAO.saveAll(allCustomers);
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

    public Order changeStatusToDelivered(String params, String username) throws JsonSyntaxException, IOException {
    	Supplier supplier = supplierDAO.getByID(username);
    	Order order = orderDAO.getByID(params);
    	order.setOrderStatus(OrderStatus.DELIVERED);
    	update(order);
    	for(Order o: supplier.getOrders()) {
    		if(o.getID().equals(order.getID())) {
    			o.setOrderStatus(OrderStatus.DELIVERED);
    	    	supplierDAO.update(supplier);    			
    		}
    	}
    	return order;
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

    public ArrayList<Order> usersOrderByRestaurantName(String name, OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getOrdersByUser(orderFiltrateSortSearchDTO);
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getRestaurant().toLowerCase().contains(name.trim().toLowerCase())) {
                resultList.add(o);
            }
        }
        return resultList;
    }

    public ArrayList<Order> getOrderByPriceRange(Double from, Double to, OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getOrdersByUser(orderFiltrateSortSearchDTO);
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

    public List<Order> sortOrderByRestaurantName(List<Order> orders) throws JsonSyntaxException, IOException {
        Set<Order> toSort = new HashSet<>();

        for (Order object : orders) {
            toSort.add(object);
        }

        List<Order> resultList = toSort.stream().sorted((e1, e2) -> e1.getRestaurant().compareTo(e2.getRestaurant()))
                .collect(Collectors.toList());

        return resultList;
    }

    public List<Order> sortOrderByRestaurantNameDesc(List<Order> orders) throws JsonSyntaxException, IOException {
        List<Order> ordersToReturn = sortOrderByRestaurantName(orders);
        Collections.reverse(ordersToReturn);
        return ordersToReturn;

    }

    public List<Order> sortOrderByPriceAsc(List<Order> orders) throws JsonSyntaxException, IOException {
        Set<Order> toSort = new HashSet<>();

        for (Order object : orders) {
            toSort.add(object);
        }

        List<Order> resultList = toSort.stream().sorted((e1, e2) -> e1.getPrice().compareTo(e2.getPrice()))
                .collect(Collectors.toList());

        return resultList;
    }

    public List<Order> sortOrderByPriceDesc(List<Order> orders) throws JsonSyntaxException, IOException {
        List<Order> ordersToReturn = sortOrderByPriceAsc(orders);
        Collections.reverse(ordersToReturn);
        return ordersToReturn;
    }

    public List<Order> sortOrderByDateAsc(List<Order> orders) throws JsonSyntaxException, IOException, ParseException {
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

    public List<Order> sortOrderByDateDesc(List<Order> orders) throws JsonSyntaxException, IOException, ParseException {
        List<Order> ordersToReturn = sortOrderByDateAsc(orders);
        Collections.reverse(ordersToReturn);
        return ordersToReturn;
    }

    public List<Order> filtrateOrderByStatus(String params, List<Order> orders)
            throws JsonSyntaxException, IOException {
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getOrderStatus().toString().equals(params)) {
                resultList.add(o);
            }
        }
        return resultList;
    }

    public List<Order> filtrateOrderByRestoranType(String params, ArrayList<Restaurant> restaurants, List<Order> orders)
            throws JsonSyntaxException, IOException {
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

    public List<Order> searchFiltreteSortOrders(OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO,
            ArrayList<Restaurant> restaurants)
            throws JsonSyntaxException, IOException, ParseException {
        List<Order> searchByRestaurantName = new ArrayList<Order>();
        List<Order> searchByprice = new ArrayList<Order>();
        List<Order> searchByDate = new ArrayList<Order>();

        if (orderFiltrateSortSearchDTO.getSearchByrestaurantName() != "") {
        	System.out.println("sssssss");
        	searchByRestaurantName = usersOrderByRestaurantName(orderFiltrateSortSearchDTO.getSearchByrestaurantName(), orderFiltrateSortSearchDTO);
        } else {
            searchByRestaurantName = getOrdersByUser(orderFiltrateSortSearchDTO);
        }
    
        System.out.println(searchByRestaurantName.size());
        if (orderFiltrateSortSearchDTO.getSearchBypriceFrom() != 0.0
                && orderFiltrateSortSearchDTO.getSearchBypriceTo() != 0.0) {
        	System.out.println("sssssss");
            searchByprice = getOrderByPriceRange(orderFiltrateSortSearchDTO.getSearchBypriceFrom(),
                    orderFiltrateSortSearchDTO.getSearchBypriceTo(), orderFiltrateSortSearchDTO);
            System.out.println("kkkkk");
        } else {
            searchByprice = getOrdersByUser(orderFiltrateSortSearchDTO);
        }
        System.out.println(searchByprice.size());

        if (orderFiltrateSortSearchDTO.getSearchBydateFrom() != null
                && orderFiltrateSortSearchDTO.getSearchBydateTo() != null) {
            searchByDate = getOrderByDateRange(orderFiltrateSortSearchDTO.getSearchBydateFrom(),
                    orderFiltrateSortSearchDTO.getSearchBydateTo());
        } else {
            searchByDate = getAllOrders();
        }

        System.out.println(searchByDate.size());
        
        List<Order> intersectionResult = new ArrayList<Order>();
        List<Order> intersectionResult1 = new ArrayList<Order>();

        for (Order order : searchByRestaurantName) {
            for (Order order1 : searchByprice) {
                if (order.getID().equals(order1.getID())) {
                    intersectionResult.add(order);
                }
            }
        }
        System.out.println(intersectionResult.size());

        for (Order order : intersectionResult) {
            for (Order order2 : searchByDate) {
                if (order.getID().equals(order2.getID())) {
                    intersectionResult1.add(order);
                }
            }
        }
        System.out.println(intersectionResult1.size());

        List<Order> sortedList = new ArrayList<Order>();
        if (orderFiltrateSortSearchDTO.getSortByRestaurantName() != null) {
            if (orderFiltrateSortSearchDTO.getSortByRestaurantName().equals("ascending")) {
                sortedList = sortOrderByRestaurantName(intersectionResult1);
            } else {
                sortedList = sortOrderByRestaurantNameDesc(intersectionResult1);
            }
        }
        

        if (orderFiltrateSortSearchDTO.getSortByPrice() != null) {
            if (orderFiltrateSortSearchDTO.getSortByPrice().equals("ascending")) {
                sortedList = sortOrderByPriceAsc(intersectionResult1);
            } else {
                sortedList = sortOrderByPriceDesc(intersectionResult1);
            }
        }

        if (orderFiltrateSortSearchDTO.getSortByDate() != null) {
            if (orderFiltrateSortSearchDTO.getSortByDate().equals("ascending")) {
                sortedList = sortOrderByDateAsc(intersectionResult1);
            } else {
                sortedList = sortOrderByDateDesc(intersectionResult1);
            }
        }

        if (orderFiltrateSortSearchDTO.getSortByRestaurantName() == null
                && orderFiltrateSortSearchDTO.getSortByPrice() == null
                && orderFiltrateSortSearchDTO.getSortByDate() == null) {
            sortedList = intersectionResult1;
        }

        List<Order> filtrateByOrderStatus = new ArrayList<Order>();
        if (orderFiltrateSortSearchDTO.getFiltrateByOrderStatus() != null) {
            filtrateByOrderStatus = filtrateOrderByStatus(orderFiltrateSortSearchDTO.getFiltrateByOrderStatus(),
                    sortedList);
        } else {
            filtrateByOrderStatus = sortedList;
        }

        List<Order> filtrateByRestoranType = new ArrayList<Order>();
        if (orderFiltrateSortSearchDTO.getFiltrateByRestaurantType() != null) {
            filtrateByRestoranType = filtrateOrderByRestoranType(
                    orderFiltrateSortSearchDTO.getFiltrateByRestaurantType(), restaurants, sortedList);
        } else {
            filtrateByRestoranType = sortedList;
        }

        List<Order> result = new ArrayList<Order>();
        for (Order order : filtrateByOrderStatus) {
            for (Order order1 : filtrateByRestoranType) {
                if (order.getID().equals(order1.getID())) {
                    result.add(order);
                }
            }
        }
        return result;
    }

    public ArrayList<Order> getOrdersByUser(OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getCustomer().equals(orderFiltrateSortSearchDTO.getUser())) {
                resultList.add(o);
            }
        }
        return resultList;
    }

    public ArrayList<Order> getOrdersByRestaurant(String params) throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getRestaurant().equals(params)) 
                resultList.add(o);
        }
        return resultList;
    }

    public ArrayList<Order> getOrdersByRestaurantWithStatusDelivered() throws JsonSyntaxException, IOException {
        ArrayList<Order> orders = getAllOrders();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order o : orders) {
            if (o.getOrderStatus().equals(OrderStatus.DELIVERED)) {
                resultList.add(o);
            }
        }
        return resultList;
    }
    
	public ArrayList<Order> getMyOwnOrders(String customerUsername) throws JsonSyntaxException, IOException {
		ArrayList<Order> allOrders = orderDAO.getAll();
		ArrayList<Order> result = new ArrayList<>();
		for (Order o : allOrders) {
			if (o.getCustomer().equals(customerUsername) && (o.getOrderStatus().equals(OrderStatus.IN_TRANSPORT) || o.getOrderStatus().equals(OrderStatus.IN_PREPARATION) || o.getOrderStatus().equals(OrderStatus.WAITING_FOR_SUPPLIER))) {
				result.add(o);
			}
		}
		return result;
	}

	public ArrayList<Order> getOrdersBySupplier(String supplierUsername) throws JsonSyntaxException, IOException {
		ArrayList<Supplier> allSupplier = supplierDAO.getAll();
		ArrayList<Order> result = new ArrayList<>();
		
		for (Supplier s : allSupplier) {
			if (s.getUsername().equals(supplierUsername)) {
				for(Order o: s.getOrders()) {
					if(o.getOrderStatus().equals(OrderStatus.IN_TRANSPORT) || o.getOrderStatus().equals(OrderStatus.DELIVERED)) {
						result.add(o);
					}
				}
			}
		}	
		return result;	
		}

	public ArrayList<Order> getOrdersByManager() throws JsonSyntaxException, IOException {
		ArrayList<Order> allOrders = orderDAO.getAll();
		ArrayList<Menager> managers = managerDAO.getAll();
		
		ArrayList<Order> result = new ArrayList<>();
		for (Order o : allOrders) {
			for(Menager m : managers) {
				if (o.getRestaurant().equals(m.getRestaurant()) ) {
					result.add(o);
				}
			}
		}	
		return result;		
	}
}
