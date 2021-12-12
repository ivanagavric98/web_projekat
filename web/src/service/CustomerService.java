package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.CustomerDAO;
import model.Customer;

public class CustomerService {
    private CustomerDAO customerDao;
    public CustomerService(CustomerDAO customerDAO) {
      this.customerDao=customerDAO;
    }
    
    public void register(Customer customer) throws JsonSyntaxException, IOException {
		customerDao.create(customer);
	}
 	
	public ArrayList<Customer> getAllCustomers() throws JsonSyntaxException, IOException{
		return customerDao.getAll();
	}
	
}
