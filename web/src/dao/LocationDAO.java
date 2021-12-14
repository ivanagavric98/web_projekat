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

import model.Location;

public class LocationDAO  implements IDAO<Location, String>{

	private String path;
	
	public LocationDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<Location> getAll() throws JsonSyntaxException, IOException {
		ArrayList<Location> locations = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<Location>>(){}.getType());
		
		if(locations == null)
        locations = new ArrayList<Location>();
			
		return locations;
	}

	@Override
	public Location getByID(String street) throws JsonSyntaxException, IOException {
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
	public void create(Location entity) throws JsonSyntaxException, IOException {
		ArrayList<Location> locations= getAll();
		locations.add(entity);
		saveAll(locations);	
	}

	@Override
	public void update(Location entity) throws JsonSyntaxException, IOException {
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
	public void delete(Location entity) throws JsonSyntaxException, IOException {
		return;		
	}

	@Override
	public void save(Location entity) throws JsonSyntaxException, IOException {
		ArrayList<Location> locations = getAll();
		locations.add(entity);
		saveAll(locations);	
		
	}

	@Override
	public void saveAll(ArrayList<Location> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Location>>(){}.getType());
		writer.println(allEntities);
		writer.close();	
        	
	}

	@Override
	public ArrayList<Location> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
