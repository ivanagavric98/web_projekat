package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.AddressDAO;
import dao.LocationDAO;
import dao.RestaurantDAO;
import model.Address;
import model.Location;
import model.Menager;
import model.Restaurant;

public class RestaurantService {
    private RestaurantDAO restaurantDAO;
    private LocationDAO locationDAO;
    private AddressDAO addressDAO;
    private LocationService locationService;
    private AddressService addressService;
    private MenagerService menagerService;
	
	public RestaurantService(RestaurantDAO restaurantDAO) {
		this.restaurantDAO=restaurantDAO;
	}

    
	
	public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant>restaurants=getAllRestaurants();
		Boolean result=false;
       
        if(restaurants.size()==0){
            restaurantDAO.create(restaurant);
            result=true;
        }else{
            for(Restaurant u : restaurants){
                if(u.name.equals(restaurant.name)){
                    result= false;
                }else{
                    restaurantDAO.create(restaurant);
                    result=true;
                }
            }
        }
		return result;
	}
	
	public ArrayList<Restaurant> getAllRestaurants() throws JsonSyntaxException, IOException{
		return restaurantDAO.getAll();
	}
}
