package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.CustomerTypeDAO;
import model.Customer;
import model.CustomerType;
import model.Type;

public class CustomerTypeService {
    private CustomerTypeDAO customerTypeDAO;

    public CustomerTypeService(CustomerTypeDAO customerTypeDAO) {
        this.customerTypeDAO = customerTypeDAO;
    }

    public Boolean add(CustomerType type) throws JsonSyntaxException, IOException {
        ArrayList<CustomerType> types = getAllTypes();
        Boolean result = false;
        if (types == null) {
            customerTypeDAO.create(type);
            result = true;
        } else {
            for (CustomerType u : types) {
                if (u.type.equals(type.type)) {
                    return result = false;
                }
            }
            customerTypeDAO.create(type);
            result = true;
        }

        return result;
    }

    public ArrayList<CustomerType> getAllTypes() throws JsonSyntaxException, IOException {
        return customerTypeDAO.getAll();
    }

    public void updateType(CustomerType entity) throws JsonSyntaxException, IOException {
        customerTypeDAO.update(entity);
    }

    public CustomerType getType(String entity) throws JsonSyntaxException, IOException {
        return customerTypeDAO.getType(entity);
    }

    public void update(CustomerType params) throws JsonSyntaxException, IOException {
        customerTypeDAO.update(params);
    }

    public Double getPriceWithDiscount(Double price, Customer customer) throws JsonSyntaxException, IOException {
        CustomerType ct = getType(customer.getType().getType().toString());
        Double newPrice = price - (price * ct.discount) / 100;
        return newPrice;
    }

}