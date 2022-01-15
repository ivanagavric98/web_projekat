package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Customer;
import model.CustomerType;
import service.CustomerTypeService;

public class CustomerTypeController {
    private CustomerTypeService customerTypeService;

    public CustomerTypeController(CustomerTypeService customerTypeService) {
        this.customerTypeService = customerTypeService;
    }

    public Boolean add(CustomerType customerType) throws JsonSyntaxException, IOException {
        return customerTypeService.add(customerType);
    }

    public void update(CustomerType uscustomerTypeer) throws JsonSyntaxException, IOException {
        customerTypeService.update(uscustomerTypeer);
    }

    public ArrayList<CustomerType> getAllTypes() throws JsonSyntaxException, IOException {
        return customerTypeService.getAllTypes();
    }

    public Double getPriceWithDiscount(Double price, Customer customer) throws JsonSyntaxException, IOException {
        return customerTypeService.getPriceWithDiscount(price, customer);
    }

}