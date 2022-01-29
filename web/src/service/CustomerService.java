package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.gson.JsonSyntaxException;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import model.Customer;
import model.CustomerType;
import model.Order;
import model.Restaurant;
import model.User;

public class CustomerService {
	private CustomerDAO customerDao;
	private OrderDAO orderDAO;

	public CustomerService(CustomerDAO customerDAO, OrderDAO orderDAO) {
		this.customerDao = customerDAO;
		this.orderDAO = orderDAO;
	}

	public Boolean register(Customer customer) throws JsonSyntaxException, IOException {
		ArrayList<Customer> users = getAllCustomers();
		Boolean result = false;
		if (users == null) {
			customerDao.create(customer);
			result = true;
		} else {
			for (User u : users) {
				if (u.username.equals(customer.username)) {
					return result = false;
				}
			}
			customerDao.create(customer);
			result = true;
		}

		return result;
	}

	public ArrayList<Customer> getAllCustomers() throws JsonSyntaxException, IOException {
		return customerDao.getAll();
	}

	public List<Customer> userSortByUserPointAsc() throws JsonSyntaxException, IOException {
		return customerDao.userSortByUserPointAsc();
	}

	public List<Customer> userSortByUserPointsDesc() throws JsonSyntaxException, IOException {
		return customerDao.userSortByUserPointsDesc();
	}

	public List<Customer> customerFiltrateByType(String type) throws JsonSyntaxException, IOException {
		return customerDao.customerFiltrateByType(type);
	}

	public Customer updateCustomerssPoints(String customer, double price, ArrayList<CustomerType> allTypes)
			throws JsonSyntaxException, IOException {
		return customerDao.updateUsersPoints(customer, price, allTypes);

	}

	public Customer updateUsersPointsAferCancellation(String customer, Double price)
			throws JsonSyntaxException, IOException {
		return customerDao.updateUsersPointsAferCancellation(customer, price);
	}

	public Customer getByUsername(String customerUsername) throws JsonSyntaxException, IOException {
		return customerDao.getByID(customerUsername);
	}

	public ArrayList<Customer> getCustomersWithOrderFromRestaurant(String restaurantName) throws JsonSyntaxException, IOException {
		ArrayList<Order> orders = orderDAO.getAll();
		ArrayList<Customer> customers = customerDao.getAll();
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		for(Customer customer: customers) {
			for(Order order: orders) {
				if(order.getRestaurant().equals(restaurantName)) {
					if(customer.getUsername().equals(order.getCustomer())) 
						result.add(customer);
					
					if(result.contains(customer))
						break;
				}
			}	
		}
		return result;
	}

}
