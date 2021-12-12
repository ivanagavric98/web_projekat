package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Customer;
import model.User;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.IOException;

import service.CustomerService;

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
		
	}	

