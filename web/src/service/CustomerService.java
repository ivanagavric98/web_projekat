package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import dao.CustomerDAO;
import model.Customer;
import model.CustomerType;
import model.User;

public class CustomerService {
	private CustomerDAO customerDao;

	public CustomerService(CustomerDAO customerDAO) {
		this.customerDao = customerDAO;
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

}
