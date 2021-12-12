package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	

}
