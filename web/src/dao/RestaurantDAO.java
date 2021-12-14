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

import model.Restaurant;

public class RestaurantDAO  implements IDAO<Restaurant, String>{

	private String path;
	
	public RestaurantDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<Restaurant> getAll() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<Restaurant>>(){}.getType());
		
		if(restaurants == null)
        restaurants = new ArrayList<Restaurant>();
			
		return restaurants;
	}

	@Override
	public Restaurant getByID(String id) throws JsonSyntaxException, IOException {
		Restaurant wantedRestaurant = null;
		ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) getAll();
		if(restaurants.size()!=0)
		{
			for(Restaurant restaurant : restaurants) {
				if(restaurant.getName().equals(id)) {
					wantedRestaurant = restaurant;
					break;
				}
			}
		}
		return wantedRestaurant;
	}

	@Override
	public void create(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants= getAll();
		restaurants.add(entity);
		saveAll(restaurants);	
	}

	@Override
	public void update(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		for(Restaurant res : restaurants) {
			if(res.getName().equals(entity.getName())) {
				restaurants.set(restaurants.indexOf(res), entity);
				break;
			}
		}
		saveAll(restaurants);
		
	}

	@Override
	public void delete(Restaurant entity) throws JsonSyntaxException, IOException {
		return;		
	}

	@Override
	public void save(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		restaurants.add(entity);
		saveAll(restaurants);	
		
	}

	@Override
	public void saveAll(ArrayList<Restaurant> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Restaurant>>(){}.getType());
		writer.println(allEntities);
		writer.close();		
	}

	@Override
	public ArrayList<Restaurant> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
