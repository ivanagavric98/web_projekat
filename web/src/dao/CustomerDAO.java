package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeElement;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Customer;

public class CustomerDAO implements IDAO<Customer,String> {
    private String path;
	
	public CustomerDAO(String path) {
		super();
		this.path = path;
	}
    @Override
    public ArrayList<Customer> getAll() throws JsonSyntaxException, IOException {
        ArrayList<Customer> customers = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
        Charset.defaultCharset()).size() == 0) ? "" : 
            Files.readAllLines(Paths.get(path),
                    Charset.defaultCharset()).get(0), 
            new TypeToken<List<Customer>>(){}.getType());

            if(customers == null)
            customers = new ArrayList<Customer>();
                
            return customers;
    }

	

	@Override
	public void create(Customer entity) throws JsonSyntaxException, IOException {
		ArrayList<Customer> customers = getAll();
		customers.add(entity);
		saveAll(customers);	
	}
    @Override
    public ArrayList<Customer> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Customer getByID(String id) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void update(Customer entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void delete(Customer entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void save(Customer entity) throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void saveAll(ArrayList<Customer> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Customer>>(){}.getType());
		writer.println(allEntities);
		writer.close();		
        
    }
    public List<Customer> userSortByUserPointAsc() throws JsonSyntaxException, IOException {
		ArrayList<Customer> users=getAll();
		Set<Customer> toSort=new HashSet<>();

		for (Customer object : users) {
			toSort.add(object);
		}

		List<Customer> resultList = toSort.stream().sorted((e1, e2) -> 
        Integer.valueOf(e1.getPoints()).compareTo(Integer.valueOf(e2.getPoints()))).collect(Collectors.toList());

		return resultList;
    }

    public List<Customer> userSortByUserPointsDesc() throws JsonSyntaxException, IOException {
		ArrayList<Customer> users=getAll();
		Set<Customer> toSort=new HashSet<>();

		for (Customer object : users) {
			toSort.add(object);
		}

		List<Customer> resultList = toSort.stream().sorted((e1, e2) -> 
		Integer.valueOf(e1.getPoints()).compareTo(Integer.valueOf(e2.getPoints()))).collect(Collectors.toList());
		Collections.reverse(resultList);
		return resultList;  
      }
      
    public List<Customer> customerFiltrateByType(String type) throws JsonSyntaxException, IOException {
        ArrayList<Customer> customers=getAll();
		ArrayList<Customer> resultList=new ArrayList<>();
        for (Customer customer : customers) {
            if(customer.getType().type.toString().toLowerCase().equals(type.toLowerCase())){
                resultList.add(customer);
            }
        }
        return resultList;
        }
}


