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

import model.Address;

public class AddressDAO implements IDAO<Address, String>{

	private String path;
	
	public AddressDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<Address> getAll() throws JsonSyntaxException, IOException {
		ArrayList<Address> addresses = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<Address>>(){}.getType());
		
		if(addresses == null)
        addresses = new ArrayList<Address>();
			
		return addresses;
	}

	@Override
	public Address getByID(String street) throws JsonSyntaxException, IOException {
	/*	Address wantedAddress = null;
		ArrayList<Address> addresses = (ArrayList<Address>) getAll();
		if(addresses.size()!=0)
		{
			for(Address address : addresses) {
				if(address.get.equals(id)) {
					wantedRestaurant = restaurant;
					break;
				}
			}
		}
		return wantedRestaurant;
        */return null;
	}

	@Override
	public void create(Address entity) throws JsonSyntaxException, IOException {
		ArrayList<Address> addresses= getAll();
		addresses.add(entity);
		saveAll(addresses);	
	}

	@Override
	public void update(Address entity) throws JsonSyntaxException, IOException {
	/*	ArrayList<Restaurant> restaurants = getAll();
		for(Restaurant res : restaurants) {
			if(res.getName().equals(entity.getName())) {
				restaurants.set(restaurants.indexOf(res), entity);
				break;
			}
		}
		saveAll(restaurants);
        */
		
	}

	@Override
	public void delete(Address entity) throws JsonSyntaxException, IOException {
		return;		
	}

	@Override
	public void save(Address entity) throws JsonSyntaxException, IOException {
		ArrayList<Address> addresses = getAll();
		addresses.add(entity);
		saveAll(addresses);	
		
	}

	@Override
	public void saveAll(ArrayList<Address> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Address>>(){}.getType());
		writer.println(allEntities);
		writer.close();	
        	
	}

	@Override
	public ArrayList<Address> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
