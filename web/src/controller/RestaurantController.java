package controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Menager;
import model.Restaurant;
import service.AddressService;
import service.LocationService;
import service.MenagerService;
import service.RestaurantService;

public class RestaurantController {
    private RestaurantService restaurantService;
    private MenagerService menagerService;
    private AddressService addressService;
    private LocationService locationService;
	private static Gson gson = new Gson();

	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

		public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException{
          
            Boolean restourantSave=restaurantService.register(restaurant);
            
			return restourantSave;
	   }
	
}
