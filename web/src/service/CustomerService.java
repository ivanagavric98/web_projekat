package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import dao.CustomerDAO;
import model.Customer;
import model.User;

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

	public List<Customer> userSortByUserPointAsc() throws JsonSyntaxException, IOException {
		return customerDao.userSortByUserPointAsc();
	}

	public List<Customer> userSortByUserPointsDesc() throws JsonSyntaxException, IOException {
		return customerDao.userSortByUserPointsDesc();
	}

	public List<Customer> customerFiltrateByType(String type) throws JsonSyntaxException, IOException {
		return customerDao.customerFiltrateByType(type);
	}	
	
}
