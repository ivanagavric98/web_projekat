package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Customer;
import model.User;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.IOException;
import java.util.List;

import service.CustomerService;
import spark.CustomErrorPages;

public class CustomerController {
    private CustomerService customerService;
	private static Gson gson = new Gson();

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
    }
		
        public Customer register(Customer customer) throws JsonSyntaxException, IOException{			
             customerService.register(customer);
         return customer;
        }

        public List<Customer> userSortByUserPointAsc() throws JsonSyntaxException, IOException {
			return  customerService.userSortByUserPointAsc();
		}

        public List<Customer> userSortByUserPointsDesc() throws JsonSyntaxException, IOException {
            return customerService.userSortByUserPointsDesc();
        }

        public List<Customer> customerFiltrateByType(String type) throws JsonSyntaxException, IOException {
            return customerService.customerFiltrateByType(type);   
             }
		
	}	

